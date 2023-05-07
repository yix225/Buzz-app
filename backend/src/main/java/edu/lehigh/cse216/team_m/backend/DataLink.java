package edu.lehigh.cse216.team_m.backend;

import java.sql.Timestamp;

public class DataLink extends DataIdea {

    /**
     * The id of Idea the comment is under, it is final as it does not change once
     * set.
     */
    public final int mIdeaId;

    DataLink(int id, String fileDescription, String filePath, int userId, int likes, int comments, boolean valid,
            int ideaId, Timestamp created) {
        super(id, fileDescription, filePath, userId, likes, comments, valid, created);
        mIdeaId = ideaId;
    }

    /**
     * Copy constructor to create one datacomment from another
     * 
     * @param data The dataComment you are copying from
     */
    DataLink(DataLink data) {
        super(data);
        mIdeaId = data.mIdeaId;
    }
}
