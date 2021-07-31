package apps.game.controller;

import GsonHandler.GsonHandler;
import Holder.*;
import Models.Game.GameState;
import Models.Game.Player;
import Models.Request;
import Models.RequestType;
import Models.Response;
import Models.ResponseType;
import StreamHandler.StreamHandler;
import apps.game.views.GamePanel;
import apps.menu.views.MainMenu;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.swing.plaf.TableHeaderUI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MatchingController {

    public void main() {

        StreamHandler.sendRequest(new Request(RequestType.StartMatch, "", TokenHolder.Token));


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
            ResponseIsBack();
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    public void ResponseIsBack() {
        Response response = ResponseHolder.response;
        if (response.responseType == ResponseType.Accepted) {
            GamePanel gamePanel = new GamePanel();
            Player player = GsonHandler.getGson().fromJson(response.data, Player.class);
            GameIDHolder.gameID = player.GameID;
            GameUsernameHolder.versusUser = player.versusUsername;
            gamePanel.timeLabel = new Label();
            LabelHolder.ArrangeLabelHolder = gamePanel.timeLabel;
            gamePanel.gamePanelController = new GamePanelController();
            gamePanel.gamePanelController.setChangingTimer(player);
            gamePanel.main(player);
        }
        else {
            MainMenu mainMenu = new MainMenu();
            mainMenu.main();
        }

    }
}
