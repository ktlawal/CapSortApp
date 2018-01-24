/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capsortserver;

import java.io.Serializable;

/**
 *
 * Date: 11/12/2017
 * @author Kareem Lawal
 */
public class Message implements Serializable {

    private String message;
    private int clientID;
    private String trackClients;
    private String clients;
//    private  final double timeStamp;

    public Message() {
        this.message = null;
//        this.timeStamp = timeStamp();
    }

//    public Message(String a) {
//        this.message = a;
//        this.timeStamp = timeStamp();
//    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setTrackClients(String trackClients) {
        this.trackClients = trackClients;
    }

    public String getTrackClients() {
        return trackClients;
    }

    public void setClients(String clients) {
        this.clients = clients;
    }

    public String getClients() {
        return clients;
    }

//    public double getTimeStamp() {
//        return timeStamp;
//    }
}
