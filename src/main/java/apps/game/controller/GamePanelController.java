package apps.game.controller;

import GsonHandler.GsonHandler;
import Holder.*;
import Models.Game.Coordinate;
import Models.Game.GamePhase;
import Models.Game.GridChoice;
import Models.Game.Player;
import Models.Request;
import Models.RequestType;
import Models.Response;
import Models.ResponseType;
import Properties.properties;
import StreamHandler.StreamHandler;
import apps.game.views.EndGame;
import apps.game.views.GamePanel;
import com.sun.jdi.ThreadReference;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Thread.sleep;

public class GamePanelController {
    public boolean done = false;
    public void GamePanelClicked(int x, int y, Player player) {
        Coordinate coordinate = gamePanelCoordToGridCoord(x, y, player.gridChoice);
        if (coordinate != null) {
            if (player.gamePhase != GamePhase.Playing || player.turnEndTime.isBefore(LocalDateTime.now()))
                return;
            StreamHandler.sendRequest(new Request(RequestType.Shot, GsonHandler.getGson().toJson(coordinate), TokenHolder.Token, player.GameID));
            player.gamePhase = GamePhase.Waiting;
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    Response response = StreamHandler.getResponse();
                    ResponseHolder.response = response;
                    return null;
                }
            };

            task.setOnSucceeded((e)-> {
                GamePlayResponseIsBack();
            });
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public Coordinate gamePanelCoordToGridCoord(int x, int y, GridChoice gridChoice) {
        if (x >= properties.loadSize("right-grid-startX") && x <= properties.loadSize("right-grid-endX")
            && y >= properties.loadSize("right-grid-startY") && y <=properties.loadSize("right-grid-endY")
                && gridChoice != GridChoice.right ) {
            Coordinate coordinate =
                    new Coordinate(
                            (x - properties.loadSize("right-grid-startX"))/
                                    properties.loadSize("grid-length"),
                            (y - properties.loadSize("right-grid-startY"))/
                                    properties.loadSize("grid-length")
                    );
            return coordinate;
        }

        if (x >= properties.loadSize("left-grid-startX") && x <= properties.loadSize("left-grid-endX")
                && y >= properties.loadSize("left-grid-startY") && y <=properties.loadSize("left-grid-endY")
                    && gridChoice != GridChoice.left) {
            Coordinate coordinate =
                    new Coordinate(
                            (x - properties.loadSize("left-grid-startX"))/
                                    properties.loadSize("grid-length"),
                            (y - properties.loadSize("left-grid-startY"))/
                                    properties.loadSize("grid-length")
                    );
            return coordinate;
        }
        return null;
    }


    public void reArrange(Player player) {
        if (player.timesReArranged == 3)
            return;
        StreamHandler.sendRequest(new Request(RequestType.ReArrange, String.valueOf(GameIDHolder.gameID), TokenHolder.Token));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Response response = null;
                response= StreamHandler.getResponse();
                ResponseHolder.response = response;
                return null;
            }
        };
        task.setOnSucceeded((e)->{
            ReArrangeResponseIsBack();
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    public void ReArrangeResponseIsBack() {
        Response response = ResponseHolder.response;
        GamePanel gamePanel = new GamePanel();
        gamePanel.gamePanelController = new GamePanelController();
        gamePanel.timeLabel = new Label();
        LabelHolder.ArrangeLabelHolder = gamePanel.timeLabel;
        Player player = GsonHandler.getGson().fromJson(response.data, Player.class);
        TimeHolder.ArrangementTimeLeft=(int) LocalDateTime.from(LocalDateTime.now()).until(player.turnEndTime, ChronoUnit.SECONDS);;;
        gamePanel.main(player);
    }

    public void setChangingTimer(Player player) {
        Label timeLabel = LabelHolder.ArrangeLabelHolder;
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                sleep(100);
                return null;
            }
        };
        task.setOnSucceeded((e)->{
            if (player.turnEndTime.isAfter(LocalDateTime.now())) {
                timeLabel.setText(String.valueOf(LocalDateTime.from(LocalDateTime.now()).until(player.turnEndTime, ChronoUnit.SECONDS)));
                setChangingTimer(player);
            }
            else {
                sendReadyRequest(player);
            }
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    public void sendReadyRequest(Player player) {
        if (GamePhases.ReadyRequestSent)
            return;
        GamePhases.ReadyRequestSent = true;
        StreamHandler.sendRequest(new Request(RequestType.Ready, "", TokenHolder.Token, GameIDHolder.gameID));
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Response response = StreamHandler.getResponse();
                if (response.responseType != ResponseType.Accepted) {
                    System.out.println("Shir2");
                    throw new Exception("Shir");
                }
                ResponseHolder.response = response;
                return null;
            }
        };
        task.setOnSucceeded((e)->{
            ReadyResponseIsBack();
        });
        Thread thread = new Thread(task);
        thread.start();
        player.gamePhase = GamePhase.Ready;
        GamePanel gamePanel = new GamePanel();
        gamePanel.gamePanelController = this;
        gamePanel.main(player);
    }

    public void ReadyResponseIsBack() {
        if (GamePhases.ReadyResponseIsBack)
            return;

        Response response = ResponseHolder.response;
        GamePanel gamePanel = new GamePanel();
        gamePanel.gamePanelController = this;
        gamePanel.timeLabel = new Label();
        Player player = GsonHandler.getGson().fromJson(response.data, Player.class);
        if (player.gamePhase == GamePhase.Ready) {

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    Response response = StreamHandler.getResponse();
                    if (response.responseType != ResponseType.Accepted) {
                        System.exit(0);
                    }
                    ResponseHolder.response = response;
                    return null;
                }
            };
            task.setOnSucceeded((e) -> {
                ReadyResponseIsBack();
            });
            Thread thread = new Thread(task);
            thread.start();

            gamePanel.main(player);
        } else {
            GamePhases.ReadyResponseIsBack = true;
            GamePlayResponseIsBack();
        }

    }


    public void setChangingTimerGame(int turn, Player player) {
        Label timeLabel = LabelHolder.TurnLabelHolder;
        if (turn != GameTurnHolder.Turn)
            return;
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                sleep(100);
                return null;
            }
        };
        task.setOnSucceeded((e)->{
            if (player.turnEndTime.isAfter(LocalDateTime.now())) {
                timeLabel.setText(String.valueOf(LocalDateTime.from(LocalDateTime.now()).until(player.turnEndTime, ChronoUnit.SECONDS)));
                setChangingTimerGame(turn, player);
            }
            else {
                if (player.gamePhase == GamePhase.Playing) {
                    sendGameTurnDoneRequest();
                }
            }
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    public void GamePlayResponseIsBack() {
        Response response = ResponseHolder.response;
        Player player = GsonHandler.getGson().fromJson(response.data, Player.class);
        if (player.gamePhase == GamePhase.Lose || player.gamePhase == GamePhase.Win) {
            endGameResponseIsBack();
            return;
        }

        TimeHolder.TurnTimeLeft = (int) LocalDateTime.from(LocalDateTime.now()).until(player.turnEndTime, ChronoUnit.SECONDS);
        GamePanel gamePanel = new GamePanel();
        gamePanel.gamePanelController = this;
        gamePanel.timeLabel = new Label(String.valueOf(TimeHolder.TurnTimeLeft));
        LabelHolder.TurnLabelHolder = gamePanel.timeLabel;
        setChangingTimerGame(++GameTurnHolder.Turn, player);
        if (player.gamePhase == GamePhase.Waiting) {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    Response response = StreamHandler.getResponse();
                    ResponseHolder.response = response;
                    return null;
                }
            };
            task.setOnSucceeded((e) -> {
                GamePlayResponseIsBack();
            });
            Thread thread = new Thread(task);
            thread.start();
        }

        gamePanel.main(player);

    }

    public void sendGameTurnDoneRequest() {
        StreamHandler.sendRequest(new Request(RequestType.TurnDone, "", TokenHolder.Token));

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Response response = StreamHandler.getResponse();
                ResponseHolder.response = response;
                return null;
            }
        };
        task.setOnSucceeded((e) -> {
            GamePlayResponseIsBack();
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    public void endGameResponseIsBack() {
        if (done)
            return;
        GamePhases.ReadyRequestSent = false;
        GamePhases.ReadyResponseIsBack = false;
        Response response = ResponseHolder.response;
        Player player = GsonHandler.getGson().fromJson(response.data, Player.class);
        GameIDHolder.gameID = -1;
        GameTurnHolder.Turn = 0;
        GameUsernameHolder.versusUser = "";
        ResponseHolder.response = null;
        EndGame endGame = new EndGame(this);
        endGame.main(player);

    }

}
