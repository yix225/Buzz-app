package edu.lehigh.cse216.team_m.backend;

import java.util.Date;

/**
 * DataRow holds a row of information.  A row of information consists of
 * an identifier, strings for a "subject" and "content", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class DataRow {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mId;

    /**
     * The subject for this row of data
     */
    public String mSubject;

    /**
     * The message for this row of data
     */
    public String mMessage;
    
    /**
     * The number of likes for this row of data
     */
    public int mLikes;

    /**
     * The creation date for this row of data.  Once it is set, it cannot be 
     * changed
     */
    public final Date mCreated;

    /**
     * Default Constructor: Create a new DataRow with the provided id and subject/content, 
     * and a creation date based on the system clock at the time the constructor was
     * called. Likes are automatically set to 0.
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this row of data
     * 
     * @param content The content string for this row of data
     */
    DataRow(int id, String subject, String message) {
        mId = id;
        mSubject = subject;
        mMessage = message;
        mLikes = 0;
        mCreated = new Date();
    }

    /**
     * Create a new DataRow with the provided id and subject/content, and likes, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     * @param likes The likes of 
     */
    DataRow(int id, String subject, String message, int likes) {
        mId = id;
        mSubject = subject;
        mMessage = message;
        mLikes = likes;
        mCreated = new Date();
    }


    /**
     * Copy constructor to create one datarow from another
     */
    DataRow(DataRow data) {
        mId = data.mId;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        mSubject = data.mSubject;
        mMessage = data.mMessage;
        mLikes = data.mLikes;
        mCreated = data.mCreated;
    }
}