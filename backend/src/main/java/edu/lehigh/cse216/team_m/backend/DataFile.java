package edu.lehigh.cse216.team_m.backend;

import java.sql.Timestamp;

public class DataFile extends DataIdea {

    /**
     * The id of Idea the comment is under, it is final as it does not change once
     * set.
     */
    public final int mIdeaId;

    public String mfilePath; 
    public String mfileType;
    DataFile(int id, String subject, String message, int userId, int likes, int comments, boolean valid,
            int ideaId, Timestamp created,String filePath, String fileType) {
        super(id, subject, message, userId, likes, comments, valid, created);
        mfilePath = filePath;
        mfileType = fileType;
        mIdeaId = ideaId;
    }

    /**
     * Copy constructor to create one datacomment from another
     * 
     * @param data The dataComment you are copying from
     */
    DataFile(DataFile data) {
        super(data);
        mIdeaId = data.mIdeaId;
    }
}
