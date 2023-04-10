package edu.lehigh.cse216.team_m.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 * @author Hien Thi
 * @version 3/25/2023
 */
public class DatabaseTest extends TestCase {
    /**
     * @param testName name of the database
     */
    public DatabaseTest(String testName) {
        super(testName);
    }
    /**
     * Suite test to return database test class
     * @return TestSuite
     */
    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }

    
    /**
     * Test to see if inserted id, subject, message and likes match up with the row in the database
     */
    public void testRowData() {
        /*int id = 123;
        String subject = "Test Subject";
        String message = "Test Messsage";
        int likes = 3;
        Database.RowData db = new Database.RowData(id, subject, message, likes);

        assertTrue(db.mId == id);
        assertTrue(db.mSubject.equals(subject));
        assertTrue(db.mMessage.equals(message));
        assertTrue(db.mLikes == likes);*/
    }

}
