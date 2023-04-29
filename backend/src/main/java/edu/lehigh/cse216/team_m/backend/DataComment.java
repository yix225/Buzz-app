package edu.lehigh.cse216.team_m.backend;

import java.sql.Timestamp;

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

    public String fileName;

    public String fileType;

    public String fileDescription;

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
    DataComment(int id, String subject, String message, int userId, int ideaId) {
        super(id, subject, message, userId);
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
    DataComment(int id, String subject, String message, int userId, int likes, int comments, boolean valid, int ideaId, Timestamp created) {
        super(id, subject, message, userId, likes, comments, valid, created);
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
    /**
     * Create a new DataComment with the provided id, fileName, userId, fileType, fileDescription, and ideaId
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param fileName The name of the file aka the media 
     * 
     * @param fileType The name of the type of file the user is trying to upload
     * 
     * @param fileDescription Description of the file and its importance 
     */
    DataComment(int id, String fileName, int userId, String fileType, String fileDescription, int ideaId){
        super(id, fileName, userId, fileType, fileDescription);
        mIdeaId = data.mIdeaId;
        }
}