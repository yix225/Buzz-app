package edu.lehigh.cse216.team_m.backend;

/**
 * SimpleRequest provides a format for clients to present title and message 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequest {
    /**
     * The title being provided by the client.
     */
    public String mSubject;

    /**
     * The message being provided by the client.
     */
    public String mMessage;

    public String mfilePath;

    public String mfileType;
}