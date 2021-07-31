package apps.menu.views;

import Holder.StageHolder;
import Properties.properties;
import Utils.PageInterface;
import apps.Profile.controllers.ProfileController;
import apps.game.controller.MatchingController;
import apps.game.views.Matching;
import apps.menu.controller.MainMenuController;
import apps.scoreBoard.controllers.ScoreBoardController;
import assets.ImageHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static java.lang.Thread.sleep;

public class MainMenu implements PageInterface {
    public MainMenuController mainMenuController;
    public void main() {
        mainMenuController = new MainMenuController();
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(ImageHandler.getImage("logo"));

        VBox mainBox = new VBox(properties.loadSize("big-indent"));
        mainBox.setAlignment(Pos.CENTER);
        mainPane.setCenter(mainBox);

        Button startGameButton = new Button(properties.loadDialog("start-game"));
        Button WatchGameButton = new Button(properties.loadDialog("watch-game"));
        Button ScoreBoardButton = new Button(properties.loadDialog("scoreboard"));
        Button ProfileButton = new Button(properties.loadDialog("profile"));
        mainBox.getChildren().addAll(startGameButton, WatchGameButton, ScoreBoardButton, ProfileButton);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);

        startGameButton.setOnAction(this::startButtonPushed);
        WatchGameButton.setOnAction(this::liveGameButtonPressed);
        ScoreBoardButton.setOnAction(this::ScoreBoardButtonPressed);
        ProfileButton.setOnAction(this::profileButtonPressed);
    }

    private void profileButtonPressed(ActionEvent actionEvent) {
        new ProfileController();
    }

    private void ScoreBoardButtonPressed(ActionEvent actionEvent) {
        new ScoreBoardController();
    }

    private void liveGameButtonPressed(ActionEvent actionEvent) {
        mainMenuController.ShowGameList();
    }

    private void startButtonPushed(ActionEvent actionEvent) {
        mainMenuController.matchMakingButtonPressed();
    }
}
