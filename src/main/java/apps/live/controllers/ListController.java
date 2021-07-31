package apps.live.controllers;

import GsonHandler.GsonHandler;
import Holder.ResponseHolder;
import Holder.TokenHolder;
import Models.Live.GameStateList;
import Models.Request;
import Models.RequestType;
import Models.Response;
import StreamHandler.StreamHandler;
import apps.live.views.GameList;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;

public class ListController {
    apps.live.views.GameList GameList;
    public boolean done = false;
    public void main() {
        GameList = new GameList(this);
        setTiming();
    }

    public void sendListRequest() {
        if (done)
            return;
        StreamHandler.sendRequest(new Request(RequestType.LiveList, "", TokenHolder.Token));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Response response = StreamHandler.getResponse();
                ResponseHolder.response = response;
                return null;
            }
        };
        task.setOnSucceeded((e)->{
            ListResponseIsBack();
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    public void ListResponseIsBack() {
        Response response = ResponseHolder.response;
        GameStateList gameStateList = GsonHandler.getGson().fromJson(response.data, GameStateList.class);
        if (done)
            return;
        GameList.main(gameStateList);
    }

    public void setTiming() {
        if (done)
            return;
        sendListRequest();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                sleep(500);
                return null;
            }
        };
        task.setOnSucceeded((e)->{
            setTiming();
        });
        Thread thread = new Thread(task);
        thread.start();
    }


}
