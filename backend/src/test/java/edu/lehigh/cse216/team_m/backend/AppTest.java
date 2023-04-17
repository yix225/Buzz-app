package edu.lehigh.cse216.team_m.backend;

<<<<<<< HEAD
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
=======
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Map;
import java.util.UUID;

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
<<<<<<< HEAD
    public Database connectDB(){
=======
    
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
        
>>>>>>> 49c32c61ef3467218e5c844eef12bc8dd4babccb
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";
        Database db = Database.getDatabase(ip, port, user, pass);
        return db;
    }
    public void mClose(Database db) throws SQLException {
        if (db != null) {
            try {
                ((Closeable) db).close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            db = null;
            System.out.println("Database connection closed.");
        }
    }
    public void myTest(){
        int s=0;
        Database db = connectDB();
        db.insertIdea("testSub", "testMess", 1);
        ArrayList<DataIdea> arr = db.selectIdeasAll();
        for(DataIdea idea : arr){
            if(idea.mUserId == 1){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
        s=0;
        db.insertComment("testSub", "testMess", 1, 3);
        ArrayList<DataIdea> arr2 = db.selectIdeasAll();
        for(DataIdea idea : arr2){
            if(idea.mUserId == 3){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
        s=0;
        db.updateIdea("updateMess", 3);
        ArrayList<DataIdea> arr3 = db.selectIdeasAll();
        for(DataIdea idea : arr3){
            if(idea.mUserId == 3){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
        s=0;
        db.unlikeIdea(1,1,1);
        ArrayList<DataIdea> arr4 = db.selectIdeasAll();
        for(DataIdea idea : arr4){
            if(idea.mUserId == 1){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
        s=0;
        db.insertComment("testSub", "testMess", 1, 1);
        ArrayList<DataIdea> arr5 = db.selectIdeasAll();
        for(DataIdea idea : arr5){
            if(idea.mUserId == 1){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
        s=0;
        db.likeComment(1,1,1,1);
        ArrayList<DataIdea> arr6 = db.selectIdeasAll();
        for(DataIdea idea : arr6){
            if(idea.mUserId == 1){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
        s=0;
        db.unlikeComment(1,1,1,1);
        ArrayList<DataIdea> arr7 = db.selectIdeasAll();
        for(DataIdea idea : arr7){
            if(idea.mUserId == 1){
                s=1;
                break;
            }
        }
        if(s==0){
            assertTrue(false);
        }
        else{
            assertTrue(true);
        }
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