package edu.lehigh.cse216.team_m.backend;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class that holds and stores all DataIdeas
 */
public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all user data in the database
     */
    private PreparedStatement mSelectUsersAll;

    /**
     * A prepared statement for getting all idea data in the database
     */
    private PreparedStatement mSelectIdeasAll;

    /**
     * A prepared statement for getting all comment data in the database
     */
    private PreparedStatement mSelectCommentsAll;

    /**
     * A prepared statement for getting all valid user data in the database
     */
    private PreparedStatement mSelectUsersValid;

    /**
     * A prepared statement for getting all valid idea data in the database
     */
    private PreparedStatement mSelectIdeasValid;

    /**
     * A prepared statement for getting all valid comment data in the database
     */
    private PreparedStatement mSelectCommentsValid;


    /**
     * A prepared statement for getting one user row from the database
     */
    private PreparedStatement mSelectUser;

    /**
     * A prepared statement for getting one idea row from the database
     */
    private PreparedStatement mSelectIdea;

    /**
     * A prepared statement for getting one comment row from the database
     */
    private PreparedStatement mSelectComment;

    /**
     * A prepared statement for getting one like row from the database
     */
    private PreparedStatement mSelectLikeIdea;

    /**
     * A prepared statement for getting one like row from the database
     */
    private PreparedStatement mSelectLikeComment;

    /**
     * A prepared statement for getting all comments for oen idea from the database
     */
    private PreparedStatement mViewComments;

    /**
     * A prepared statement for deleting a user row from the database
     */
    private PreparedStatement mDeleteUser;

    /**
     * A prepared statement for deleting a idea row from the database
     */
    private PreparedStatement mDeleteIdea;
    
    /**
     * A prepared statement for deleting a comment row from the database
     */
    private PreparedStatement mDeleteComment;

    /**
     * A prepared statement for inserting user into the database
     */
    private PreparedStatement mInsertUser;

    /**
     * A prepared statement for inserting idea into the database
     */
    private PreparedStatement mInsertIdea;

    /**
     * A prepared statement for inserting comment into the database
     */
    private PreparedStatement mInsertComment;

    /**
     * A prepared statement for inserting a comment that has a file into the database
     */
    private PreparedStatement mInsertCommentFile;

    /**
     * A prepared statement for inserting a comment that has a link attached into the database
     */
    private PreparedStatement mInsertCommentLink;

    /**
     * A prepared statement for inserting like into the database
     */
    private PreparedStatement mInsertLike;

    /**
     * A prepared statement for updating a single user row in the database
     */
    private PreparedStatement mUpdateUser;

    /**
     * A prepared statement for updating a single idea row in the database
     */
    private PreparedStatement mUpdateIdea;

    /**
     * A prepared statement for updating a single comment row in the database
     */
    private PreparedStatement mUpdateComment;  

    /**
     * A prepared statement for incrementing like on a single idea row in the database
     */
    private PreparedStatement mLikeIdea;

    /**
     * A prepared statement for incrementing like on a single comment row in the database
     */
    private PreparedStatement mLikeComment;

    /**
     * A prepared statement for decrementing like on a single idea row in the database
     */
    private PreparedStatement mUnlikeIdea;

    /**
     * A prepared statement for decrementing like on a single comment row in the database
     */
    private PreparedStatement mUnlikeComment;

    /**
     * A prepared statement for toggling valid on a single user row in the database
     */
    private PreparedStatement mToggleUser;

    /**
     * A prepared statement for toggling valid on a single idea row in the database
     */
    private PreparedStatement mToggleIdea;
    
    /**
     * A prepared statement for toggling valid on a single comment row in the database
     */
    private PreparedStatement mToggleComment;


    /**
     * A prepared statement for creating all the table in our database
     */
    private PreparedStatement mCreateTables;

    /**
     * A prepared statement for dropping all the tables in our database
     */
    private PreparedStatement mDropAll;

    private PreparedStatement mSelectUserEmail;

    private PreparedStatement mInsertIdeaLink;

    private PreparedStatement mInsertIdeaFile;
    /**
     * RowData is like a struct in C: we use it to hold data, and we allow 
     * direct access to its fields.  In the context of this Database, RowData 
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database.  RowData and the 
     * Database are tightly coupled: if one changes, the other should too.
     */

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

/**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String ip, String port, String user, String pass) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception
            db.mCreateTables = db.mConnection
                    .prepareStatement("CREATE TABLE users (userid SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL,"
                                + " email VARCHAR(250) NOT NULL, genderidentity VARCHAR(200) NOT NULL, sexualorientation VARCHAR(200)"
                                + " NOT NULL, note VARCHAR(500) NOT NULL, creationdate TIMESTAMP NOT NULL, valid boolean DEFAULT TRUE NOT NULL);"
                                
                                + " CREATE TABLE ideas (ideaid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, likes int DEFAULT 0 NOT NULL,"
                                + " comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,  valid boolean DEFAULT"
                                + " TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid));"
            
                                + " CREATE TABLE comments (commentid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, ideaid int NOT NULL, likes int"
                                + " DEFAULT 0 NOT NULL, comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,"
                                + " valid boolean DEFAULT TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid));"

                                + " CREATE TABLE likes (ideaid int NOT NULL, commentid int DEFAULT NULL, userid int NOT NULL, Status int DEFAULT 0 NOT NULL,"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid),"
                                + " FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(commentid) REFERENCES comments(commentid))");
            
            db.mDropAll = db.mConnection.prepareStatement("DROP TABLE users, ideas, comments, likes");

            // Standard CRUD operations
            db.mDeleteUser = db.mConnection.prepareStatement("DELETE FROM users WHERE userid = ?; DELETE FROM ideas WHERE userid = ?;"
                                                            + " DELETE FROM comments WHERE userid = ?; DELETE FROM likes WHERE userid = ?");
            db.mDeleteIdea = db.mConnection.prepareStatement("DELETE FROM ideas WHERE ideaid = ?; DELETE FROM comments WHERE ideaid = ?;"
                                                            + " DELETE FROM likes WHERE ideaid = ?");
            db.mDeleteComment = db.mConnection.prepareStatement("DELETE FROM comments WHERE ideaid = ? AND commentid = ?;"
                                                            + "DELETE FROM likes WHERE ideaid = ? AND commentid = ?;"
                                                            + "UPDATE ideas SET comments = comments -1 WHERE ideaid = ?");
            
            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, ?, ?, default)");
            db.mInsertIdea = db.mConnection.prepareStatement("INSERT INTO ideas VALUES (default, ?, ?, ?, default, default, ?, default)");
            db.mInsertIdeaFile = db.mConnection.prepareStatement("INSERT INTO ideas VALUES(default, ?,?,?, default, default, ?,default, ?,?)");
            db.mInsertIdeaLink = db.mConnection.prepareStatement("INSERT INTO ideas VALUES(default, ?,?,?, default, default, ?,default, ?,?)");
            db.mInsertComment = db.mConnection.prepareStatement("INSERT INTO comments VALUES (default, ?, ?, ?, ?, default, default, ?, default);"
                                                            + " UPDATE ideas SET comments = comments + 1 WHERE ideaid = ?");
            db.mInsertCommentFile = db.mConnection.prepareStatement("INSERT INTO files VALUES (?,default, default,?, ?, default, ?, default, default, ?, default)"
                                                            + "UPDATE ideas SET comments = comments + 1 WHERE ideaid = ?");
            db.mInsertCommentLink = db.mConnection.prepareStatement("INSERT INTO files VALUES (?,default,default,?, ?, default, ?, default, default, ?, default)"
                                                            + "UPDATE ideas SET comments = comments + 1 WHERE ideaid = ?");
            db.mInsertLike = db.mConnection.prepareStatement("INSERT INTO likes VALUES (?, ?, ?, default)");

            db.mSelectUsersAll = db.mConnection
                    .prepareStatement("SELECT userid, name, email, genderidentity, sexualorientation, note, creationdate, valid FROM users ORDER BY creationdate");
            db.mSelectIdeasAll = db.mConnection
                    .prepareStatement("SELECT ideaid, subject, message, userid, likes, comments, creationdate, valid FROM ideas ORDER BY creationdate");
            db.mSelectCommentsAll = db.mConnection
                    .prepareStatement("SELECT commentid, subject, message, userid, ideaid, likes, comments, creationdate, valid FROM comments ORDER BY creationdate");
            
            db.mSelectUsersValid = db.mConnection
                    .prepareStatement("SELECT userid, name, email, genderidentity, sexualorientation, note, creationdate, valid FROM users WHERE valid = true ORDER BY creationdate");
            db.mSelectIdeasValid = db.mConnection
                    .prepareStatement("SELECT ideaid, subject, message, userid, likes, comments, creationdate, valid FROM ideas WHERE valid = true ORDER BY creationdate");
            db.mSelectCommentsValid = db.mConnection
                    .prepareStatement("SELECT commentid, subject, message, userid, ideaid, likes, comments, creationdate, valid FROM comments WHERE valid = true ORDER BY creationdate");
            
            db.mSelectUser = db.mConnection.prepareStatement("SELECT * FROM users WHERE userid = ?");
            db.mSelectIdea = db.mConnection.prepareStatement("SELECT * FROM ideas WHERE ideaid = ?");
            db.mSelectComment = db.mConnection.prepareStatement("SELECT * FROM comments WHERE ideaid = ? AND commentid = ?");
            db.mSelectLikeIdea = db.mConnection.prepareStatement("SELECT * FROM likes WHERE ideaid = ? AND userid = ? AND commentid is NULL ");
            db.mSelectLikeComment = db.mConnection.prepareStatement("SELECT * FROM likes WHERE ideaid = ? AND userid = ? AND commentid = ? ");

            db.mViewComments = db.mConnection.prepareStatement("SELECT * FROM comments WHERE ideaid = ?");

            db.mUpdateUser = db.mConnection.prepareStatement("UPDATE users SET name = ?, email = ?, genderidentity = ?, sexualorientation = ?, note = ? WHERE userid = ?");
            db.mUpdateIdea = db.mConnection.prepareStatement("UPDATE ideas SET message = ? WHERE ideaid = ?");
            db.mUpdateComment = db.mConnection.prepareStatement("UPDATE comments SET message = ? WHERE ideaid = ? AND commentid = ?");

            db.mLikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes + 1 WHERE ideaid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid is NULL");
            db.mLikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes + 1 WHERE ideaid = ? AND commentid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid = ?");

            db.mUnlikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes - 1 WHERE ideaid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid is NULL ");
            db.mUnlikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes - 1 WHERE ideaid = ? AND commentid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid = ? ");

            db.mToggleUser = db.mConnection.prepareStatement("UPDATE users SET valid = NOT valid WHERE userid = ?; UPDATE ideas SET valid = NOT valid" 
                                                            + " WHERE userid = ?; UPDATE comments SET valid = NOT valid WHERE userid = ?;");
            db.mToggleIdea = db.mConnection.prepareStatement("UPDATE ideas SET valid = NOT valid WHERE ideaid = ?; UPDATE comments SET valid = NOT valid"
                                                            + " WHERE ideaid = ?;");
            db.mToggleComment = db.mConnection.prepareStatement("UPDATE comments SET valid = NOT valid WHERE ideaid = ? AND commentid = ?");
            db.mSelectUserEmail = db.mConnection.prepareStatement("SELECT userid, email FROM users where email=? ORDER by creationdate");
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
    * Get a fully-configured connection to the database
    * 
    * @param host The IP address or hostname of the database server
    * @param port The port on the database server to which connection requests
    *             should be sent
    * @param path The path to use, can be null
    * @param user The user ID to use when connecting
    * @param pass The password to use when connecting
    * 
    * @return A Database object, or null if we cannot connect properly
    */
    static Database getDatabase(String host, String port, String path, String user, String pass) {
        if( path==null || "".equals(path) ){
            path="/";
        }

        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            String dbUrl = "jdbc:postgresql://" + host + ':' + port + path;
            Connection conn = DriverManager.getConnection(dbUrl, user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            // SQL incorrectly. We really should have things like the table 
            // names as constants, and then build the strings for the statements
            // from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception
            db.mCreateTables = db.mConnection
                    .prepareStatement("CREATE TABLE users (userid SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL,"
                                + " email VARCHAR(250) NOT NULL, genderidentity VARCHAR(200) NOT NULL, sexualorientation VARCHAR(200)"
                                + " NOT NULL, note VARCHAR(500) NOT NULL, creationdate TIMESTAMP NOT NULL, valid boolean DEFAULT TRUE NOT NULL);"
                                
                                + " CREATE TABLE ideas (ideaid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, likes int DEFAULT 0 NOT NULL,"
                                + " comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,  valid boolean DEFAULT"
                                + " TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid));"
            
                                + " CREATE TABLE comments (commentid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, ideaid int NOT NULL, likes int"
                                + " DEFAULT 0 NOT NULL, comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,"
                                + " valid boolean DEFAULT TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid));"

                                + " CREATE TABLE likes (ideaid int NOT NULL, commentid int DEFAULT NULL, userid int NOT NULL, Status int DEFAULT 0 NOT NULL,"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid),"
                                + " FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(commentid) REFERENCES comments(commentid))");
            
            db.mDropAll = db.mConnection.prepareStatement("DROP TABLE users, ideas, comments, likes");

            // Standard CRUD operations
            db.mDeleteUser = db.mConnection.prepareStatement("DELETE FROM users WHERE userid = ?; DELETE FROM ideas WHERE userid = ?;"
                                                            + " DELETE FROM comments WHERE userid = ?; DELETE FROM likes WHERE userid = ?");
            db.mDeleteIdea = db.mConnection.prepareStatement("DELETE FROM ideas WHERE ideaid = ?; DELETE FROM comments WHERE ideaid = ?;"
                                                            + " DELETE FROM likes WHERE ideaid = ?");
            db.mDeleteComment = db.mConnection.prepareStatement("DELETE FROM comments WHERE ideaid = ? AND commentid = ?;"
                                                            + "DELETE FROM likes WHERE ideaid = ? AND commentid = ?;"
                                                            + "UPDATE ideas SET comments = comments -1 WHERE ideaid = ?");
            
            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, ?, ?, default)");
            db.mInsertIdea = db.mConnection.prepareStatement("INSERT INTO ideas VALUES (default, ?, ?, ?, default, default, ?, default)");
            db.mInsertIdeaFile = db.mConnection.prepareStatement("INSERT INTO ideas VALUES(default, ?,?,?, default, default, ?,default, ?,?)");
            db.mInsertIdeaLink = db.mConnection.prepareStatement("INSERT INTO ideas VALUES(default, ?,?,?, default, default, ?,default, ?,?)");
            db.mInsertComment = db.mConnection.prepareStatement("INSERT INTO comments VALUES (default, ?, ?, ?, ?, default, default, ?, default);"
            + " UPDATE ideas SET comments = comments + 1 WHERE ideaid = ?");
            db.mInsertCommentFile = db.mConnection.prepareStatement("INSERT INTO files VALUES (?,default, default,?, ?, default, ?, default, default, ?, default)"
            + "UPDATE ideas SET comments = comments + 1 WHERE ideaid = ?");
            db.mInsertCommentLink = db.mConnection.prepareStatement("INSERT INTO files VALUES (?,default,default,?, ?, default, ?, default, default, ?, default)"
            + "UPDATE ideas SET comments = comments + 1 WHERE ideaid = ?");
            db.mInsertLike = db.mConnection.prepareStatement("INSERT INTO likes VALUES (?, ?, ?, default)");

            db.mSelectUsersAll = db.mConnection
                    .prepareStatement("SELECT userid, name, email, genderidentity, sexualorientation, note, creationdate, valid FROM users ORDER BY creationdate");
            db.mSelectIdeasAll = db.mConnection
                    .prepareStatement("SELECT ideaid, subject, message, userid, likes, comments, creationdate, valid FROM ideas ORDER BY creationdate");
            db.mSelectCommentsAll = db.mConnection
                    .prepareStatement("SELECT commentid, subject, message, userid, ideaid, likes, comments, creationdate, valid FROM comments ORDER BY creationdate");
            
            db.mSelectUsersValid = db.mConnection
                    .prepareStatement("SELECT userid, name, email, genderidentity, sexualorientation, note, creationdate, valid FROM users WHERE valid = true ORDER BY creationdate");
            db.mSelectIdeasValid = db.mConnection
                    .prepareStatement("SELECT ideaid, subject, message, userid, likes, comments, creationdate, valid FROM ideas WHERE valid = true ORDER BY creationdate");
            db.mSelectCommentsValid = db.mConnection
                    .prepareStatement("SELECT commentid, subject, message, userid, ideaid, likes, comments, creationdate, valid FROM comments WHERE valid = true ORDER BY creationdate");
            
            db.mSelectUser = db.mConnection.prepareStatement("SELECT * FROM users WHERE userid = ?");
            db.mSelectIdea = db.mConnection.prepareStatement("SELECT * FROM ideas WHERE ideaid = ?");
            db.mSelectComment = db.mConnection.prepareStatement("SELECT * FROM comments WHERE ideaid = ? AND commentid = ?");
            db.mSelectLikeIdea = db.mConnection.prepareStatement("SELECT * FROM likes WHERE ideaid = ? AND userid = ? AND commentid is NULL ");
            db.mSelectLikeComment = db.mConnection.prepareStatement("SELECT * FROM likes WHERE ideaid = ? AND userid = ? AND commentid = ? ");

            db.mViewComments = db.mConnection.prepareStatement("SELECT * FROM comments WHERE ideaid = ?");

            db.mUpdateUser = db.mConnection.prepareStatement("UPDATE users SET name = ?, email = ?, genderidentity = ?, sexualorientation = ?, note = ? WHERE userid = ?");
            db.mUpdateIdea = db.mConnection.prepareStatement("UPDATE ideas SET message = ? WHERE ideaid = ?");
            db.mUpdateComment = db.mConnection.prepareStatement("UPDATE comments SET message = ? WHERE ideaid = ? AND commentid = ?");

            db.mLikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes + 1 WHERE ideaid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid is NULL");
            db.mLikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes + 1 WHERE ideaid = ? AND commentid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid = ?");

            db.mUnlikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes - 1 WHERE ideaid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid is NULL ");
            db.mUnlikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes - 1 WHERE ideaid = ? AND commentid = ?;"
                                                            + "UPDATE likes SET status = ? WHERE ideaid = ? AND userid = ? AND commentid = ? ");

            db.mToggleUser = db.mConnection.prepareStatement("UPDATE users SET valid = NOT valid WHERE userid = ?; UPDATE ideas SET valid = NOT valid" 
                                                            + " WHERE userid = ?; UPDATE comments SET valid = NOT valid WHERE userid = ?;");
            db.mToggleIdea = db.mConnection.prepareStatement("UPDATE ideas SET valid = NOT valid WHERE ideaid = ?; UPDATE comments SET valid = NOT valid"
                                                            + " WHERE ideaid = ?;");
            db.mToggleComment = db.mConnection.prepareStatement("UPDATE comments SET valid = NOT valid WHERE ideaid = ? AND commentid = ?");
            db.mSelectUserEmail = db.mConnection.prepareStatement("SELECT userid, email FROM users where email=? ORDER by creationdate");
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    } 

    /**
    * Get a fully-configured connection to the database
    * 
    * @param db_url The url to the database
    * @param port_default port to use if absent in db_url
    * 
    * @return A Database object, or null if we cannot connect properly
    */
    static Database getDatabase(String db_url, String port_default) {
        try {
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String port = dbUri.getPort() == -1 ? port_default : Integer.toString(dbUri.getPort());

            return getDatabase(host, port, path, username, password);
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }
    } 

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /**
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * 
     * @return The number of rows that were inserted
     */
    /**
     * Insert an user into users table in database
     * 
     * @param name the name of the new user
     * @param email the email for this new user
     * @param genId the gender identiy of the new user
     * @param sexOtn the sexual orientation of the new user
     * @param note the note/description of the new user
     * 
     * @return the number of rows that were inserted
     */
    int insertUser(String name, String email, String genId, String sexOtn, String note) {
        int userid = -1;
        ResultSet res;
        try {
            mInsertUser.setString(1, name);
            mInsertUser.setString(2, email);
            mInsertUser.setString(3, genId);
            mInsertUser.setString(4, sexOtn);
            mInsertUser.setString(5, note);
            mInsertUser.setTimestamp(6, new Timestamp(new Date().getTime()));
            res = mInsertUser.executeQuery();
            while(res.next()){
                userid = res.getInt("userid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userid;
    }

    /**
     * Insert an idea into the ideas table in database
     * 
     * @param subject the subject for the new row
     * @param message the message body for the new row
     * @param userid the id of the user who created the idea row
     * 
     * @return The number of rows that were inserted
     */
    int insertIdea(String subject, String message, int userid) {
        int count = 0;
        try {
            mInsertIdea.setString(1, subject);
            mInsertIdea.setString(2, message);
            mInsertIdea.setInt(3, userid);
            mInsertIdea.setTimestamp(4, new Timestamp(new Date().getTime()));
            count += mInsertIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    db.mInsertIdeaFile = db.mConnection.prepareStatement("INSERT INTO ideas VALUES(default, ?,?,?, default, default, ?,default, ?,?)");
    db.mInsertIdeaLink = db.mConnection.prepareStatement("INSERT INTO ideas VALUES(default, ?,?,?, default, default, ?,default, ?,?)");
    /**
     * Insert an idea with a file into the idea table in database
     */
    int insertIdeaFile(String subject, String message, int userid, String filePath, String fileType){
        int count =0;
        try{
            mInsertIdeaFile.setString(1,subject);
            mInsertIdeaFile.setString(2, message);
            mInsertIdeaFile.setInt(3, userid);
            mInsertIdeaFile.setTimestamp(4, new Timestamp(new Date().getTime()));
            mInsertIdeaFile.setString(5, filePath);
            mInsertIdeaFile.setString(6, fileType);
            count += mInsertIdeaFile.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert an idea with a link into the idea table in database
     */
    int insertIdeaLink(String subject, String message, int userid, String filePath, String fileType){
        int count =0;
        try{
            mInsertIdeaFile.setString(1,subject);
            mInsertIdeaFile.setString(2, message);
            mInsertIdeaFile.setInt(3, userid);
            mInsertIdeaFile.setTimestamp(4, new Timestamp(new Date().getTime()));
            mInsertIdeaFile.setString(5, filePath);
            mInsertIdeaFile.setString(6, fileType);
            count += mInsertIdeaFile.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return count;
    }
    /**
     * Insert a comment into comments table in the database
     * 
     * @param subject the subject for the new row
     * @param message the message body for the new row
     * @param userid the id of the user who created the idea row
     * @param ideaid the id of the idea the comment row is for
     * 
     * @return The number of rows that were inserted
     */
    int insertComment(String subject, String message, int userid, int ideaid) {
        int count = 0;
        try {
            mInsertComment.setString(1, subject);
            mInsertComment.setString(2, message);
            mInsertComment.setInt(3, userid);
            mInsertComment.setInt(4, ideaid);
            mInsertComment.setTimestamp(5, new Timestamp(new Date().getTime()));
            mInsertComment.setInt(6, ideaid);
            count += mInsertComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
 
    
    int insertCommentFile(String filePath, int ideaid, int userid, String fileDescription){
        int count =0;
        try{
            mInsertCommentFile.setString(1, filePath);
            mInsertCommentFile.setInt(2, userid);
            mInsertCommentFile.setString(3, fileDescription);
            mInsertCommentFile.setInt(4, ideaid);
            mInsertCommentFile.setTimestamp(5, new Timestamp(new Date().getTime()));
            count += mInsertCommentFile.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    int insertCommentLink(String fileType, String fileDescription, String filePath, int ideaid){
        int count = 0;
        try{
          mInsertCommentLink.setString(1, fileType);
        mInsertCommentLink.setString(2, fileDescription);
        mInsertCommentLink.setString(3, filePath);
        mInsertCommentLink.setInt(4, ideaid);
        mInsertCommentLink.setTimestamp(5, new Timestamp(new Date().getTime()));  
        count += mInsertCommentLink.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return count;
    }
    /**
     * Insert a like into the likes table in the database
     * 
     * @param ideaid the id of the idea for the new like row
     * @param commentid the id of the comment for the new like row
     * @param userid the id of the user who created the like row
     * 
     * @return The number of rows that were inserted
     */
    int insertLike(int ideaid, Object commentid, int userid) {
        int count = 0;
        try {
            mInsertLike.setInt(1, ideaid);
            mInsertLike.setObject(2, commentid);
            mInsertLike.setInt(3, userid);
            count += mInsertLike.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    /**
     * Query the database for a list of all user variables, userid, name, email, gender identity
     * sexual orientation, note, valid, and creation date
     * 
     * @return All user rows, as an ArrayList
     */
    ArrayList<DataUser> selectUsersAll() {
        ArrayList<DataUser> res = new ArrayList<DataUser>();
        try {
            ResultSet rs = mSelectUsersAll.executeQuery();
            while (rs.next()) {
                res.add(new DataUser(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        rs.getString("genderidentity"), rs.getString("sexualorientation"), rs.getString("note"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all idea variables, ideaid, subject, message, userid
     * likes, comments, valid, and creation date
     * 
     * @return All idea rows, as an ArrayList
     */
    ArrayList<DataIdea> selectIdeasAll() {
        ArrayList<DataIdea> res = new ArrayList<DataIdea>();
        try {
            ResultSet rs = mSelectIdeasAll.executeQuery();
            while (rs.next()) {
                res.add(new DataIdea(rs.getInt("ideaid"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all comment variables, commentid, subject, message, userid
     * likes, comments, valid, ideaid, and creation date
     * 
     * @return All Comment rows, as an ArrayList
     */
    ArrayList<DataComment> selectCommentsAll() {
        ArrayList<DataComment> res = new ArrayList<DataComment>();
        try {
            ResultSet rs = mSelectCommentsAll.executeQuery();
            while (rs.next()) {
                res.add(new DataComment(rs.getInt("commentid"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                        rs.getBoolean("valid"),  rs.getInt("ideaid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all valid user variables, userid, name, email, gender identity
     * sexual orientation, note, valid, and creation date
     * 
     * @return All user rows, as an ArrayList
     */
    ArrayList<DataUser> selectUsersValid() {
        ArrayList<DataUser> res = new ArrayList<DataUser>();
        try {
            ResultSet rs = mSelectUsersValid.executeQuery();
            while (rs.next()) {
                res.add(new DataUser(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        rs.getString("genderidentity"), rs.getString("sexualorientation"), rs.getString("note"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all valid idea variables, ideaid, subject, message, userid
     * likes, comments, valid, and creation date
     * 
     * @return All idea rows, as an ArrayList
     */
    ArrayList<DataIdea> selectIdeasValid() {
        ArrayList<DataIdea> res = new ArrayList<DataIdea>();
        try {
            ResultSet rs = mSelectIdeasValid.executeQuery();
            while (rs.next()) {
                res.add(new DataIdea(rs.getInt("ideaid"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Query the database for a list of all valid comment variables, commentid, subject, message, userid
     * likes, comments, valid, ideaid, and creation date
     * 
     * @return All Comment rows, as an ArrayList
     */
    ArrayList<DataComment> selectCommentsValid() {
        ArrayList<DataComment> res = new ArrayList<DataComment>();
        try {
            ResultSet rs = mSelectCommentsValid.executeQuery();
            while (rs.next()) {
                res.add(new DataComment(rs.getInt("commentid"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                        rs.getBoolean("valid"),  rs.getInt("ideaid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all comment variables, commentid, subject, message, userid
     * likes, comments, valid, ideaid, and creation date under a specific idea
     * 
     * @return All Comment rows for one idea, as an ArrayList
     */
    ArrayList<DataComment> viewComments(int ideaid) {
        ArrayList<DataComment> res = new ArrayList<DataComment>();
        try {
            mViewComments.setInt(1, ideaid);
            ResultSet rs = mViewComments.executeQuery();
            while (rs.next()) {
                res.add(new DataComment(rs.getInt("commentid"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                        rs.getBoolean("valid"),  rs.getInt("ideaid"), rs.getTimestamp("creationdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data for a specific user row, by ID
     * 
     * @param userid The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataUser selectUser(int userid) {
        DataUser res = null;
        try {
            mSelectUser.setInt(1, userid);
            ResultSet rs = mSelectUser.executeQuery();
            if (rs.next()) {
                res = new DataUser(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        rs.getString("genderidentity"), rs.getString("sexualorientation"), rs.getString("note"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Get all data for a specific user row, by ID
     * 
     * @param userid The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataUser selectAnotherUser(int userid) {
        DataUser res = null;
        try {
            mSelectUser.setInt(1, userid);
            ResultSet rs = mSelectUser.executeQuery();
            if (rs.next()) {
                res = new DataUser(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        null, null, rs.getString("note"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Get all data for a specific idea row, by ID
     * 
     * @param ideaid The id of the idea row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataIdea selectIdea(int ideaid) {
        DataIdea res = null;
        try {
            mSelectIdea.setInt(1, ideaid);
            ResultSet rs = mSelectIdea.executeQuery();
            if (rs.next()) {
                res = new DataIdea(rs.getInt("ideaid"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                        rs.getBoolean("valid"), rs.getTimestamp("creationdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific idea row, by ID
     * 
     * @param ideaid The id of the idea row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataComment selectComment(int ideaid, int commentid) {
        DataComment res = null;
        try {
            mSelectComment.setInt(1, ideaid);
            mSelectComment.setInt(2, commentid);
            ResultSet rs = mSelectComment.executeQuery();
            if (rs.next()) {
                res = new DataComment(rs.getInt("commentid"), rs.getString("subject"), rs.getString("message"),
                    rs.getInt("userid"), rs.getInt("likes"), rs.getInt("comments"), 
                    rs.getBoolean("valid"),  rs.getInt("ideaid"), rs.getTimestamp("creationdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Get all data for a specific like row, by ID, used specifically for likes for ideas
     * 
     * @param ideaid The id of the idea row being requested
     * @param userid The id of the user who the like belongs to 
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataLike selectLikeIdea(int ideaid, int userid) {
        DataLike res = null;
        try {
            mSelectLikeIdea.setInt(1, ideaid);
            mSelectLikeIdea.setInt(2, userid);
            ResultSet rs = mSelectLikeIdea.executeQuery();
            if (rs.next()) {
                res = new DataLike(rs.getInt("ideaid"), rs.getInt("userid"), (Integer)rs.getInt("commentid"),
                        rs.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Get all data for a specific like row, by ID, used specifically for likes for comments
     * 
     * @param ideaid The id of the idea row being requested
     * @param commentid The id of the comment row being requested
     * @param userid The id of who the like belongs to
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataLike selectLikeComment(int ideaid, int userid, int commentid) {
        DataLike res = null;
        try {
            mSelectLikeComment.setInt(1, ideaid);
            mSelectLikeComment.setObject(2, userid);
            mSelectLikeComment.setObject(3, commentid);
            ResultSet rs = mSelectLikeComment.executeQuery();
            if (rs.next()) {
                res = new DataLike(rs.getInt("ideaid"), rs.getInt("userid"), rs.getInt("commentid"),
                        rs.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a user by ID
     * 
     * @param userid The id of the user to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteUser(int userid) {
        int res = -1;
        try {
            mDeleteUser.setInt(1, userid);
            mDeleteUser.setInt(2, userid);
            mDeleteUser.setInt(3, userid);
            mDeleteUser.setInt(4, userid);
            res = mDeleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a idea by ID
     * 
     * @param id The id of the idea to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteIdea(int ideaid) {
        int res = -1;
        try {
            mDeleteIdea.setInt(1, ideaid);
            mDeleteIdea.setInt(2, ideaid);
            mDeleteIdea.setInt(3, ideaid);
            res = mDeleteIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a comment by ID
     * 
     * @param ideaid The id of the idea of the comment to delete
     * @param commentid The id of the comment to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteComment(int ideaid, int commentid) {
        int res = -1;
        try {
            mDeleteComment.setInt(1, ideaid);
            mDeleteComment.setInt(2, commentid);
            mDeleteComment.setInt(3, ideaid);
            mDeleteComment.setInt(4, commentid);
            mDeleteComment.setInt(5, ideaid);
            res = mDeleteComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the user informati0on for a user row in the database
     * 
     * @param userid the id of the user being updated
     * @param name the new user name
     * @param email the new user email
     * @param genId the new user gender identity
     * @param sexOtn the new user sexual orientation
     * @param note the new user note
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateUser(int userid, String name, String email, String genId, String sexOtn, String note) {
        int res = -1;
        try {
            mUpdateUser.setString(1, name);
            mUpdateUser.setString(2, email);
            mUpdateUser.setString(3, genId);
            mUpdateUser.setString(4, sexOtn);
            mUpdateUser.setString(5, note);
            mUpdateUser.setInt(6, userid);
            res = mUpdateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Updates the message for an ieda
     * 
     * @param message the updated message put into the database
     * @param ideaid the id of the idea being updated
     * @return
     */
    int updateIdea(String message, int ideaid) {
        int res = -1;
        try {
            mUpdateIdea.setString(1, message);
            mUpdateIdea.setInt(2, ideaid);
            res = mUpdateIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Updates the message for a comment
     * 
     * @param message the updated message put into the database
     * @param ideaid the id of the idea the comment is under
     * @param commentid the id of the comment being updated
     * @return
     */
    int mUpdateComment(String message, int ideaid, int commentid) {
        int res = -1;
        try {
            mUpdateComment.setString(1, message);
            mUpdateComment.setInt(2, ideaid);
            mUpdateComment.setInt(3, commentid);
            res = mUpdateComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Increments like count for idea row and sets like status in likes table to desirble amount 
     * 
     * @param ideaid The id of the idea being liked
     * @param userid The id of the user liking the idea
     * @param status The status of the like (0 = nuetral, 1 = Upvote, -1 = Downvote)
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int likeIdea(int ideaid, int userid, int status) {
        int res = -1;
        try {
            mLikeIdea.setInt(1, ideaid);
            mLikeIdea.setInt(2, status);
            mLikeIdea.setInt(3, ideaid);
            mLikeIdea.setInt(4, userid);
            res = mLikeIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Increments like count for comment row and sets like status in likes table to desirble amount 
     * 
     * @param ideaid The id of the idea being liked
     * @param userid The id of the user liking the idea
     * @param status The status of the like (0 = nuetral, 1 = Upvote, -1 = Downvote)
     * @param commentid The id of the comment being liked
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int likeComment(int ideaid, int userid, int status, int commentid) {
        int res = -1;
        try {
            mLikeComment.setInt(1, ideaid);
            mLikeComment.setInt(2, commentid);
            mLikeComment.setInt(3, status);
            mLikeComment.setInt(4, ideaid);
            mLikeComment.setInt(5, userid);
            mLikeComment.setInt(6, commentid);
            res = mLikeComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Decrements like count for idea row and sets like status in likes tablew to desirble amount 
     * 
     * @param ideaid The id of the idea being unliked
     * @param userid The id of the user unliking the idea
     * @param status The status of the like (0 = nuetral, 1 = Upvote, -1 = Downvote)
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int unlikeIdea(int ideaid, int userid, int status) {
        int res = -1;
        try {
            mUnlikeIdea.setInt(1, ideaid);
            mUnlikeIdea.setInt(2, status);
            mUnlikeIdea.setInt(3, ideaid);
            mUnlikeIdea.setInt(4, userid);
            res = mUnlikeIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Increments like count for comment row and sets like status in likes table to desirble amount 
     * 
     * @param ideaid The id of the idea being liked
     * @param userid The id of the user liking the idea
     * @param status The status of the like (0 = nuetral, 1 = Upvote, -1 = Downvote)
     * @param commentid The id of the comment being liked
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int unlikeComment(int ideaid, int userid, int status, int commentid) {
        int res = -1;
        try {
            mUnlikeComment.setInt(1, ideaid);
            mUnlikeComment.setInt(2, commentid);
            mUnlikeComment.setInt(3, status);
            mUnlikeComment.setInt(4, ideaid);
            mUnlikeComment.setInt(5, userid);
            mUnlikeComment.setInt(6, commentid);
            res = mUnlikeComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Toggles the validity of the user, its ideas, and comments
     * 
     * @param userid The id of the user being toggled
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int toggleUser(int userid) {
        int res = -1;
        try {
            mToggleUser.setInt(1, userid);
            mToggleUser.setInt(2, userid);
            mToggleUser.setInt(3, userid);
            res = mToggleUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Toggles the validity of the idea and comments
     * 
     * @param ideaid The id of the idea being toggled
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int toggleIdea(int ideaid) {
        int res = -1;
        try {
            mToggleIdea.setInt(1, ideaid);
            mToggleIdea.setInt(2, ideaid);
            res = mToggleIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Toggles the validity of the comment
     * 
     * @param ideaid The id of the idea of the comment
     * @param commentid the id of the comment being toggled
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int toggleComment(int ideaid, int commentid) {
        int res = -1;
        try {
            mToggleComment.setInt(1, ideaid);
            mToggleComment.setInt(2, commentid);
            res = mToggleComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    ResultSet selectUserEmail(String email) {
        ResultSet res = null;
        try {
            mSelectUserEmail.setString(1, email);
            res = mSelectUserEmail.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Create tblData.  If it already exists, this will print an error
     */
    void createTables() {
        try {
            mCreateTables.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTables() {
        try {
            mDropAll.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}