package apps.game.views;

import Holder.StageHolder;
import Models.Game.GamePhase;
import Models.Game.Player;
import Properties.properties;
import apps.game.controller.GamePanelController;
import apps.menu.views.MainMenu;
import assets.ImageHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EndGame {
    GamePanelController gamePanelController;
    public EndGame(GamePanelController gamePanelController) {
        this.gamePanelController = gamePanelController;
    }

    public void main(Player player) {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        HBox mainBox = new HBox(properties.loadSize("medium-indent"));
        Button menu = new Button(properties.loadDialog("menu"));
        VBox alignmentBox = new VBox(properties.loadSize("medium-indent"),mainBox, menu);
        alignmentBox.setAlignment(Pos.CENTER);
        mainBox.setAlignment(Pos.CENTER);
        Label winner = null, loser = null;
        if (player.gamePhase == GamePhase.Lose) {
            winner = new Label(player.versusUsername);
            loser = new Label(player.username);
        }
        else {
            loser = new Label(player.versusUsername);
            winner = new Label(player.username);
        }
        mainBox.getChildren().addAll(loser, ImageHandler.getImage("gigachad-gif"), winner);
        mainPane.setCenter(alignmentBox);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);


        menu.setOnAction(this::menuButtonPressed);
    }

    private void menuButtonPressed(ActionEvent actionEvent) {
        gamePanelController.done = true;
        MainMenu mainMenu = new MainMenu();
        mainMenu.main();
    }
}
