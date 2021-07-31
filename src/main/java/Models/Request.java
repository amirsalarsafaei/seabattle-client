package Models;

import Models.BaseUser;
import Models.RequestType;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Request {
    public RequestType requestType;
    public String data;
    public String token;
    public User user;
    public DataInputStream dataIn;
    public DataOutputStream dataOut;
    public int GameID;
    public Request(RequestType requestType, String data, String token) {
        this.requestType = requestType;
        this.data = data;
        this.token = token;
    }

    public Request(RequestType requestType, String data, String token, int GameID) {
        this.requestType = requestType;
        this.data = data;
        this.token = token;
        this.GameID = GameID;
    }
}
