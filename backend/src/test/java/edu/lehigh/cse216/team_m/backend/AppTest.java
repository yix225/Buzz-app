package edu.lehigh.cse216.team_m.backend;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
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
    public Database connectDB(){
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
}