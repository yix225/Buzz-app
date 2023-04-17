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
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";
        //Map<String, String> env = System.getenv();
        // String ip = env.get("POSTGRES_IP");
        // String port = env.get("POSTGRES_PORT");
        // String user = env.get("POSTGRES_USER");
        // String pass = env.get("POSTGRES_PASS");
        // String ip = env.get("POSTGRES_IP");
        // String port = env.get("POSTGRES_PORT");
        // String user = env.get("POSTGRES_USER");
        // String pass = env.get("POSTGRES_PASS");
        
        return Database.getDatabase(ip, port, "", user, pass);
    } 
<<<<<<< HEAD
    //
=======
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
    /**
    * Get an integer environment variable if it exists, and otherwise return the
    * default value.
    * 
    * @envar      The name of the environment variable to get.
    * @defaultVal The integer value to use as the default if envar isn't found
    * 
    * @returns The best answer we could come up with for a value for envar
    */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }
<<<<<<< HEAD
=======

>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
    /**
     * @author David
     * @version 4/2/2023
     */
<<<<<<< HEAD
    //public static <GoogleSignInResponse> void main(String[] args) {
    public static void main(String[] args) {
=======
    public static <GoogleSignInResponse> void main(String[] args) {
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
        //Store the exist < UUID as Int , user > infomation
        HashMap<Integer,String> userSessPair = new HashMap<Integer,String>();

        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", DEFAULT_PORT_SPARK));

        // Set up the location for serving static files.  If the STATIC_LOCATION
        // environment variable is set, we will serve from it.  Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        if ("True".equalsIgnoreCase(System.getenv("CORS_ENABLED"))) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }
        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe.  See 
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
<<<<<<< HEAD
        final Gson gson = new Gson();
=======
        Gson gson = new Gson();
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
        // NB: every time we shut down the server, we will lose all data, and 
        //     every time we start the server, we'll have an empty dataStore,
        //     with IDs starting over from 0.
        //final DataStore dataStore = new DataStore();
        Database db = getDatabaseConnection();
        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });
<<<<<<< HEAD
       
=======

>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
        // GET route that returns all message titles and Ids.  All we do is get 
        // the data, embed it in a StructuredResponse, turn it into JSON, and 
        // return it.  If there's no data, we return "[]", so there's no need 
        // for error handling.
<<<<<<< HEAD
        Spark.get("/GetAllIdea", (request, response) -> {
            // int mSessID = Integer.parseInt(request.params("SessID"));
            // if(userSessPair.containsKey(mSessID))
            // {
=======
        Spark.get("/GetAllIdea/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, db.selectIdeasAll()));
<<<<<<< HEAD
            // }
            // return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
=======
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
        });

        // GET route that returns everything for a single row in the DataStore.
        // The ":id" suffix in the first parameter to get() becomes 
        // request.params("id"), so that we can get the requested row ID.  If 
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error.  Otherwise, we have an integer, and the only possible 
        // error is that it doesn't correspond to a row with data.
        Spark.get("/GetIdea/:IdeaId/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                int idx = Integer.parseInt(request.params("IdeaId"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                DataIdea data = db.selectIdea(idx);
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
<<<<<<< HEAD
        Spark.post("/insertIdea:/SessID", (request, response) -> {
=======
        Spark.post("/insertIdea/:SessID", (request, response) -> {
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
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
<<<<<<< HEAD
                int newId = db.insertIdea(req.mSubject, req.mMessage, 1);
=======
                int newId = db.insertIdea(req.mSubject, req.mMessage, mSessID);
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } 
                else {
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // POST route for adding a new element to the DataStore.  This will read
        // JSON from the body of the request, turn it into a SimpleRequest 
        // object, extract the title and message, insert them, and return the 
        // ID of the newly created row.
        Spark.post("/insertComment/:IdeaId/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // NB: if gson.Json fails, Spark will reply with status 500 Internal 
                // Server Error
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                // NB: even on error, we return 200, but with a JSON object that
                //     describes the error.
                int idx = Integer.parseInt(request.params("Ideaid"));
                response.status(200);
                response.type("application/json");
                // NB: createEntry checks for null title and message
<<<<<<< HEAD
                int newId = db.insertComment(req.mSubject, req.mMessage, 1, idx);
=======
                int newId = db.insertComment(req.mSubject, req.mMessage, mSessID, idx);
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } 
                else {
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
        
        // PUT route for updating a comment
        Spark.put("/updateIdea/:Ideaid/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.updateIdea(req.mMessage, idx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update a comment", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for updating a comment
        Spark.put("/updateComment/:id/:Ideaid/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                int cidx = Integer.parseInt(request.params("id"));
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.mUpdateComment(req.mMessage, idx, cidx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update a comment", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for liking a idea.
        Spark.put ("/likeIdea/:Ideaid/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
<<<<<<< HEAD
                DataLike mIdea = db.selectLikeIdea(idx, 1);
                int status = mIdea.mStatus;
                int result = db.likeIdea(idx,1,status);
=======
                DataLike mIdea = db.selectLikeIdea(idx, mSessID);
                int status = mIdea.mStatus;
                int result = db.likeIdea(idx,mSessID,status);
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to like row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for unliking a idea.
        Spark.put("/unlikeIdea/:Ideaid/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
<<<<<<< HEAD
                DataLike mIdea = db.selectLikeIdea(idx, 1);
                int status = mIdea.mStatus;
                int result = db.unlikeIdea(idx,1,status);
=======
                DataLike mIdea = db.selectLikeIdea(idx, mSessID);
                int status = mIdea.mStatus;
                int result = db.unlikeIdea(idx,mSessID,status);
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to unlike row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for liking a comment.
        Spark.put ("/likeComment/:Ideaid/:id/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                int cidx = Integer.parseInt(request.params("id"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
<<<<<<< HEAD
                DataLike mComment = db.selectLikeComment(idx, 1,cidx);
                int status = mComment.mStatus;
                int result = db.likeComment(idx,1,status,cidx);
=======
                DataLike mComment = db.selectLikeComment(idx, mSessID,cidx);
                int status = mComment.mStatus;
                int result = db.likeComment(idx,mSessID,status,cidx);
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to like comment ", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
<<<<<<< HEAD
=======
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for unliking a comment.
        Spark.put("/unlikeComment/:Ideaid/:id/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                int cidx = Integer.parseInt(request.params("id"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                DataLike mComment = db.selectLikeComment(idx, mSessID,cidx);
                int status = mComment.mStatus;
                int result = db.unlikeComment(idx,mSessID,status,cidx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to unlike comment ", null));
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
                int uuidAsInt = uuid.hashCode();
                userSessPair.put(uuidAsInt,userId);
                String myEmail = payload.getEmail().toString();
                String sub = myEmail.substring(myEmail.length() - 10);
                System.out.println(sub!="lehigh.edu");
                if(!sub.equals("lehigh.edu")){
                    return gson.toJson(new StructuredResponse("error", "Email is not from lehigh.edu domain", null));
                }
                int result = db.insertUser(payload.get("name").toString(), myEmail , "", "", "");
                if(result == -1)
                {
                    System.out.println("Failed to insertUser!");
                }
                response.status(200);
                response.redirect("/index.html");
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, uuidAsInt));
            }
            else{
                return gson.toJson(new StructuredResponse("error", "Invalid Google Token!", null));
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for unliking a comment.
        Spark.put("/unlikeComment/:Ideaid/:id/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("Ideaid"));
                int cidx = Integer.parseInt(request.params("id"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                DataLike mComment = db.selectLikeComment(idx, 1,cidx);
                int status = mComment.mStatus;
                int result = db.unlikeComment(idx,1,status,cidx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to unlike comment ", null));
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
                int uuidAsInt = uuid.hashCode();
                userSessPair.put(uuidAsInt,userId);
                String myEmail = payload.getEmail().toString();
                String sub = myEmail.substring(myEmail.length() - 10);
                System.out.println(sub!="lehigh.edu");
                if(!sub.equals("lehigh.edu")){
                    return gson.toJson(new StructuredResponse("error", "Email is not from lehigh.edu domain", null));
                }
                int result = db.insertUser(payload.get("name").toString(), myEmail , "", "", "");
                if(result == -1)
                {
                    System.out.println("Failed to insertUser!");
                }
                response.status(200);
                response.redirect("/index.html");
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, uuidAsInt));
            }
            else{
                return gson.toJson(new StructuredResponse("error", "Invalid Google Token!", null));
            }
        });
        
        //Update User Info
        Spark.put("/profile/:SessID/:name/:email/:genId/:sexOtn/:note", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                String mName = request.params("name");
                String mEmail = request.params("email");
                String mGenId = request.params("genId");
                String mSexOtn = request.params("sexOtn");
                String mNote = request.params("note");
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.updateUser(1,mName,mEmail,mGenId,mSexOtn,mNote);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update User", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "Successfully update User info", null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
       
         //Get User Info (myself)
         Spark.get("/getProfile/:User/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            String mUser = request.params("SessID");
            if(userSessPair.containsKey(mSessID))
            {
                String logUser = userSessPair.get(1);
                if(!logUser.equals(mUser)){
                    response.status(200);
                    response.type("application/json");
                    return gson.toJson(new StructuredResponse("ok", "", db.selectAnotherUser(1)));
                }
                else{
                    response.status(200);
                    response.type("application/json");
                    return gson.toJson(new StructuredResponse("ok", "", db.selectUser(1)));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
       
    }
    /**
     * Set up CORS headers for the OPTIONS verb, and for every response that the
     * server sends. This only needs to be called once.
     * 
     * @param origin  The server that is allowed to send requests to this server
     * @param methods The allowed HTTP verbs from the above origin
     * @param headers The headers that can be sent with a request from the above
     *                origin
     */
    private static void enableCORS(String origin, String methods, String headers) {        
        // Create an OPTIONS route that reports the allowed CORS headers and methods
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        // 'before' is a decorator, which will run before any
        // get/post/put/delete. In our case, it will put three extra CORS
        // headers into the response
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
        
        //Update User Info
        Spark.put("/profile/:SessID/:name/:email/:genId/:sexOtn/:note", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if(userSessPair.containsKey(mSessID))
            {
                String mName = request.params("name");
                String mEmail = request.params("email");
                String mGenId = request.params("genId");
                String mSexOtn = request.params("sexOtn");
                String mNote = request.params("note");
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.updateUser(mSessID,mName,mEmail,mGenId,mSexOtn,mNote);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update User", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "Successfully update User info", null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
       
         //Get User Info (myself)
         Spark.get("/getProfile/:SessID/:User", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            String mUser = request.params("SessID");
            if(userSessPair.containsKey(mSessID))
            {
                String logUser = userSessPair.get(mSessID);
                if(!logUser.equals(mUser)){
                    response.status(200);
                    response.type("application/json");
                    return gson.toJson(new StructuredResponse("ok", "", db.selectAnotherUser(mSessID)));
                }
                else{
                    response.status(200);
                    response.type("application/json");
                    return gson.toJson(new StructuredResponse("ok", "", db.selectUser(mSessID)));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
       
    }
}