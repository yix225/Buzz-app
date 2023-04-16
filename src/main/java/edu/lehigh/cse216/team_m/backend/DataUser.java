package edu.lehigh.cse216.team_m.backend;

import java.util.Date;
import java.sql.Timestamp;

 /**
 * DataUser holds a row of information for a user. 
 * 
 */

public class DataUser {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mId;

    /**
     * The name for this user
     */
    public String mName;

    /**
     * The email for this user
     */
    public String mEmail;

    /**
     * The gender identity for this user
     */
    public String mGenId;

    /**
     * The sexual orientation for this user
     */
    public String mSexOtn;

    /**
     * The note, a short description, for this user
     */
    public String mNote;

    /**
     * Denotes if user is valid. If true, the user can use the website/app
     */
    public boolean mValid;

    /**
     * The creation date for this idea row.  Once it is set, it cannot be 
     * changed
     */
    public final Date mCreated;
  
    /**
     * 
     * Default Constructor: Create a new DataUser with the provided id, name, email,
     * genId, sexOtn, note, and a creation date based on the system clock at the time 
     * the constructor was called.
     * 
     * @param id the id of the user
     * 
     * @param name the username of the user
     * 
     * @param email the email of the user
     * 
     * @param genId the gender identity of the user
     * 
     * @param sexOtn the sexual orientation of the user
     * 
     * @param note the note/description of the user
     * 
     */
    DataUser(int id, String name, String email, String genId, String sexOtn, String note) {
        mId = id;
        mName = name;
        mEmail = email;
        mGenId = genId;
        mSexOtn = sexOtn;
        mNote = note;
        mValid = true;
        mCreated = new Date();
    }

    /**
     * 
     * Create a new DataUser with the provided id, name, email, genId, sexOtn, note, and 
     * validness. A creation date based on the system clock at the time the constructor 
     * was called
     * 
     * @param id the id of the user
     * 
     * @param name the username of the user
     * 
     * @param email the email of the user
     * 
     * @param genId the gender identity of the user
     * 
     * @param sexOtn the sexual orientation of the user
     * 
     * @param note the note/description of the user
     * 
     * @param valid the validity of the user
     * 
     */
    DataUser(int id, String name, String email, String genId, String sexOtn, String note, boolean valid, Timestamp created) {
        mId = id;
        mName = name;
        mEmail = email;
        mGenId = genId;
        mSexOtn = sexOtn;
        mNote = note;
        mValid = valid;
        mCreated = new Date(created.getTime());
    }


     /**
     * Copy constructor to create one datacomment from another
     * @param data The dataComment you are copying from
     */
    DataUser(DataUser data){
        mId = data.mId;
        mName = data.mName;
        mEmail = data.mEmail;
        mGenId = data.mGenId;
        mSexOtn = data.mSexOtn;
        mNote = data.mNote;
        mCreated = data.mCreated;
    }
}

