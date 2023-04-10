package edu.lehigh.cse216.team_m.admin;

public class DataLike {
    /**
     * The id of the idea the like belongs to
     */
    public final int mIdeaId;

    /**
     * the id of the comment the like belongs to
     */
    public final int mComId;
    
    /**
     * The id of the user who the like belongs to
     */
    public final int mUserId;

    /**
     * The status of the like. 0 is neutral(not up-voted or down-voted). 1 is up-voted, -1 is down-voted
     */
    public int mStatus;


    /**
     * Constructor for an Idea Like. Create new DataLike with ideaId, userId, and status.
     * The comId is automatically set to -1 meaning that it is not a comment.
     * 
     * @param ideaId for the id of Idea being liked
     * 
     * @param userId for the id of User liking
     * 
     * @param status for the status of the like
     */
    DataLike(int ideaId, int userId, int status) {
        mIdeaId = ideaId;
        mUserId = ideaId;
        mComId = (Integer)null;
        mStatus = status;
    }

    /**
     * Constructor for a Comment like. Create new DataLike with ideaId, userId, status, and comId.
     * 
     * @param ideaId for the id of Idea the comment is under
     * 
     * @param userId for the id of User liking
     * 
     * @param comId for the id of the comment being liked
     * 
     * @param status for the status of the like
     */
    DataLike(int ideaId, int userId, int comId, int status) {
        mIdeaId = ideaId;
        mUserId = ideaId;
        mComId = comId;
        mStatus = status;
    }

     /**
     * Copy constructor to create one datalike from another
     * @param data The dataLike you are copying from
     */
    DataLike(DataLike data) {
        mIdeaId = data.mIdeaId;
        mUserId = data.mUserId;
        mComId = data.mComId;
        mStatus = data.mStatus;
    }
    
}
