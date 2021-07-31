package apps.scoreBoard.controllers;

import GsonHandler.GsonHandler;
import Holder.ResponseHolder;
import Holder.TokenHolder;
import Models.Request;
import Models.RequestType;
import Models.ScoreBoard.ScoreBoardList;
import StreamHandler.StreamHandler;
import apps.scoreBoard.views.ScoreBoardView;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;

public class ScoreBoardController {
    ScoreBoardView scoreBoardView;
    public boolean done = false;
    public ScoreBoardController() {
        scoreBoardView = new ScoreBoardView(this);
        sendGetScoreBoardRequest();
    }
    public void sendGetScoreBoardRequest() {
        if (done)
            return;
        StreamHandler.sendRequest(new Request(RequestType.ScoreBoard, "", TokenHolder.Token));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                ResponseHolder.response = StreamHandler.getResponse();
                return null;
            }
        };
        task.setOnSucceeded((e)->GetScoreBoardResponseIsBack());
        Thread thread = new Thread(task);
        thread.start();
    }

    public void GetScoreBoardResponseIsBack() {
        if (done)
            return;
        ScoreBoardList scoreBoardList = GsonHandler.getGson().fromJson(ResponseHolder.response.data, ScoreBoardList.class);
        scoreBoardView.main(scoreBoardList);
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                sleep(1000);
                return null;
            }
        };
        task.setOnSucceeded((e)->sendGetScoreBoardRequest());
        Thread thread = new Thread(task);
        thread.start();
    }
}
