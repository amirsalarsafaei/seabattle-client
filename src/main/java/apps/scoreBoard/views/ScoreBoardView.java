package apps.scoreBoard.views;

import Holder.StageHolder;
import Models.ScoreBoard.ScoreBoardList;
import Models.ScoreBoard.ScoreBoardUser;
import Properties.properties;
import apps.menu.views.MainMenu;
import apps.scoreBoard.controllers.ScoreBoardController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScoreBoardView {
    ScoreBoardController scoreBoardController;
    public ScoreBoardView(ScoreBoardController scoreBoardController) {
        this.scoreBoardController = scoreBoardController;
    }
    public void main(ScoreBoardList scoreBoardList) {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        VBox ScrollBox = new VBox(properties.loadSize("medium-indent"));

        ScrollPane scrollPane = new ScrollPane(ScrollBox);
        HBox alignmentBox = new HBox(scrollPane);
        alignmentBox.setAlignment(Pos.TOP_CENTER);
        mainPane.setCenter(alignmentBox);
        ScrollBox.setAlignment(Pos.TOP_CENTER);
        for (ScoreBoardUser user:scoreBoardList.ScoreBoardUser) {
            HBox userBox = new HBox(properties.loadSize("big-indent"));
            Label usernameLabel = new Label(user.username);
            Label pointsLabel = new Label(String.valueOf(user.points));
            Label status = null;
            if (user.Online)
                status = new Label(properties.loadDialog("online"));
            else
                status = new Label(properties.loadDialog("offline"));
            userBox.getChildren().addAll(usernameLabel, pointsLabel, status);
            userBox.setId("live-game-box");
            userBox.setPadding(new Insets(properties.loadSize("small-indent")));
            ScrollBox.getChildren().add(userBox);
        }

        Button menuButton = new Button(properties.loadDialog("menu"));
        VBox menuButtonBox = new VBox(menuButton);
        menuButtonBox.setPadding(new Insets(properties.loadSize("medium-indent")));
        mainPane.setTop(menuButtonBox);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);


        menuButton.setOnAction(this::menuButtonPressed);
    }

    private void menuButtonPressed(ActionEvent actionEvent) {
        scoreBoardController.done = true;
        MainMenu mainMenu = new MainMenu();
        mainMenu.main();
    }
}
