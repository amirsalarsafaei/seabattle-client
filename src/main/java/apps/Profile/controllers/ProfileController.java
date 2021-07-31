package apps.Profile.controllers;

import GsonHandler.GsonHandler;
import Holder.ResponseHolder;
import Holder.TokenHolder;
import Models.Profile.Profile;
import Models.Request;
import Models.RequestType;
import StreamHandler.StreamHandler;
import apps.Profile.views.ProfileView;
import javafx.concurrent.Task;

public class ProfileController {
    ProfileView profileView;
    public ProfileController() {
        profileView = new ProfileView();
        sendGetProfileRequest();
    }

    public void sendGetProfileRequest() {
        StreamHandler.sendRequest(new Request(RequestType.Profile, "", TokenHolder.Token));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                ResponseHolder.response = StreamHandler.getResponse();
                return null;
            }
        };
        task.setOnSucceeded((e)->getProfileResponseIsBack());
        Thread thread = new Thread(task);
        thread.start();
    }

    public void getProfileResponseIsBack() {
        Profile profile = GsonHandler.getGson().fromJson(ResponseHolder.response.data, Profile.class);
        profileView.main(profile);
    }
}
