package edu.lehigh.cse216.team_m.backend;
import spark.Spark;
import com.google.gson.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

// import org.omg.CORBA.CODESET_INCOMPATIBLE;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.HashMap;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.json.JsonFactory;
/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {
    private static final String DEFAULT_PORT_DB = "5432";
    private static final int DEFAULT_PORT_SPARK = 4567;

    /**
     * Get a fully-configured connection to the database, or exit immediately
     * Uses the Postgres configuration from environment variables
     * 
     * NB: now when we shutdown the server, we no longer lose all data
     * 
     * @return null on failure, otherwise configured database object
     */
    private static Database getDatabaseConnection(){
        if( System.getenv("DATABASE_URL") != null ){
            return Database.getDatabase(System.getenv("DATABASE_URL"), DEFAULT_PORT_DB);
        }

        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        return Database.getDatabase(ip, port, "", user, pass);
    } 
    /**
    * Get an integer environment variable if it exists, and otherwise return the
    * default value.
    * 
    * @param envar The name of the environment variable to get.
    * @param defaultVal The integer value to use as the default if envar isn't found
    * 
    * @return The best answer we could come up with for a value for envar
    */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    /**
     * @author David
     * @version 4/2/2023
     */
    public static <GoogleSignInResponse> void main(String[] args) {
        //Store the exist <user,UUID.toString()> infomation
        HashMap<String,String> userSessPair = new HashMap<String,String>();

        // Set up the location for serving static files.  If the STATIC_LOCATION
        // environment variable is set, we will serve from it.  Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe.  See 
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        Gson gson = new Gson();
        // NB: every time we shut down the server, we will lose all data, and 
        //     every time we start the server, we'll have an empty dataStore,
        //     with IDs starting over from 0.
        final DataStore dataStore = new DataStore();
        Database db = getDatabaseConnection();

        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });
        
        // GET route that returns all message titles and Ids.  All we do is get 
        // the data, embed it in a StructuredResponse, turn it into JSON, and 
        // return it.  If there's no data, we return "[]", so there's no need 
        // for error handling.
        Spark.get("/messages/:SessID", (request, response) -> {
            String mSessID = request.params("mSessID");
            String user = userSessPair.get(mSessID);
            if(user!=null)
            {
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, db.selectAll()));
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // GET route that returns everything for a single row in the DataStore.
        // The ":id" suffix in the first parameter to get() becomes 
        // request.params("id"), so that we can get the requested row ID.  If 
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error.  Otherwise, we have an integer, and the only possible 
        // error is that it doesn't correspond to a row with data.
        Spark.get("/messages/:id/:SessID", (request, response) -> {
            String mSessID = request.params("mSessID");
            String user = userSessPair.get(mSessID);
            if(user!=null)
            {
                int idx = Integer.parseInt(request.params("id"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                DataRow data = db.selectOne(idx);
                if (data == null) {
                    return gson.toJson(new StructuredResponse("error", idx + " not found", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, data));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // POST route for adding a new element to the DataStore.  This will read
        // JSON from the body of the request, turn it into a SimpleRequest 
        // object, extract the title and message, insert them, and return the 
        // ID of the newly created row.
        Spark.post("/messages/:SessID", (request, response) -> {
            String mSessID = request.params("mSessID");
            String user = userSessPair.get(mSessID);
            if(user!=null)
            {
                // NB: if gson.Json fails, Spark will reply with status 500 Internal 
                // Server Error
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                // NB: even on error, we return 200, but with a JSON object that
                //     describes the error.
                response.status(200);
                response.type("application/json");
                // NB: createEntry checks for null title and message
                int newId = db.insertRow(req.mSubject, req.mMessage);
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } 
                else {
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for updating a row in the DataStore.  This is almost 
        // exactly the same as POST
        Spark.put("/messages/:id/:Ideaid/:SessID", (request, response) -> {
            String mSessID = request.params("mSessID");
            String user = userSessPair.get(mSessID);
            if(user!=null)
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("id"));
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.updateOne(idx, req.mMessage);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for liking a row in the DataStore.
        Spark.put ("/messages/:id/like/:SessID", (request, response) -> {
            String mSessID = request.params("mSessID");
            String user = userSessPair.get(mSessID);
            if(user!=null)
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("id"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.likeOne(idx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to like row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for unliking a row in the DataStore.
        Spark.put("/messages/:id/unlike/:SessID", (request, response) -> {
            String mSessID = request.params("mSessID");
            String user = userSessPair.get(mSessID);
            if(user!=null)
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("id"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.unlikeOne(idx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to unlike row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        Spark.post("/login", (request, response) -> {
            String CLIENT_ID = "926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com";
            HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
            JsonFactory JSON_FACTORY = new JacksonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
            GoogleIdToken idToken = verifier.verify(request.body());
            if (idToken != null){
                Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                UUID uuid = UUID.randomUUID();
                userSessPair.put(userId,uuid.toString());
                response.status(200);
                response.redirect("/index.html");
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, uuid.toString()));
            }
            else{
                return gson.toJson(new StructuredResponse("error", "Invalid Google Token!", null));
            }
        });

        // // DELETE route for removing a row from the DataStore
        // Spark.delete("/messages/:id/:SessID", (request, response) -> {
        //     String mSessID = request.params("mSessID");
        //     String user = userSessPair.get(mSessID);
        //     if(user!=null)
        //     {
        //         // If we can't get an ID, Spark will send a status 500
        //         int idx = Integer.parseInt(request.params("id"));
        //         // ensure status 200 OK, with a MIME type of JSON
        //         response.status(200);
        //         response.type("application/json");
        //         // NB: we won't concern ourselves too much with the quality of the 
        //         //     message sent on a successful delete
        //         int result = db.deleteRow(idx);
        //         if (result == -1) {
        //             return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
        //         }
        //         else {
        //             return gson.toJson(new StructuredResponse("ok", null, null));
        //         }
        //     }
        //     return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        // });
    }
}