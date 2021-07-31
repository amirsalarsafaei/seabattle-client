package main;

import Holder.DataStreamHolder;
import Holder.SocketHolder;
import Holder.StageHolder;
import Properties.properties;
import apps.auth.views.menu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class main {
    public static Stage stage;
    public static class YourRealApplication extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            main.stage = stage;
            stage.setResizable(false);
            stage.setWidth(properties.loadSize("frame-width"));
            stage.setHeight(properties.loadSize("frame-height"));
            stage.show();
            StageHolder.stage = stage;
            menu menu1 = new menu();
            menu1.main();
        }

    }
    public static void main(String[] args) {

        try {
            SocketHolder.socket = new Socket(properties.loadServerConfig("address"),
                    Integer.valueOf(properties.loadServerConfig("port")));
            DataStreamHolder.dataIn = new DataInputStream(SocketHolder.socket.getInputStream());
            DataStreamHolder.dataOut = new DataOutputStream(SocketHolder.socket.getOutputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Application.launch(YourRealApplication.class);
    }

}