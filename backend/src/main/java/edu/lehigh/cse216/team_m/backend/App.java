package edu.lehigh.cse216.team_m.backend;

import spark.Spark;
import com.google.gson.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.sql.ResultSet;
//  import org.omg.CORBA.CODESET_INCOMPATIBLE;

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

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import com.google.api.client.http.FileContent;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

//MemCache
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import java.lang.InterruptedException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import java.util.Base64;


import com.google.api.client.http.ByteArrayContent;
import java.io.FileInputStream;
/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {
    private static final String DEFAULT_PORT_DB = "5432";
    private static final int DEFAULT_PORT_SPARK = 4567;

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Buzz App";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make
     * it a single
     * globally shared instance across your application.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    // private static final List<String> SCOPES =
    // Collections.singletonList(DriveScopes.DRIVE);
    private static final String CLIENT_SECRET_FILE_NAME = "/client_secret.json";

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String FOLDER_ID = "13JAUMDXxMBgiCmx3-9g3jB6Tz-OVP9HJ";
    /** Global instance of the HTTP transport. */
    // private static HttpTransport HTTP_TRANSPORT;

    /** Global Drive API client. */
    private static Drive _driveService;

    /**
     * Get a fully-configured connection to the database, or exit immediately
     * Uses the Postgres configuration from environment variables
     * 
     * NB: now when we shutdown the server, we no longer lose all data
     * 
     * @return null on failure, otherwise configured database object
     */
    private static Database getDatabaseConnection() {
        if (System.getenv("DATABASE_URL") != null) {
            return Database.getDatabase(System.getenv("DATABASE_URL"), DEFAULT_PORT_DB);
        }
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";

        Map<String, String> env = System.getenv();
        // String ip = env.get("POSTGRES_IP");
        // String port = env.get("POSTGRES_PORT");
        // String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        // String ip = env.get("POSTGRES_IP");
        // String port = env.get("POSTGRES_PORT");
        // String user = env.get("POSTGRES_USER");
        // String pass = env.get("POSTGRES_PASS");

        return Database.getDatabase(ip, port, "", user, pass);
    }

    //
    /**
     * Get an integer environment variable if it exists, and otherwise return the
     * default value.
     * 
     * @envar The name of the environment variable to get.
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

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = App.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        // returns an authorized Credential object.
        return credential;
    }

    /**
     * Upload new file.
     *
     * @return Inserted file metadata if successful, {@code null} otherwise.
     * @throws IOException if service account credentials file not found.
     */
    public static String uploadBasic() throws IOException {
        // Load pre-authorized user credentials from the environment.
        // TODO(developer) - See https://developers.google.com/identity for
        // guides on implementing OAuth2 for your application.
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Drive samples")
                .build();
        // Upload file photo.jpg on drive.
        File fileMetadata = new File();
        fileMetadata.setName("photo.jpg");
        // File's content.
        java.io.File filePath = new java.io.File(" C:\\Users\\nguye\\CSE216\\team_m\\photo.jpg");
        // Specify media type and file-path for file.
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }

    /**
     * Uploads file to google drive
     * 
     * @param fileData is the binary representation of the file
     * @param fileName is name of the file
     */
    public static void uploadFileToGoogleDrive(String fileData, String filename)
            throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // Load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        // Set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .build();

        // Authorize
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        // Build the Drive object
        Drive service = new Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Decode the binary string to a byte array
        byte[] fileBytes = Base64.getDecoder().decode(fileData);

        // Create the metadata for the file
        File fileMetadata = new File();
        fileMetadata.setName(filename);
    

        // Create a file content with the data
        ByteArrayContent mediaContent = new ByteArrayContent("application/octet-stream", fileBytes);

        // Insert the file into Google Drive
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        System.out.println("File ID: " + file.getId());
    }

    /**
     * @author David
     * @version 4/2/2023
     */
    // public static <GoogleSignInResponse> void main(String[] args) {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Store the exist < UUID as Int , user > infomation
        HashMap<Integer, Integer> userSessPair = new HashMap<Integer, Integer>();

        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", DEFAULT_PORT_SPARK));

        // Set up the location for serving static files. If the STATIC_LOCATION
        // environment variable is set, we will serve from it. Otherwise, serve
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
        // NB: Gson is thread-safe. See
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        final Gson gson = new Gson();
        // NB: every time we shut down the server, we will lose all data, and
        // every time we start the server, we'll have an empty dataStore,
        // with IDs starting over from 0.
        // final DataStore dataStore = new DataStore();
        Database db = getDatabaseConnection();

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        _driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });

        // GET route that returns all message titles and Ids. All we do is get
        // the data, embed it in a StructuredResponse, turn it into JSON, and
        // return it. If there's no data, we return "[]", so there's no need
        // for error handling.
        Spark.get("/GetAllIdea/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, db.selectIdeasValid()));
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // GET route that returns everything for a single row in the DataStore.
        // The ":CommentID" suffix in the first parameter to get() becomes
        // request.params("CommentID"), so that we can get the requested row ID. If
        // ":CommentID" isn't a number, Spark will reply with a status 500 Internal
        // Server Error. Otherwise, we have an integer, and the only possible
        // error is that it doesn't correspond to a row with data.
        Spark.get("/GetIdea/:IdeaID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                int idx = Integer.parseInt(request.params("IdeaID"));
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

        Spark.get("/GetComment/:IdeaId", (request, response) -> {

            int idx = Integer.parseInt(request.params("IdeaId"));
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("ok", null, db.viewComments(idx)));

        });

        // POST route for adding a new element to the DataStore. This will read
        // JSON from the body of the request, turn it into a SimpleRequest
        // object, extract the title and message, insert them, and return the
        // ID of the newly created row.
        Spark.post("/insertIdea/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // NB: if gson.Json fails, Spark will reply with status 500 Internal
                // Server Error
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                // NB: even on error, we return 200, but with a JSON object that
                // describes the error.
                response.status(200);
                response.type("application/json");
                // NB: createEntry checks for null title and message
                int newId = db.insertIdea(req.mSubject, req.mMessage, userSessPair.get(mSessID));
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
        /**
         * Route for inserting links and files into an idea
         */
        Spark.post("/insertIdea:/:SessID/:MediaLink", (request, response) -> {
            String res = request.params("SessID");
            System.out.print(res);
            int mSessID = Integer.parseInt(res);
            String mediaType = request.params("MediaLink");
            if (userSessPair.containsKey(mSessID)) {
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                response.status(200);
                response.type("application/json");
                if (mediaType.toLowerCase().contains("https://")) {

                    response.status(200);
                    response.type("application/json");

                    int newId = db.insertIdeaLink(req.mSubject, req.mMessage, userSessPair.get(mSessID), req.mfilePath,
                            req.mfileType);
                    if (newId == -1) {
                        return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } else {
                        return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                    }
                } else if (mediaType.toLowerCase().contains(".jpeg") | mediaType.toLowerCase().contains(".jpg")
                        | mediaType.toLowerCase().contains(".mp4")) {

                    response.status(200);
                    response.type("application/json");

                    int newId = db.insertIdeaFile(req.mSubject, req.mMessage, userSessPair.get(mSessID), req.mfilePath,
                            req.mfileType);
                    if (newId == -1) {
                        return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } else {
                        String binaryData = req.mfileType;
                        String fileName = req.mSubject;
                        uploadFileToGoogleDrive(binaryData, fileName);
                        return gson.toJson(new StructuredResponse("ok", "" + newId, null));

                    }
                }

            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });
        // POST route for adding a new element to the DataStore. This will read
        // JSON from the body of the request, turn it into a SimpleRequest
        // object, extract the title and message, insert them, and return the
        // ID of the newly created row.
        Spark.post("/insertComment/:IdeaID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                int idx = Integer.parseInt(request.params("IdeaID"));
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                response.status(200);
                response.type("application/json");
                int newId = db.insertComment(req.mSubject, req.mMessage, userSessPair.get(mSessID), idx);
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // POST route for adding a new element to the DataStore. This will read
        // JSON from the body of the request, turn it into a SimpleRequest
        // object, extract the title and message, insert them, and return the
        // ID of the newly created row.
        Spark.post("/insertComment/:IdeaId/:MediaLink", (request, response) -> {
            String mediaType = request.params("MediaLink");
            int idx = Integer.parseInt(request.params("Ideaid"));
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            // NB: even on error, we return 200, but with a JSON object that
            // describes the error.

            if (mediaType.toLowerCase().contains("https://")) {

                response.status(200);
                response.type("application/json");

                int newId = db.insertCommentLink("Link", req.mfilePath, req.mfileType, idx);
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));
                }
            } else if (mediaType.toLowerCase().contains(".jpeg") | mediaType.toLowerCase().contains(".jpg")
                    | mediaType.toLowerCase().contains(".mp4")) {

                response.status(200);
                response.type("application/json");

                int newId = db.insertCommentFile(req.mfilePath, idx, 1, req.mfileType);
                if (newId == -1) {
                    return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } else {
                    String binaryData = req.mfileType;
                    String fileName = req.mSubject;
                    uploadFileToGoogleDrive(binaryData, fileName);
                    return gson.toJson(new StructuredResponse("ok", "" + newId, null));

                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for updating a comment
        Spark.put("/updateIdea/:IdeaID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                String ideaid = request.params("IdeaID");
                int idx = Integer.parseInt(ideaid);
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
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
        Spark.put("/updateComment/:IdeaID/:CommentID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("IdeaID"));
                int cidx = Integer.parseInt(request.params("CommentID"));
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int result = db.updateComment(req.mMessage, idx, cidx);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update a comment", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, result));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for liking a idea.
        Spark.put("/likeIdea/:IdeaID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("IdeaID"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int update = -1;
                DataLike like = db.selectLikeIdea(idx, userSessPair.get(mSessID));
                if (like == null) {
                    int insert = db.insertLike(idx, null, userSessPair.get(mSessID));
                    if (insert == -1)
                        return gson.toJson(new StructuredResponse("error", "unable to like row " + idx, null));
                    like = db.selectLikeIdea(idx, userSessPair.get(mSessID));
                } else if ((like.mStatus == 0) || (like.mStatus == -1)) {
                    update = db.likeIdea(idx, userSessPair.get(mSessID), 1);
                } else if (like.mStatus == 1) {
                    update = db.unlikeIdea(idx, userSessPair.get(mSessID), 0);
                }
                if (update == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to like row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, update));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for unliking a idea.
        Spark.put("/unlikeIdea/:IdeaID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("IdeaID"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int update = -1;
                DataLike like = db.selectLikeIdea(idx, userSessPair.get(mSessID));
                if (like == null) {
                    int insert = db.insertLike(idx, null, userSessPair.get(mSessID));
                    if (insert == -1)
                        return gson.toJson(new StructuredResponse("error", "unable to unlike row " + idx, null));
                    like = db.selectLikeIdea(idx, userSessPair.get(mSessID));
                } else if ((like.mStatus == 0) || (like.mStatus == 1)) {
                    update = db.unlikeIdea(idx, userSessPair.get(mSessID), -1);
                } else if (like.mStatus == -1) {
                    update = db.likeIdea(idx, userSessPair.get(mSessID), 0);
                }
                if (update == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to unlike row " + idx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, update));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for liking a comment.
        Spark.put("/likeComment/:IdeaID/:CommentID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("IdeaID"));
                int cidx = Integer.parseInt(request.params("CommentID"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int update = -1;
                DataLike like = db.selectLikeComment(idx, userSessPair.get(mSessID), cidx);
                if (like == null) {
                    int insert = db.insertLike(idx, cidx, userSessPair.get(mSessID));
                    if (insert == -1)
                        return gson.toJson(new StructuredResponse("error", "unable to like row " + cidx, null));
                    like = db.selectLikeComment(idx, userSessPair.get(mSessID), cidx);
                } else if ((like.mStatus == 0) || (like.mStatus == -1)) {
                    update = db.likeComment(idx, userSessPair.get(mSessID), 1, cidx);
                } else if (like.mStatus == 1) {
                    update = db.unlikeComment(idx, userSessPair.get(mSessID), 0, cidx);
                }
                if (update == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to like comment " + cidx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, update));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // PUT route for unliking a comment.
        Spark.put("/unlikeComment/:IdeaID/:CommentID/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                // If we can't get an ID or can't parse the JSON, Spark will send
                // a status 500
                int idx = Integer.parseInt(request.params("IdeaID"));
                int cidx = Integer.parseInt(request.params("CommentID"));
                // ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                int update = -1;
                DataLike like = db.selectLikeComment(idx, userSessPair.get(mSessID), cidx);
                if (like == null) {
                    int insert = db.insertLike(idx, cidx, userSessPair.get(mSessID));
                    if (insert == -1)
                        return gson.toJson(new StructuredResponse("error", "unable to unlike row " + cidx, null));
                    like = db.selectLikeComment(idx, userSessPair.get(mSessID), cidx);
                } else if ((like.mStatus == 0) || (like.mStatus == 1)) {
                    update = db.unlikeComment(idx, userSessPair.get(mSessID), -1, cidx);
                } else if (like.mStatus == -1) {
                    update = db.likeComment(idx, userSessPair.get(mSessID), 0, cidx);
                }
                if (update == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to unlike row " + cidx, null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", null, update));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        Spark.post("/login", (request, response) -> {
            String CLIENT_ID = "926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com";

            JsonFactory JSON_FACTORY = new JacksonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                    .setAudience(Collections.singletonList(CLIENT_ID)).build();
            GoogleIdToken idToken = verifier.verify(request.body());
            System.out.println(request.body());
            System.out.println(idToken);
            if (idToken != null) {
                int result = -1;
                Payload payload = idToken.getPayload();
                String myEmail = payload.getEmail().toString();
                String[] tokens = myEmail.split("@");
                int sid;
                if (!tokens[1].equals("lehigh.edu"))
                    return gson.toJson(new StructuredResponse("error", "Email is not from lehigh.edu domain", null));
                String usertoken = payload.getSubject();
                UUID uuid = UUID.randomUUID();
                sid = uuid.hashCode();
                ResultSet res = db.selectUserEmail(myEmail);
                if ((res.next()) == false) {
                    result = db.insertUser(payload.get("name").toString(), myEmail, "", "", "");
                    if (result == -1)
                        System.out.println("Failed to insert user!");
                } else
                    result = res.getInt("userid");
                userSessPair.put(sid, result);
                System.out.printf("\n\n%s\n%s\n%s\n", usertoken, sid, userSessPair.get(sid));
                DataUser user = db.selectUser(result);
                if (user.mValid == false) {
                    return gson.toJson(new StructuredResponse("error",
                            "You have been banned from the micky house club house", null));
                }
                String data[] = { Integer.toString(sid), Integer.toString(user.mId), user.mGenId, user.mSexOtn,
                        user.mNote };
                response.status(200);
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", null, data));
            } else {
                return gson.toJson(new StructuredResponse("error", "Invalid Google Token!", null));
            }
        });

        // Update User Info
        Spark.put("/profile/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            if (userSessPair.containsKey(mSessID)) {
                UserRequest req = gson.fromJson(request.body(), UserRequest.class);
                response.status(200);
                response.type("application/json");
                System.out.printf("\nname: %s\nemail: %s\ngen: %s\nsex: %s\nnote: %s\n", req.mName, req.mEmail,
                        req.mGenId, req.mSexOtn, req.mNote);
                // ensure status 200 OK, with a MIME type of JSON
                int result = db.updateUser(userSessPair.get(mSessID), req.mName, req.mEmail, req.mGenId, req.mSexOtn,
                        req.mNote);
                if (result == -1) {
                    return gson.toJson(new StructuredResponse("error", "unable to update User", null));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "Successfully update User info", null));
                }
            }
            return gson.toJson(new StructuredResponse("error", "Invalid SessID", null));
        });

        // Get User Info (myself)
        Spark.get("/getProfile/:User/:SessID", (request, response) -> {
            int mSessID = Integer.parseInt(request.params("SessID"));
            int profileid = Integer.parseInt(request.params("User"));
            if (userSessPair.containsKey(mSessID)) {
                response.status(200);
                response.type("application/json");
                return gson.toJson(new StructuredResponse("ok", "", db.selectUser(profileid)));
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
    }
}