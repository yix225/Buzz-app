package edu.lehigh.cse216.team_m.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatabaseTest extends TestCase {

    public DatabaseTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }

    // public void testConnection() {

    // String ip2 = Integer.toString(4567);
    // String port2 = Integer.toString(8998);
    // String user2 = "gkzavwme";
    // String pass2 = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

    // Database db2 = new Database(ip2, port2, user2, pass2);
    // Database db = new Database(db);
    // assertTrue(db.ip.equals(ip));
    // assertTrue(db.port.equals(port2));
    // assertTrue(db.user.equals(user2));
    // assertTrue(db.pass.equals(pass2));
    // }

    public void testRowData() {
        int id = 123;
        String subject = "Test Subject";
        String message = "Test Messsage";

        Database.RowData db = new Database.RowData(id, subject, message);

        assertTrue(db.mId == id);
        assertTrue(db.mSubject.equals(subject));
        assertTrue(db.mMessage.equals(message));
    }
}
