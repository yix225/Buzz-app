package edu.lehigh.cse216.team_m.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;

/**
 * Class that holds and stores all DataIdeas
 */
public class Database {
    /**
     * The connection to the database. When there is no connection, it should
     * be null. Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all User data in the database
     */
    private PreparedStatement mSelectUsersAll;

    /**
     * A prepared statement for getting all Idea data in the database
     */
    private PreparedStatement mSelectIdeasAll;

    /**
     * A prepared statement for getting all Comment data in the database
     */
    private PreparedStatement mSelectCommentsAll;

    /**
     * A prepared statement for getting all Like data in the database
     */
    private PreparedStatement mSelectLikesAll;

    /**
     * A prepared statement for getting one User row from the database
     */
    private PreparedStatement mSelectUser;

    /**
     * A prepared statement for getting one Idea row from the database
     */
    private PreparedStatement mSelectIdea;

    /**
     * A prepared statement for getting one Comment row from the database
     */
    private PreparedStatement mSelectComment;

    /**
     * A prepared statement for getting one Like row from the database
     */
    private PreparedStatement mSelectLike;

    /**
     * A prepared statement for deleting a User row from the database
     */
    private PreparedStatement mDeleteUser;

    /**
     * A prepared statement for deleting a Idea row from the database
     */
    private PreparedStatement mDeleteIdea;
    
    /**
     * A prepared statement for deleting a Comment row from the database
     */
    private PreparedStatement mDeleteComment;

    /**
     * A prepared statement for deleting a Like row from the database
     */
    private PreparedStatement mDeleteLike;

    /**
     * A prepared statement for inserting User into the database
     */
    private PreparedStatement mInsertUser;

    /**
     * A prepared statement for inserting Idea into the database
     */
    private PreparedStatement mInsertIdea;

    /**
     * A prepared statement for inserting Comment into the database
     */
    private PreparedStatement mInsertComment;

    /**
     * A prepared statement for inserting Like into the database
     */
    private PreparedStatement mInsertLike;

    /**
     * A prepared statement for updating a single User row in the database
     */
    private PreparedStatement mUpdateUser;

    /**
     * A prepared statement for updating a single Idea row in the database
     */
    private PreparedStatement mUpdateIdea;

    /**
     * A prepared statement for updating a single Comment row in the database
     */
    private PreparedStatement mUpdateComment;

    /**
     * A prepared statement for updating a single Like row in the database
     */
    private PreparedStatement mUpdateLike;

    /**
     * A prepared statement for incrementing like on a single Idea row in the database
     */
    private PreparedStatement mLikeIdea;

    /**
     * A prepared statement for incrementing like on a single Comment row in the database
     */
    private PreparedStatement mLikeComment;

    /**
     * A prepared statement for decrementing like on a single Idea row in the database
     */
    private PreparedStatement mUnlikeIdea;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mUnlikeComment;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mValidateUser;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mInvalidateUser;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mValidateIdea;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mInvalidateIdea;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mValidateComment;

    /**
     * A prepared statement for decrementing like on a single Comment row in the database
     */
    private PreparedStatement mInvalidateComment;

    /**
     * A prepared statement for creating the users table in our database
     */
    private PreparedStatement mCreateUsersTable;

    /**
     * A prepared statement for creating the ideas table in our database
     */
    private PreparedStatement mCreateIdeasTable;

    /**
     * A prepared statement for creating the comments table in our database
     */
    private PreparedStatement mCreateCommentsTable;

    /**
     * A prepared statement for creating the likes table in our database
     */
    private PreparedStatement mCreateLikesTable;

    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mDropUsers;

    /**
     * A prepared statement for dropping the ideas table in our database
     */
    private PreparedStatement mDropIdeas;

    /**
     * A prepared statement for dropping the comments table in our database
     */
    private PreparedStatement mDropComments;

    /**
     * A prepared statement for dropping the likes table in our database
     */
    private PreparedStatement mDropLikes;

    /**
     * A prepared statement for dropping all the tables in our database
     */
    private PreparedStatement mDropAll;

    /**
     * RowData is like a struct in C: we use it to hold data, and we allow
     * direct access to its fields. In the context of this Database, RowData
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database. RowData and the
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
     * @param port The port on the database server to which connection requests should be sent
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

        // Attempt to create all of our prepared statements. If any of these
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            // SQL incorrectly. We really should have things like "tblData"
            // as constants, and then build the strings for the statements
            // from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table
            // creation/deletion, so multiple executions will cause an exception
            db.mCreateUsersTable = db.mConnection
                    .prepareStatement("CREATE TABLE users (userid SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL,"
                                + " email VARCHAR(250) NOT NULL, genderidentity VARCHAR(200) NOT NULL, sexualorientation VARCHAR(200)"
                                + " NOT NULL, note VARCHAR(500) NOT NULL, creationdate TIMESTAMP NOT NULL, valid boolean DEFAULT TRUE NOT NULL,)");

            db.mCreateIdeasTable = db.mConnection
                    .prepareStatement("CREATE TABLE ideas (ideaid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, likes int DEFAULT 0 NOT NULL,"
                                + " comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,  valid boolean DEFAULT"
                                + " TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid))");
            
            db.mCreateCommentsTable = db.mConnection
                    .prepareStatement("CREATE TABLE comments (commentid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, ideaid int NOT NULL, likes int"
                                + " DEFAULT 0 NOT NULL, comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,"
                                + " valid boolean DEFAULT TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid))");

            db.mCreateLikesTable = db.mConnection
                    .prepareStatement("CREATE TABLE likes (ideaid int NOT NULL, commentid int, userid int NOT NULL, Status int DEFAULT 0 NOT NULL,"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid),"
                                + " FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(commentid) REFERENCES comments(commentid))");
            
            // Drop Operations
            db.mDropUsers = db.mConnection.prepareStatement("DROP TABLE users CASCADE");
            db.mDropIdeas = db.mConnection.prepareStatement("DROP TABLE ideas CASCADE"); 
            db.mDropComments = db.mConnection.prepareStatement("DROP TABLE comments CASCADE");
            db.mDropLikes = db.mConnection.prepareStatement("DROP TABLE likes");
            db.mDropAll = db.mConnection.prepareStatement("DROP TABLE users, ideas, comments, likes");

            // Standard CRUD operations
            db.mDeleteUser = db.mConnection.prepareStatement("DELETE FROM users WHERE userid = ?");
            db.mDeleteIdea = db.mConnection.prepareStatement("DELETE FROM ideas WHERE ideaid = ?");
            db.mDeleteComment = db.mConnection.prepareStatement("DELETE FROM comments WHERE commentid = ? AND ideaid = ?");
            db.mDeleteLike = db.mConnection.prepareStatement("DELETE FROM likes WHERE ideaid = ? AND commentid = ? AND userid = ?");

            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, ?, ?, default)");
            db.mInsertIdea = db.mConnection.prepareStatement("INSERT INTO ideas VALUES (default, ?, ?, ?, default, default, ?, default)");
            db.mInsertComment = db.mConnection.prepareStatement("INSERT INTO comments VALUES (default, ?, ?, ?, ?, default, default, ?, default);"
                                                            + " UPDATE ideas SET comments = comment + 1 WHERE ideaid = ?");
            db.mInsertLike = db.mConnection.prepareStatement("INSERT INTO likes VALUES (?, ?, ?, default)");

            db.mSelectUsersAll = db.mConnection
                    .prepareStatement("SELECT userid, name, email, genderidentity, sexualorientation, note, creationdate, valid FROM users ORDER BY creationdate");
            db.mSelectIdeasAll = db.mConnection
                    .prepareStatement("SELECT ideaid, subject, message, userid, likes, comments, creationdate, valid FROM ideas ORDER BY creationdate");
            db.mSelectCommentsAll = db.mConnection
                    .prepareStatement("SELECT commentid, subject, message, userid, ideaid, likes, comments, creationdate, valid FROM comments ORDER BY creationdate");
            db.mSelectLikesAll = db.mConnection
                    .prepareStatement("SELECT ideaid, commentid, userid, status FROM likes");
            
            db.mSelectUser = db.mConnection.prepareStatement("SELECT * FROM users WHERE userid = ?");
            db.mSelectIdea = db.mConnection.prepareStatement("SELECT * FROM ideas WHERE ideaid = ?");
            db.mSelectComment = db.mConnection.prepareStatement("SELECT * FROM comments WHERE commentid = ? AND ideaid = ?");
            db.mSelectLike = db.mConnection.prepareStatement("SELECT * FROm likes WHERE ideaid = ? AND commentid = ? AND userid = ?");

            db.mUpdateUser = db.mConnection.prepareStatement("UPDATE users SET name = ?, email = ?, genderidentity = ?, sexualorientation = ?, note = ?, WHERE userid = ?");
            db.mUpdateIdea = db.mConnection.prepareStatement("UPDATE ideas SET message = ? WHERE ideaid = ?");
            db.mUpdateComment = db.mConnection.prepareStatement("UPDATE comments SET message = ? WHERE commentid = ? AND ideaid = ?");
            db.mUpdateLike = db.mConnection.prepareStatement("UPDATE likes SET status = ? WHERE ideaid = ? AND commentid = ? AND userid = ?");

            db.mLikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes + 1 WHERE ideaid = ?");
            db.mLikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes + 1 WHERE commentid = ? AND ideaid");

            db.mUnlikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes - 1 WHERE ideaid = ?");
            db.mUnlikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes - 1 WHERE commentid = ? AND ideaid = ?");
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
     * @param port The port on the database server to which connection requests should be sent
     * @param path The path to use, can be null
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String host, String port, String path, String user, String pass) {
        if (path == null || "".equals(path)) {
            path = "/";
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

        // Attempt to create all of our prepared statements. If any of these
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            // SQL incorrectly. We really should have things like the table 
            // names as constants, and then build the strings for the statements
            // from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table
            // creation/deletion, so multiple executions will cause an exception
            db.mCreateUsersTable = db.mConnection
                    .prepareStatement("CREATE TABLE users (userid SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL,"
                                + " email VARCHAR(250) NOT NULL, genderidentity VARCHAR(200) NOT NULL, sexualorientation VARCHAR(200)"
                                + " NOT NULL, note VARCHAR(500) NOT NULL, creationdate TIMESTAMP NOT NULL, valid boolean DEFAULT TRUE NOT NULL,)");

            db.mCreateIdeasTable = db.mConnection
                    .prepareStatement("CREATE TABLE ideas (ideaid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, likes int DEFAULT 0 NOT NULL,"
                                + " comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,  valid boolean DEFAULT"
                                + " TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid))");
            
            db.mCreateCommentsTable = db.mConnection
                    .prepareStatement("CREATE TABLE comments (commentid SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL,"
                                + " message VARCHAR(500) NOT NULL, userid int NOT NULL, ideaid int NOT NULL, likes int"
                                + " DEFAULT 0 NOT NULL, comments int DEFAULT 0 NOT NULL, creationdate TIMESTAMP NOT NULL,"
                                + " valid boolean DEFAULT TRUE NOT NULL, FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid))");

            db.mCreateLikesTable = db.mConnection
                    .prepareStatement("CREATE TABLE likes (ideaid int NOT NULL, commentid int, userid int NOT NULL, Status int DEFAULT 0 NOT NULL,"
                                + " FOREIGN KEY(ideaid) REFERENCES ideas(ideaid),"
                                + " FOREIGN KEY(userid) REFERENCES users(userid),"
                                + " FOREIGN KEY(commentid) REFERENCES comments(commentid))");
            
            // Drop Operations
            db.mDropUsers = db.mConnection.prepareStatement("DROP TABLE users CASCADE");
            db.mDropIdeas = db.mConnection.prepareStatement("DROP TABLE ideas CASCADE"); 
            db.mDropComments = db.mConnection.prepareStatement("DROP TABLE comments CASCADE");
            db.mDropLikes = db.mConnection.prepareStatement("DROP TABLE likes");
            db.mDropAll = db.mConnection.prepareStatement("DROP TABLE users, ideas, comments, likes");

            // Standard CRUD operations
            db.mDeleteUser = db.mConnection.prepareStatement("DELETE FROM users WHERE userid = ?");
            db.mDeleteIdea = db.mConnection.prepareStatement("DELETE FROM ideas WHERE ideaid = ?");
            db.mDeleteComment = db.mConnection.prepareStatement("DELETE FROM comments WHERE commentid = ? AND ideaid = ?");
            db.mDeleteLike = db.mConnection.prepareStatement("DELETE FROM likes WHERE ideaid = ? AND commentid = ? AND userid = ?");

            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, ?, ?, default)");
            db.mInsertIdea = db.mConnection.prepareStatement("INSERT INTO ideas VALUES (default, ?, ?, ?, default, default, ?, default)");
            db.mInsertComment = db.mConnection.prepareStatement("INSERT INTO comments VALUES (default, ?, ?, ?, ?, default, default, ?, default)");
            db.mInsertLike = db.mConnection.prepareStatement("INSERT INTO likes VALUES (?, ?, ?, default)");

            db.mSelectUsersAll = db.mConnection
                    .prepareStatement("SELECT userid, name, email, genderidentity, sexualorientation, note, creationdate, valid FROM users ORDER BY creationdate");
            db.mSelectIdeasAll = db.mConnection
                    .prepareStatement("SELECT ideaid, subject, message, userid, likes, comments, creationdate, valid FROM ideas ORDER BY creationdate");
            db.mSelectCommentsAll = db.mConnection
                    .prepareStatement("SELECT commentid, subject, message, userid, ideaid, likes, comments, creationdate, valid FROM comments ORDER BY creationdate");
            db.mSelectLikesAll = db.mConnection
                    .prepareStatement("SELECT ideaid, commentid, userid, status FROM likes");
            
            db.mSelectUser = db.mConnection.prepareStatement("SELECT * FROM users WHERE userid = ?");
            db.mSelectIdea = db.mConnection.prepareStatement("SELECT * FROM ideas WHERE ideaid = ?");
            db.mSelectComment = db.mConnection.prepareStatement("SELECT * FROM comments WHERE commentid = ? AND ideaid = ?");
            db.mSelectLike = db.mConnection.prepareStatement("SELECT * FROm likes WHERE ideaid = ? AND commentid = ? AND userid = ?");

            db.mUpdateUser = db.mConnection.prepareStatement("UPDATE users SET name = ?, email = ?, genderidentity = ?, sexualorientation = ?, note = ?, WHERE userid = ?");
            db.mUpdateIdea = db.mConnection.prepareStatement("UPDATE ideas SET message = ? WHERE ideaid = ?");
            db.mUpdateComment = db.mConnection.prepareStatement("UPDATE comments SET message = ? WHERE commentid = ? AND ideaid = ?");
            db.mUpdateLike = db.mConnection.prepareStatement("UPDATE likes SET status = ? WHERE ideaid = ? AND commentid = ? AND userid = ?");

            db.mLikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes + 1 WHERE ideaid = ?");
            db.mLikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes + 1 WHERE commentid = ? AND ideaid");

            db.mUnlikeIdea = db.mConnection.prepareStatement("UPDATE ideas SET likes = likes - 1 WHERE ideaid = ?");
            db.mUnlikeComment = db.mConnection.prepareStatement("UPDATE comments SET likes = likes - 1 WHERE commentid = ? AND ideaid = ?");
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
     * @param db_url       The url to the database
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
     * error occurred during the closing operation.
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
     * Insert an user into ideatable in database
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
        int count = 0;
        try {
            mInsertUser.setString(1, name);
            mInsertUser.setString(2, email);
            mInsertUser.setString(3, genId);
            mInsertUser.setString(4, sexOtn);
            mInsertUser.setString(5, note);
            mInsertUser.setString(6, DateFormat.getDateInstance().format(new Date()));
            count += mInsertUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a row into the database
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
            mInsertIdea.setString(6, DateFormat.getDateInstance().format(new Date()));
            count += mInsertIdea.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a row into the database
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
            mInsertComment.setString(7, DateFormat.getDateInstance().format(new Date()));
            count += mInsertComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a row into the database
     * 
     * @param ideaid the id of the idea for the new like row
     * @param commentid the id of the comment for the new like row
     * @param userid the id of the user who created the like row
     * 
     * @return The number of rows that were inserted
     */
    int insertLike(int ideaid, int commentid, int userid) {
        int count = 0;
        try {
            mInsertLike.setInt(0, ideaid);
            mInsertLike.setInt(1, commentid);
            mInsertLike.setInt(2, userid);
            count += mInsertComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    /**
     * Query the database for a list of all subjects, their IDs, content, and number
     * of likes
     * 
     * @return All rows, as an ArrayList
    ArrayList<DataIdea> selectAll() {
        ArrayList<DataIdea> res = new ArrayList<DataIdea>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new DataIdea(rs.getInt("id"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("likes")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
    DataIdea selectOne(int id) {
        DataIdea res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new DataIdea(rs.getInt("id"), rs.getString("subject"), rs.getString("message"),
                        rs.getInt("likes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
    int deleteRow(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id      The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated. -1 indicates an error.
    int updateOne(int id, String message) {
        int res = -1;
        try {
            mUpdateOne.setString(1, message);
            mUpdateOne.setInt(2, id);
            res = mUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @param id
     * @return int
    int likeOne(int id) {
        int res = -1;
        try {
            mLikeOne.setInt(1, id);
            res = mLikeOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @param id
     * @return int
    int unlikeOne(int id) {
        int res = -1;
        try {
            mUnlikeOne.setInt(1, id);
            res = mUnlikeOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Create tblData. If it already exists, this will print an error
     */
    void createTables() {
        try {
            mCreateUsersTable.execute();
            mCreateIdeasTable.execute();
            mCreateCommentsTable.execute();
            mCreateLikesTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database. If it does not exist, this will print
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