package edu.lehigh.cse216.team_m.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Formatter.BigDecimalLayoutForm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Date;

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

    public void testDataUser() {
        int id = 1231241351; String name = "testname"; 
        String email = "test@gmail.com"; String genId = "test Gender"; 
        String sexOtn = "test Orientation"; String note = "test note"; 
        boolean valid = true; Date created = new Date();


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
