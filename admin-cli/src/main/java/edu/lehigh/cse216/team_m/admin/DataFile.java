package edu.lehigh.cse216.team_m.admin;

import java.sql.Timestamp;
import java.util.Date;

/**
 * DataFile holds a row of information for a file.
 */
public class DataFile {

    /**
     * The unique identifier associated with this file. It's final, because we
     * never want to change it.
     */

    public  int mfileId;

    public int mCommentID;

    /**
     * The file description of this file.
     */
    public String mFileDescription;

    /**
     * The path of this file.
     */
    public String mFilePath;

    /**
     * The id of the user who uploaded this file.
     */
    public  int mUserID;

    /**
     * The id of the idea associated with this file.
     */
    public  int mIdeaID;

    /**
     * The number of likes for this file.
     */
    public final int mLikes;

    /**
     * The number of comments for this file.
     */
    public final int mComments;

    /**
     * The creation date for this file row. Once it is set, it cannot be changed.
     */
    public  Date mCreated;

    /**
     * Denotes if user is valid. If true, the user can use the website/app.
     */
    public boolean mValid;

    /**
     * Creates a new DataFile object with the given parameters.
     */
    public DataFile(int fileID, String fileDescription, String filePath, int userID, int ideaID, int commentID) {
        mfileId = fileID;
        mFileDescription = fileDescription;
        mFilePath = filePath;
        mUserID = userID;
        mIdeaID = ideaID;
        mCommentID = commentID;
        mLikes = 0;
        mComments = 0;
        mValid = true;
        mCreated = new Date();
    }

    /**
     * Creates a new DataFile object with the given parameters.
     */
    public DataFile(int fileID, String fileDescription, String filePath, int userID, int ideaID, int commentID,
            int likes, int comments, Timestamp created, boolean valid) {
        mfileId = fileID;
        mFilePath = filePath;
        mIdeaID = ideaID;
        mUserID = userID;
        mCommentID = commentID;
        mLikes = likes;
        mComments = comments;
        mValid = valid;
        mCreated = new Date(created.getTime());
    }

    /**
     * Creates a new DataFile object that is a copy of the given DataFile object.
     */
    public DataFile(DataFile data) {
        mfileId = data.mfileId;
        mFileDescription = data.mFileDescription;
        mFilePath = data.mFilePath;
        mUserID = data.mUserID;
        mIdeaID = data.mIdeaID;
        mCommentID = data.mCommentID;
        mLikes = data.mLikes;
        mComments = data.mComments;
        mValid = data.mValid;
        mCreated = data.mCreated;
    }
}
