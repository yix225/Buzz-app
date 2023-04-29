package edu.lehigh.cse216.team_m.backend;

import java.sql.Timestamp;

public class DataFile extends DataIdea {

    /**
     * The id of Idea the comment is under, it is final as it does not change once set.
     */
    public final int mIdeaId;
  
    DataFile(int id, String filePath, String fileName, String fileType, int userId, String fileDescription, int ideaId){
        super(id,filePath,fileName,fileType,userId,fileDescription);
        mIdeaId = ideaId;
    }
}
