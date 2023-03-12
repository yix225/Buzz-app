package edu.lehigh.cse216.team_m.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DataRowTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataRowTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DataRowTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String subject = "Test subject";
        String message = "Test message";
        int id = 17;
        DataRow d = new DataRow(id, subject, message);

        assertTrue(d.mSubject.equals(subject));
        assertTrue(d.mMessage.equals(message));
        assertTrue(d.likes == 0);
        assertTrue(d.mId == id);
        assertFalse(d.mCreated == null);
    }

    /**
     * Ensure that the constructor populates every field when likes are specified of the object it
     * creates
     */
    public void testconstructor2() {
        String subject = "Test subject";
        String message = "Test message";
        int likes = 32;
        int id = 21;
        DataRow d = new DataRow(id, subject, message);

        assertTrue(d.mSubject.equals(subject));
        assertTrue(d.mMessage.equals(message));
        assertTrue(d.likes == 0);
        assertTrue(d.mId == id);
        assertFalse(d.mCreated == null);
    }

    /**
     * Ensure that the copy constructor works correctly
     */
    public void testCopyconstructor() {
        String subject = "Test Title For Copy";
        String message = "Test Content For Copy";
        int id = 177;
        DataRow d = new DataRow(id, subject, message);
        DataRow d2 = new DataRow(d);
        assertTrue(d2.mSubject.equals(d.mSubject));
        assertTrue(d2.mMessage.equals(d.mMessage));
        assertTrue(d2.likes == d.likes);
        assertTrue(d2.mId == d.mId);
        assertTrue(d2.mCreated.equals(d.mCreated));
    }
}