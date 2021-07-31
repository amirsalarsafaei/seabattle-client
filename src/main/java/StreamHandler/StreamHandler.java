package StreamHandler;

import GsonHandler.GsonHandler;
import Holder.DataStreamHolder;
import Models.Request;
import Models.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class StreamHandler {
    private static final Logger logger = LogManager.getLogger(StreamHandler.class);
    public synchronized static void sendRequest( Request request) {
        DataOutputStream dataOut = DataStreamHolder.dataOut;
        try {
            dataOut.writeUTF(GsonHandler.getGson().toJson(request));
            dataOut.flush();
        } catch (IOException ioException) {
            logger.error("Couldn't send Request to server");
        }
    }

    public static Response getResponse() {
        try {
            sleep(200);
        } catch (InterruptedException interruptedException) {
            logger.error("sleep thread interrupted");
        }
        DataInputStream dataIn = DataStreamHolder.dataIn;
        try {
            return GsonHandler.getGson().fromJson(dataIn.readUTF(), Response.class);
        } catch (IOException ioException) {
            logger.error("Couldn't get response from server");
        }
        return null;
    }
}
