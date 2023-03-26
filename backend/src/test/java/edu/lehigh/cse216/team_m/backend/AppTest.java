package edu.lehigh.cse216.team_m.backend;

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
}