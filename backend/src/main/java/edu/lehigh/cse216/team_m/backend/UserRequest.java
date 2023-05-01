package edu.lehigh.cse216.team_m.backend;
​
/**
 * SimpleRequest provides a format for clients to present title and message 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class UserRequest {
    /**
     * The name being provided by the client.
     */
    public String mName;
​
    /**
     * The email being provided by the client.
     */
    public String mEmail;
​
    /**
     * The email being provided by the client.
     */
    public String mGenId;
    
    /**
     * The email being provided by the client.
     */
    public String mSexOtn;
​
    /**
     * The email being provided by the client.
     */
    public String mNote;
}