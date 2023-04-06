package edu.lehigh.cse216.team_m.admin;

/**
 * DataComment holds a row of information for a comment.  It has all the variables
 * from the DataIdea. Plus an identifier for the Idea the comment is under.
 * 
 * @see DataIdea 
 * 
 */
public class DataComment extends DataIdea{
    /**
     * The id of Idea the comment is under, it is final as it does not change once set.
     */
    public final int mIdeaId;

    /**
     * Default Constructor: Create a new DataComment with the provided id, subject/message
     * and ideaId, and a creation date based on the system clock at the time the constructor 
     * was called. Likes and comments are automatically set to 0.
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this row of data
     * 
     * @param message The message string for this row of data
     * 
     * @param ideaId The id of the idea the comment is under. 
     */
    DataComment(int id, String subject, String message, int ideaId) {
        super(id, subject, message);
        mIdeaId = ideaId;
    }

    /**
     * Create a new DataComment with the provided id, subject/message, likes, comments, 
     * and ideaId. A creation date based on the system clock at the time the constructor 
     * was called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this row of data
     * 
     * @param message The message string for this row of data
     * 
     * @param likes The likes of 
     */
    DataComment(int id, String subject, String message, int likes, int comments, boolean valid, int ideaId) {
        super(id, subject, message, likes, comments, valid);
        mIdeaId = ideaId;
    }


    /**
     * Copy constructor to create one datacomment from another
     * @param data The dataComment you are copying from
     */
    DataComment(DataComment data) {
        super(data);
        mIdeaId = data.mIdeaId;
    }
}