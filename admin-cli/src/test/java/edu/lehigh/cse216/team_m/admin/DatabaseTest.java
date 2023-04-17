package edu.lehigh.cse216.team_m.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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
        boolean valid = true; Timestamp created = new Timestamp(new Date().getTime());

        DataUser user = new DataUser(id, name, email, genId, sexOtn, note);

        assertTrue(user.mId == id);
        assertTrue(user.mName.equals(name));
        assertTrue(user.mEmail.equals(email));
        assertTrue(user.mGenId.equals(genId));
        assertTrue(user.mSexOtn.equals(sexOtn));
        assertTrue(user.mNote.equals(note));
        assertTrue(user.mValid == valid);


        DataUser user2 = new DataUser(id, name, email, genId, sexOtn, note, valid, created);

        assertTrue(user2.mId == id);
        assertTrue(user2.mName.equals(name));
        assertTrue(user2.mEmail.equals(email));
        assertTrue(user2.mGenId.equals(genId));
        assertTrue(user2.mSexOtn.equals(sexOtn));
        assertTrue(user2.mNote.equals(note));
        assertTrue(user2.mValid == valid);
        assertTrue(user2.mCreated.equals(new Date(created.getTime())));

        DataUser copy = new DataUser(user2);

        assertTrue(copy.mId == id);
        assertTrue(copy.mName.equals(name));
        assertTrue(copy.mEmail.equals(email));
        assertTrue(copy.mGenId.equals(genId));
        assertTrue(copy.mSexOtn.equals(sexOtn));
        assertTrue(copy.mNote.equals(note));
        assertTrue(copy.mValid == valid);
        assertTrue(copy.mCreated.equals(new Date(created.getTime())));
    }
    
    public void testDataIdea() {
        int id = 671236923; String subject = "testidea"; 
        String message = "this is a test"; 
        int userId = 1; int likes = 5; int comments = 2;  
        boolean valid = true; Timestamp created = new Timestamp(new Date().getTime());

        DataIdea idea = new DataIdea(id, subject, message, userId);
        assertTrue(idea.mId == id);
        assertTrue(idea.mSubject.equals(subject));
        assertTrue(idea.mMessage.equals(message));
        assertTrue(idea.mUserId == userId);
        assertTrue(idea.mLikes == 0);
        assertTrue(idea.mComments == 0);
        assertTrue(idea.mValid == valid);

        DataIdea idea2 = new DataIdea(id, subject, message, userId, likes, comments, valid, created); 
        assertTrue(idea2.mId == id);
        assertTrue(idea2.mSubject.equals(subject));
        assertTrue(idea2.mMessage.equals(message));
        assertTrue(idea2.mUserId == userId);
        assertTrue(idea2.mLikes == likes);
        assertTrue(idea2.mComments == comments);
        assertTrue(idea2.mValid == valid);
        assertTrue(idea2.mCreated.equals(new Date(created.getTime())));

        DataIdea copy = new DataIdea(idea2); 
        assertTrue(copy.mId == id);
        assertTrue(copy.mSubject.equals(subject));
        assertTrue(copy.mMessage.equals(message));
        assertTrue(copy.mUserId == userId);
        assertTrue(copy.mLikes == likes);
        assertTrue(copy.mComments == comments);
        assertTrue(copy.mValid == valid);
        assertTrue(copy.mCreated.equals(new Date(created.getTime())));
    }

    public void testDataComment() {
        int id = 671236923; String subject = "testidea"; 
        String message = "this is a test"; int ideaId = 1;
        int userId = 1; int likes = 5; int comments = 2;  
        boolean valid = true; Timestamp created = new Timestamp(new Date().getTime());

        DataComment comment = new DataComment(id, subject, message, userId, ideaId);
        assertTrue(comment.mId == id);
        assertTrue(comment.mSubject.equals(subject));
        assertTrue(comment.mMessage.equals(message));
        assertTrue(comment.mUserId == userId);
        assertTrue(comment.mIdeaId == ideaId);
        assertTrue(comment.mLikes == 0);
        assertTrue(comment.mComments == 0);
        assertTrue(comment.mValid == valid);

        DataComment comment2 = new DataComment(id, subject, message, userId, likes, comments, valid, ideaId, created); 
        assertTrue(comment2.mId == id);
        assertTrue(comment2.mSubject.equals(subject));
        assertTrue(comment2.mMessage.equals(message));
        assertTrue(comment2.mUserId == userId);
        assertTrue(comment2.mLikes == likes);
        assertTrue(comment.mIdeaId == ideaId);
        assertTrue(comment2.mComments == comments);
        assertTrue(comment2.mValid == valid);
        assertTrue(comment2.mCreated.equals(new Date(created.getTime())));

        DataComment copy = new DataComment(comment2); 
        assertTrue(copy.mId == id);
        assertTrue(copy.mSubject.equals(subject));
        assertTrue(copy.mMessage.equals(message));
        assertTrue(copy.mUserId == userId);
        assertTrue(copy.mLikes == likes);
        assertTrue(comment.mIdeaId == ideaId);
        assertTrue(copy.mComments == comments);
        assertTrue(copy.mValid == valid);
        assertTrue(copy.mCreated.equals(new Date(created.getTime())));
    }

    public void testDataLike() {
        int ideaId = 1; int userId = 1; 
        int status = 0; int comId = 2;

        DataLike like = new DataLike(ideaId, userId, comId, status);
        assertTrue(like.mIdeaId == ideaId);
        assertTrue(like.mComId == comId);
        assertTrue(like.mUserId == userId);
        assertTrue(like.mStatus == status);

        DataLike like2 = new DataLike(ideaId, userId, status);
        assertTrue(like2.mIdeaId == ideaId);
        assertTrue(like2.mUserId == userId);
        assertTrue(like2.mComId == null);
        assertTrue(like2.mStatus == status);

        DataLike copy = new DataLike(like);
        assertTrue(copy.mIdeaId == ideaId);
        assertTrue(copy.mComId == comId);
        assertTrue(copy.mUserId == userId);
        assertTrue(copy.mStatus == status);
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
