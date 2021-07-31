package apps.live.controllers;

import GsonHandler.GsonHandler;
import Holder.ResponseHolder;
import Holder.TokenHolder;
import Models.Game.GridChoice;
import Models.Live.LiveGame;
import Models.Request;
import Models.RequestType;
import Properties.properties;
import StreamHandler.StreamHandler;
import apps.live.views.LiveGameView;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;

public class LiveGameController {
    public LiveGameView liveGame;
    public int GameID;
    public boolean done = false;
    public LiveGameController(int gameID) {
        liveGame = new LiveGameView(this);
        GameID = gameID;
    }

    public void main() {
        sendGetGameRequest();
    }

    public void sendGetGameRequest() {
        StreamHandler.sendRequest(new Request(RequestType.Live, "", TokenHolder.Token, GameID));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                ResponseHolder.response = StreamHandler.getResponse();
                return null;
            }
        };
        task.setOnSucceeded((e)-> GetGameResponseIsBack());
        Thread thread = new Thread(task);
        thread.start();
    }


    public void GetGameResponseIsBack() {
        if (done) {
            return;
        }
        liveGame.main(GsonHandler.getGson().fromJson(ResponseHolder.response.data, LiveGame.class));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                sleep(1000);
                return null;
            }
        };
        task.setOnSucceeded((e)->sendGetGameRequest());
        Thread thread = new Thread(task);
        thread.start();
    }

}
