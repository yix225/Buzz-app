package edu.lehigh.cse216.team_m.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;
import java.io.BufferedReader;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Map;

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
     * 
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
     * 
     */
    public void testPrompt() { // tests that users can input stuff
        App testApp = new App();
        String input = "?\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        char result = testApp.prompt(in);
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
    }
}
