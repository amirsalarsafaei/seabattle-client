package apps.live.views;

import Holder.StageHolder;
import Models.Live.GameStateList;
import Models.Live.GameStateListMode;
import Models.Live.PlayerListMode;
import Properties.properties;
import apps.live.controllers.ListController;
import apps.live.controllers.LiveGameController;
import apps.menu.views.MainMenu;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameList {
    public ListController listController;
    public GameList(ListController listController) {
        this.listController = listController;
    }
    public void main(GameStateList gameStateList) {

        BorderPane borderPane = new BorderPane();
        VBox ListBox = new VBox(properties.loadSize("medium-indent"));

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("style.css");
        for (GameStateListMode gameStateListMode:gameStateList.gameStateLists) {
            ListBox.getChildren().add(getGameState(gameStateListMode));
        }
        ScrollPane ListPane = new ScrollPane(ListBox);
        HBox alignmentBox = new HBox(ListPane);
        alignmentBox.setAlignment(Pos.TOP_CENTER);
        borderPane.setCenter(alignmentBox);
        ListPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        ListPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);

        Button menuButton = new Button(properties.loadDialog("menu"));
        VBox menuButtonBox = new VBox(menuButton);
        menuButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        menuButtonBox.setPadding(new Insets(properties.loadSize("medium-indent")));
        borderPane.setBottom(menuButtonBox);

        StageHolder.stage.setScene(scene);

        menuButton.setOnAction(this::menuButtonPressed);

    }

    private void menuButtonPressed(ActionEvent actionEvent) {
        MainMenu mainMenu = new MainMenu();
        listController.done = true;
        mainMenu.main();
    }

    public HBox getGameState(GameStateListMode gameStateListMode) {
        HBox mainBox = new HBox(properties.loadSize("big-indent"));
        Label player1Username = new Label(properties.loadDialog("player1") + ":\t"
                + gameStateListMode.player1.username);
        Label player2Username = new Label(properties.loadDialog("player2") + ":\t"
                + gameStateListMode.player2.username);
        VBox player1Box = new VBox(properties.loadSize("small-indent"), player1Username);
        VBox player2Box = new VBox(properties.loadSize("small-indent"), player2Username);
        makePlayerBox(player1Box, gameStateListMode.player1, gameStateListMode.player2.killedShips);
        makePlayerBox(player2Box, gameStateListMode.player2, gameStateListMode.player1.killedShips);
        mainBox.setId("live-game-box");
        mainBox.setPadding(new Insets(properties.loadSize("big-indent")));
        mainBox.getChildren().addAll(player1Box, player2Box);
        mainBox.setOnMouseClicked((e)->{
            GamePressed(gameStateListMode.gameID);
        });
        return mainBox;
    }

    public void makePlayerBox(VBox playerBox, PlayerListMode playerListMode, int brokeShips) {
        Label missedShots = new Label(properties.loadDialog("missed-shots") + ":\t"
                + playerListMode.missedShots);
        Label landedShots = new Label(properties.loadDialog("landed-shots") + ":\t"
                + playerListMode.landedShots);
        Label killedShips = new Label(properties.loadDialog("killed-ships") + ":\t"
                + playerListMode.killedShips);
        Label healthyShips = new Label(properties.loadDialog("healthy-ships") + ":\t"
                + (playerListMode.Ships - brokeShips));
        playerBox.getChildren().addAll(missedShots, landedShots, killedShips, healthyShips);
    }

    private void GamePressed(int gameId) {
        listController.done = true;
        LiveGameController liveGameController = new LiveGameController(gameId);
        liveGameController.main();
    }



}
