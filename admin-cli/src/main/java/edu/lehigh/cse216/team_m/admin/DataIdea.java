package edu.lehigh.cse216.team_m.admin;

import java.util.Date;
import java.sql.Timestamp;

/**
 * DataIdea holds a row of information for an idea.  A row of information consists of
 * an identifier, strings for a "subject" and "message", an int for number of "likes"
 * and "comments", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class DataIdea {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mId;
    
    /**
     * The subject for this idea row
     */
    public String mSubject;

    /**
     * The message for this idea row
     */
    public String mMessage;
    
    /**
     * The number of likes for this idea row
     */
    public int mLikes;

    /**
     * The number of comments for this idea row
     */
    public int mComments;

    /**
     * The id of creator of this idea row
     * 
     * @see DataUser
     */
    public final int mUserId;

    /**
     * Denotes if idea row is valid. If true, the idea can be displayed on the website/app
     */
    public boolean mValid;

    /**
     * The date for this idea row.  Once it is set, try not to change it :)
     */
    public Date mCreated;

    /**
     * Default Constructor: Create a new DataIdea with the provided id and subject/message, 
     * and a creation date based on the system clock at the time the constructor was
     * called. Likes and comments are automatically set to 0.
     * 
     * @param id The id to associate this idea row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this idea row
     * 
     * @param message The message string for this idea row
     * 
     * @param userId the id associated with the user who created the row.
     */
    DataIdea(int id, String subject, String message, int userId) {
        mId = id;
        mSubject = subject;
        mMessage = message;
        mLikes = 0;
        mComments = 0;
        mUserId = userId;
        mValid = true;
        mCreated = new Date();
    }

    /**
     * Create a new DataIdea with the provided id, subject/message, likes, and 
     * comments. A creation date based on the system clock at the time the constructor 
     * was called
     * 
     * @param id The id to associate this idea row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this idea row.
     * 
     * @param message The message string for this idea row.
     * 
     * @param userId the user who created this idea row
     * 
     * @param likes The likes of this idea row.
     * 
     * @param comments The number of comments on this idea
     * 
     * @param valid The validity of the idea
     */
    DataIdea(int id, String subject, String message, int userId, int likes, int comments, boolean valid, Timestamp created) {
        mId = id;
        mSubject = subject;
        mMessage = message;
        mLikes = likes;
        mComments = comments;
        mUserId = userId;
        mValid = valid;
        mCreated = new Date(created.getTime());
    }


    /**
     * Copy constructor to create one dataidea from another
     * @param data The dataIdea you are copying from
     */
    DataIdea(DataIdea data) {
        mId = data.mId;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        mSubject = data.mSubject;
        mMessage = data.mMessage;
        mLikes = data.mLikes;
        mComments = data.mComments;
        mUserId = data.mUserId;
        mValid = data.mValid;
        mCreated = data.mCreated;
    }
}