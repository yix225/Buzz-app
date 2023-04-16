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
    public Database connectDB(){
        String ip = "isilo.db.elephantsql.com";
        String port = Integer.toString(5432);
        String user = "gkzavwme";
        String pass = "5TWc-gVQdICuVD1rE-cCgdBQFBH-xH6g";
        Database db = Database.getDatabase(ip, port, user, pass);
        return db;
    }

    // public void testInsertIdea(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertIdea("testSub", "testMess", 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testInsertComment(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testupdateIdea(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testupdateComment(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testLikeIdea(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testunLikeIdea(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testLikeComment(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testunLikeComment(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.insertComment("testSub", "testMess", 1, 1);
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    // public void testupdateProfile(){
    //     int s=0;
    //     Database db = connectDB();
    //     db.updateUser(1, "testUserName", "testEmail", "testGenId", "testSex", "testNote");
    //     ArrayList<DataIdea> arr = db.selectIdeasAll();
    //     for(DataIdea idea : arr){
    //         if(idea.mUserId == 1){
    //             s=1;
    //             break;
    //         }
    //     }
    //     if(s==0){
    //         assertTrue(false);
    //     }
    //     else{
    //         assertTrue(true);
    //     }
    // }
    
}