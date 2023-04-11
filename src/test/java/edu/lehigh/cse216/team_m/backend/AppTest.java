package edu.lehigh.cse216.team_m.backend;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }
    
    /**
     * Test insertRow
     */
    public void testInsertRow(){
        // Connecting to Database . . .
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);

        // Setting up DataRow for Test
        DataRow dr1 = new DataRow(300, "InsertTest", "This is a test");
        
        // Inserting Row with similar Subject and Message
        db.insertRow("InsertTest", "This is a test");
        
        // Finding specific DataRow with same Subject
        ArrayList<DataRow> arr = db.selectAll();
        for(DataRow dr : arr ){
            if(dr.mSubject.equals("InsertTest")){
                id = dr.mId;
                break;
            }
        }

        // Setting new DataRow to be equal to the one found in loop
        DataRow dr2 = db.selectOne(id);

        // Checking to see if values are still the same
        assertEquals(dr1.mSubject, dr2.mSubject);
        assertEquals(dr1.mMessage, dr2.mMessage);
        assertEquals(dr1.mLikes, dr2.mLikes);
    }
    
    /**
     * Tests DeleteRow
     */
    public void testDeleteRow(){
        // Connecting to Database . . .
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);

        // deleting test row from previous test
        db.deleteRow(id);

        // Looking for DataRow with deleted id
        ArrayList<DataRow> arr = db.selectAll();
        for(DataRow dr : arr ){
            if(dr.mId == id){
                // if found, row was not deleted: failure
                assertTrue(false);
                break;
            }
        }

        // was not found, row was deleted
        assertTrue(true);
    }
    
    /**
     * Tests likeOne
     */
    public void testLikeOne() {
        
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);

        int id = 3;

        // selects a specific row in database
        DataRow res = db.selectOne(id);
        int likes = res.mLikes;

        assertEquals(res.mLikes, likes);
        // add likes to the specific row and update int likes
        db.likeOne(id);

        // should be equal after likes added
        assertEquals(res.mLikes, likes);
    }

    public void testunlikeOne() {
        
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";

        Database db = Database.getDatabase(ip, port, user, pass);

        int id = 3;

        // selects a specific row in database
        DataRow res = db.selectOne(id);
        int likes = res.mLikes;

        assertEquals(res.mLikes, likes);
        // add likes to the specific row and update int likes
        db.unlikeOne(id);

        // should be equal after likes added
        assertEquals(res.mLikes, likes);
    }
    public void testHashTable() {
        // Create a new hash map to store the user ID and session key
        HashMap<String, HashMap<String, String>> usersMap = new HashMap<>();
        HashMap<String, String> testUser = new HashMap<>();
        testUser.put("name", "David");
        testUser.put("email", "jiw324@lehigh.com");
        testUser.put("gender identity", "Male");
        testUser.put("sexual orientation", "Heterosexual");
        testUser.put("note", "Backend dev");
        usersMap.put("test1", testUser);

        HashMap<String, String> ret = usersMap.get("test1"); 
        assertEquals(ret.get("name"), "David");
        assertEquals(ret.get("email"), "jiw324@lehigh.com");
        assertEquals(ret.get("gender identity"), "Male");
        assertEquals(ret.get("sexual orientation"), "Heterosexual");
        assertEquals(ret.get("note"), "Backend dev");
    }
    public void testHashTable() {
        // Create a new hash map to store the user ID and session key
        HashMap<String, HashMap<String, String>> usersMap = new HashMap<>();
        HashMap<String, String> testUser = new HashMap<>();
        testUser.put("name", "David");
        testUser.put("email", "jiw324@lehigh.com");
        testUser.put("gender identity", "Male");
        testUser.put("sexual orientation", "Heterosexual");
        testUser.put("note", "Backend dev");
        usersMap.put("test1", testUser);

        HashMap<String, String> ret = usersMap.get("test1"); 
        assertEquals(ret.get("name"), "David");
        assertEquals(ret.get("email"), "jiw324@lehigh.com");
        assertEquals(ret.get("gender identity"), "Male");
        assertEquals(ret.get("sexual orientation"), "Heterosexual");
        assertEquals(ret.get("note"), "Backend dev");
    }
}