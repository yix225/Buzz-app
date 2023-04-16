package edu.lehigh.cse216.team_m.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

    /**
     * Get user input from scanner
     * 
     * @return String
     */
    public String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Test all possible inputs user can type
     */
    public void testInputs() {
        String actions = "TD1V*-+~/q?";

        InputStream in = new ByteArrayInputStream(actions.getBytes()); // load user input into input stream
        System.setIn(in);

        // char testAction = actions.charAt(0);
        // boolean testValid = actions.contains(String.valueOf(testAction));

        assertEquals(actions, getInput()); // if both strings are equal returns true
    }

    /**
     * Hardcode an input of '?' to test if it works with menu
     */
    public void testPrompt() { // tests that users can input stuff
        String input = "?\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        char result = App.prompt(in);
        assertEquals('?', result);
    }

    /**
     * Connect to database and test if unlike does decrease the like counter
     */
    public void testUnlike() {
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);

        DataIdea idea = db.selectIdea(5);
        int likes = idea.mLikes;
        db.unlikeIdea(5, 1, 0);
        idea = db.selectIdea(5);
        assertTrue(idea.mLikes == (likes-1));

        DataComment comment = db.selectComment(5,8);
        likes = comment.mLikes;
        db.unlikeComment(5, 1, 0, 8);
        comment = db.selectComment(5,8);
        assertTrue(comment.mLikes == (likes-1));
    }

    /**
     * Connect to database and test if like does increase the like counter
     */
    public void testLike() {
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);
        
        DataIdea idea = db.selectIdea(5);
        int likes = idea.mLikes;
        db.likeIdea(5, 1, 0);
        idea = db.selectIdea(5);
        assertTrue(idea.mLikes == (likes+1));

        DataComment comment = db.selectComment(5,8);
        likes = comment.mLikes;
        db.likeComment(5, 1, 0, 8);
        comment = db.selectComment(5,8);
        assertTrue(comment.mLikes == (likes+1));
    }

    /**
     * Connect to database and test if toggle changes the valid
     */
    public void testToggle() {
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);
        
        DataIdea idea = db.selectIdea(5);
        boolean valid = idea.mValid;
        db.toggleIdea(5);
        idea = db.selectIdea(5);
        assertTrue(idea.mValid == (!valid));

        DataComment comment = db.selectComment(5,8);
        valid = comment.mValid;
        db.toggleComment(5, 8);
        comment = db.selectComment(5,8);
        assertTrue(comment.mValid == (!valid));

        DataUser users = db.selectUser(3);
        valid = users.mValid;
        db.toggleUser(3);
        users = db.selectUser(3);
        assertTrue(users.mValid == (!valid));
    }

    /**
     * Connect to database and test if database returns only valid data
     */
    public void testShowValid() {
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);
        
        ArrayList<DataIdea> ideas = db.selectIdeasValid();
        for(DataIdea rw : ideas){
            assertEquals(true, rw.mValid);
        }

        ArrayList<DataComment> comments = db.selectCommentsValid();
        for(DataComment rw : comments){
            assertEquals(true, rw.mValid);
        }

        ArrayList<DataUser> users = db.selectUsersValid();
        for(DataUser rw : users){
            assertEquals(true, rw.mValid);
        }
    }
}
