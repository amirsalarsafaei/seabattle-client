package apps.auth.controller;

import GsonHandler.GsonHandler;
import Holder.TokenHolder;
import Holder.UsernameHolder;
import Models.*;
import Properties.properties;
import StreamHandler.StreamHandler;
import apps.auth.views.Login;
import apps.auth.views.menu;
import apps.auth.views.register;
import apps.menu.views.MainMenu;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;

public class Authenticate {
    public void signUp(String username, String password1, String password2, register reg) {
        if (!password1.equals(password2)) {
            reg.errorLabel.setText(properties.loadDialog("password-mismatch"));
            return;
        }
        if (password1.length() == 0 || username.length() == 0) {
            reg.errorLabel.setText(properties.loadDialog("empty-field"));
            return;
        }
        StreamHandler.sendRequest(new
                Request(RequestType.SignUp, GsonHandler.getGson().toJson(new BaseUser(username, password1)), null));
        Response response = StreamHandler.getResponse();
        if (response.responseType == ResponseType.Created) {
            menu menu1 = new menu();
            menu1.main();
            return;
        }
        reg.errorLabel.setText(response.data);
    }

    public void signIn(String username, String password, Login login) {
        StreamHandler.sendRequest(new
                Request(RequestType.SignIn, GsonHandler.getGson().toJson(new BaseUser(username, password)), null));
        Response response = StreamHandler.getResponse();
        if (response.responseType == ResponseType.Accepted) {
            TokenHolder.Token = response.data;
            UsernameHolder.username = username;
            sendOnlineRequest();
            MainMenu mainMenu = new MainMenu();
            mainMenu.main();
            return;
        }
        login.errorLabel.setText(response.data);
    }

    public void sendOnlineRequest() {
        StreamHandler.sendRequest(new Request(RequestType.Online, "", TokenHolder.Token));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                sleep(1000);
                return null;
            }
        };
        task.setOnSucceeded((e)->sendOnlineRequest());
        Thread thread = new Thread(task);
        thread.start();
    }
}
