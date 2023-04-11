package edu.lehigh.cse216.team_m.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;
/**
 * @author Hien Thi
 * @version 3/25/2023
 */
/**
 * App is our basic admin app.  For now, it is a demonstration of the six key 
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create tblData");
        System.out.println("  [D] Drop tblData");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [V] Query for all valid rows");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [~] Update a row");
        System.out.println("  [/] Toggle validity row");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "TD1V*-+~/q?";

        // We repeat until a valid single-character option is selected        
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided.  May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided.  On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static final String DEFAULT_PORT_DB = "5432";

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
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options.  Ignored by this program.
     */
    public static void main(String[] argv) {
        // Get a fully-configured connection to the database, or exit 
        // immediately
        Database db = getDatabaseConnection();
        if(db == null)
            return;
            
        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            //     function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {
                db.createTables();
            } else if (action == 'D') {
                db.dropTables();
            } else if (action == '1') {
                String type = getString(in, "Enter the type(user, idea, comment}");
                if(type.equals("user")) {
                    int userid = getInt(in, "Enter the user ID");
                    if (userid == -1)
                        continue;
                    DataUser res = db.selectUser(userid);
                    if (res != null) {
                        System.out.println("  [" + res.mId + "] " + res.mName);
                        System.out.println("  Email: " + res.mEmail);
                        System.out.println("  Gender ID: " + res.mGenId);
                        System.out.println("  Sexual Orientation: " + res.mSexOtn);
                        System.out.println("  Note: " + res.mNote);
                        System.out.println("  Creation Date: " + res.mCreated);
                        System.out.println("  Valid: " + res.mValid);
                        
                        String action2 = getString(in," Update(U) or Leave(L)?: ");
                        if(action2.equals("U")){
                            String name = getString(in, "Enter the name");
                            String email = getString(in, "Enter the email");
                            String genId = getString(in, "Enter the gender identity");
                            String sexOtn = getString(in, "Enter the sexual orientation");
                            String note = getString(in, "Enter the note");
                            if (name.equals("") || email.equals("") || genId.equals("") || sexOtn.equals("") || note.equals(""))
                                continue;
                            int update = db.updateUser(userid, name, email, genId, sexOtn, note);
                            if(update == -1)
                                continue;
                        }
                        else if(action2.equals("L"))
                            continue;
                    }
                }
                else if(type.equals("idea")) {
                    int ideaid = getInt(in, "Enter the idea ID");
                    if (ideaid == -1)
                        continue;
                    DataIdea res = db.selectIdea(ideaid);
                    if (res != null) {
                        System.out.println("  [" + res.mId + "] " + res.mSubject);
                        System.out.println("  --> " + res.mMessage);
                        System.out.println("  UserID: " + res.mUserId);
                        System.out.println("  Likes: " + res.mLikes);
                        System.out.println("  Comments: " + res.mComments);
                        System.out.println("  Creation Date: " + res.mCreated);
                        System.out.println("  Valid: " + res.mValid);
                        
                        String action2 = getString(in," Upvote(+) or Downvote(-) or View Comments (c) or Leave(L)?: ");
                        if(action2.equals("+")){
                            int update = 0;
                            DataLike like = db.selectLikeIdea(res.mId, res.mUserId);
                            if(like == null){
                                int insert = db.insertLike(res.mId, null, res.mUserId);
                                if (insert == -1)
                                    continue;
                                like = db.selectLikeIdea(res.mId, res.mUserId);
                            }
                            else if((like.mStatus == 0) || (like.mStatus == -1)){
                                update = db.likeIdea(res.mId, res.mUserId, 1);
                                if (update == -1)
                                    continue;
                            }
                            else if(like.mStatus == 1){
                                update = db.unlikeIdea(res.mId, res.mUserId, 0);
                                if (update == -1)
                                    continue;
                            }
                            System.out.println("  " + update + " rows updated");
                        }
                        else if(action2.equals("-")){
                            DataLike like = db.selectLikeIdea(res.mId, res.mUserId);
                            int update = 0;
                            if(like == null){
                                int insert = db.insertLike(res.mId, null, res.mUserId);
                                if (insert == -1)
                                    continue;
                                like = db.selectLikeIdea(res.mId, res.mUserId);
                            }
                            else if((like.mStatus == 0) || (like.mStatus == 1)){
                                update = db.unlikeIdea(res.mId, res.mUserId, -1);
                                if (update == -1)
                                    continue;
                            }
                            else if(like.mStatus == -1){
                                update = db.likeIdea(res.mId, res.mUserId, 0);
                                if (update == -1)
                                    continue;
                            }
                            System.out.println("  " + update + " rows updated");
                        }
                        else if(action2.equals("c")){
                            ArrayList<DataComment> row = db.viewComments(res.mId);
                            if (row == null)
                                continue;
                            for (DataComment rd : row) {
                                System.out.println("\t  [" + rd.mIdeaId + "|" + rd.mId + "] " + rd.mSubject);
                                System.out.println("\t  --> " + rd.mMessage);
                                System.out.println("\t  UserID: " + rd.mUserId);
                                System.out.println("\t  Likes: " + rd.mLikes);
                                System.out.println("\t  Comments: " + rd.mComments);
                                System.out.println("\t  Creation Date: " + rd.mCreated);
                                System.out.println("\t  Valid: " + rd.mValid);
                            }
                        }
                        else if(action2.equals("L"))
                            continue;
                    }
                }
                else if(type.equals("comment")) {
                    int ideaid = getInt(in, "Enter the idea ID");
                    if (ideaid == -1)
                        continue;
                    int commentid = getInt(in, "Enter the comment ID");
                    if (commentid == -1)
                        continue;
                    DataComment res = db.selectComment(ideaid, commentid);
                    if (res != null) {
                        System.out.println("  [" + res.mIdeaId + "|" + res.mId + "] " + res.mSubject);
                        System.out.println("  --> " + res.mMessage);
                        System.out.println("  UserID: " + res.mUserId);
                        System.out.println("  Likes: " + res.mLikes);
                        System.out.println("  Comments: " + res.mComments);
                        System.out.println("  Creation Date: " + res.mCreated);
                        System.out.println("  Valid: " + res.mValid);
                        
                        String action2 = getString(in," Upvote(+) or Downvote(-) or View Comments (c) or Leave(L)?: ");
                        if(action2.equals("+")){
                            DataLike like = db.selectLikeComment(res.mIdeaId, res.mUserId, res.mId);
                            int update = 0;
                            if(like == null){
                                int insert = db.insertLike(res.mIdeaId, res.mId, res.mUserId);
                                if (insert == -1)
                                    continue;
                                like = db.selectLikeComment(res.mIdeaId, res.mUserId, res.mId);
                            }
                            if((like.mStatus == 0) || (like.mStatus == -1)){
                                update = db.likeComment(res.mIdeaId, res.mUserId, 1, res.mId);
                                if (update == -1)
                                    continue;
                            }
                            else if(like.mStatus == 1){
                                update = db.unlikeComment(res.mIdeaId, res.mUserId, 1, res.mId);
                                if (update == -1)
                                    continue;
                            }
                            System.out.println("  " + update + " rows updated");
                        }
                        else if(action2.equals("-")){
                            DataLike like = db.selectLikeComment(res.mIdeaId, res.mUserId, res.mId);
                            int update = 0;
                            if(like == null){
                                int insert = db.insertLike(res.mIdeaId, res.mId, res.mUserId);
                                if (insert == -1)
                                    continue;
                                like = db.selectLikeComment(res.mIdeaId, res.mUserId, res.mId);
                            }
                            if((like.mStatus == 0) || (like.mStatus == 1)){
                                update = db.unlikeComment(res.mIdeaId, res.mUserId, 1, res.mId);
                                if (update == -1)
                                    continue;
                            }
                            else if(like.mStatus == -1){
                                update = db.likeComment(res.mIdeaId, res.mUserId, 1, res.mId);
                                if (update == -1)
                                    continue;
                            }
                            System.out.println("  " + update + " rows updated");
                        }
                    }
                }
            } else if (action == 'V') {
                String type = getString(in, "Enter the type(user, idea, comment}");
                if(type.equals("user")){
                    ArrayList<DataUser> res = db.selectUsersValid();
                    if (res == null)
                        continue;
                    System.out.println("  Current User Database Contents");
                    System.out.println("  -------------------------");
                    for (DataUser rd : res) {
                        System.out.println("  [" + rd.mId + "] " + rd.mName + "  <" + rd.mEmail + ">");
                    }
                }
                else if(type.equals("idea")){
                    ArrayList<DataIdea> res = db.selectIdeasValid();
                    if (res == null)
                        continue;
                    System.out.println("  Current Idea Database Contents");
                    System.out.println("  -------------------------");
                    for (DataIdea rd : res) {
                        System.out.println("  [" + rd.mId + "] " + rd.mSubject + "  <Likes:" + rd.mLikes + ">");
                    }
                }
                else if(type.equals("comment")){
                    ArrayList<DataComment> res = db.selectCommentsValid();
                    if (res == null)
                        continue;
                    System.out.println("  Current Comment Database Contents");
                    System.out.println("  -------------------------");
                    for (DataComment rd : res) {
                        System.out.println("  [" + rd.mIdeaId + "|" + rd.mId + "] " + rd.mSubject + "  <Likes:" + rd.mLikes + ">");
                    }
                }
            } else if (action == '*') {
                String type = getString(in, "Enter the type(user, idea, comment}");
                if(type.equals("user")){
                    ArrayList<DataUser> res = db.selectUsersAll();
                    if (res == null)
                        continue;
                    System.out.println("  Current User Database Contents");
                    System.out.println("  -------------------------");
                    for (DataUser rd : res) {
                        System.out.println("  [" + rd.mId + "] " + rd.mName + "  <" + rd.mEmail + ">");
                    }
                }
                else if(type.equals("idea")){
                    ArrayList<DataIdea> res = db.selectIdeasAll();
                    if (res == null)
                        continue;
                    System.out.println("  Current Idea Database Contents");
                    System.out.println("  -------------------------");
                    for (DataIdea rd : res) {
                        System.out.println("  [" + rd.mId + "] " + rd.mSubject + "  <Likes:" + rd.mLikes + ">");
                    }
                }
                else if(type.equals("comment")){
                    ArrayList<DataComment> res = db.selectCommentsAll();
                    if (res == null)
                        continue;
                    System.out.println("  Current Comment Database Contents");
                    System.out.println("  -------------------------");
                    for (DataComment rd : res) {
                        System.out.println("  [" + rd.mIdeaId + "|" + rd.mId + "] " + rd.mSubject + "  <Likes:" + rd.mLikes + ">");
                    }
                }
            } else if (action == '-') {
                String type = getString(in, "Enter the type(user, idea, comment}");
                int res = 0;
                if(type.equals("user")){
                    int userid = getInt(in, "Enter the user ID");
                    if (userid == -1)
                        continue;
                    res = db.deleteUser(userid);
                    if (res == -1)
                        continue;
                }
                else if(type.equals("idea")){
                    int ideaid = getInt(in, "Enter the idea ID");
                    if (ideaid == -1)
                        continue;
                    res = db.deleteIdea(ideaid);
                    if (res == -1)
                        continue;
                }
                else if(type.equals("comment")){
                    int ideaid = getInt(in, "Enter the ieda ID");
                    if (ideaid == -1)
                        continue;
                    int commentid = getInt(in, "Enter the comment ID");
                    if (commentid == -1)
                        continue;
                    res = db.deleteComment(ideaid, commentid);
                    if (res == -1)
                        continue;
                }
                System.out.println("  " + res + " rows deleted");
            } else if (action == '+') {
                String type = getString(in, "Enter the type(user, idea, comment}");
                int res = 0;
                if(type.equals("user")){
                    String name = getString(in, "Enter the name");
                    String email = getString(in, "Enter the email");
                    String genId = getString(in, "Enter the gender identity");
                    String sexOtn = getString(in, "Enter the sexual orientation");
                    String note = getString(in, "Enter the note");
                    if (name.equals("") || email.equals("") || genId.equals("") || sexOtn.equals("") || note.equals(""))
                        continue;
                    res = db.insertUser(name, email, genId, sexOtn, note);
                }
                else if(type.equals("idea")) {
                    String subject = getString(in, "Enter the subject");
                    String message = getString(in, "Enter the message");
                    if (subject.equals("") || message.equals(""))
                        continue;
                    res = db.insertIdea(subject, message, 1);
                }
                else if(type.equals("comment")){
                    int ideaid = getInt(in, "Enter the ideaid");
                    String subject = getString(in, "Enter the subject");
                    String message = getString(in, "Enter the message");
                    if (subject.equals("") || message.equals(""))
                        continue;
                    res = db.insertComment(subject, message, 1, ideaid);
                }
                System.out.println(res + " rows added");
            } else if (action == '~') {
                String type = getString(in, "Enter the type(idea, comment}");
                int res = 0;
                if(type.equals("idea")){
                    int ideaid = getInt(in, "Enter the idea ID :> ");
                    if (ideaid == -1)
                        continue;
                    String newMessage = getString(in, "Enter the new message");
                    res = db.updateIdea(newMessage, ideaid);
                    if (res == -1)
                        continue;
                }
                else if(type.equals("comment")){
                    int ideaid = getInt(in, "Enter the idea ID :> ");
                    if (ideaid == -1)
                        continue;
                    int commentid = getInt(in, "Enter the comment ID :> ");
                    if (commentid == -1)
                        continue;
                    String newMessage = getString(in, "Enter the new message");
                    res = db.mUpdateComment(newMessage, ideaid, commentid);
                    if (res == -1)
                        continue;
                }
                System.out.println("  " + res + " rows updated");
            } else if (action == '/') {
                String type = getString(in, "Enter the type(user, idea, comment}");
                int res = 0;
                if(type.equals("user")){
                    int userid = getInt(in, "Enter the user ID");
                    res = db.toggleUser(userid);
                }
                else if(type.equals("idea")) {
                    int ideaid = getInt(in, "Enter the idea ID");
                    res = db.toggleUser(ideaid);
                }
                else if(type.equals("comment")){
                    int ideaid = getInt(in, "Enter the idea ID");
                    int commentid = getInt(in, "Enter the commentid ID");
                    res = db.toggleComment(ideaid, commentid);
                }
                System.out.println("  " + res + " rows updated");
            }
        }
        // Always remember to disconnect from the database when the program 
        // exits
        db.disconnect();
    }
}