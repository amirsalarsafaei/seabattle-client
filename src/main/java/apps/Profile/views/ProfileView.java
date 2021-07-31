package apps.Profile.views;

import Holder.StageHolder;
import Models.Profile.Profile;
import Properties.properties;
import apps.menu.views.MainMenu;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileView {
    public void main(Profile profile) {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        VBox profileBox = new VBox(properties.loadSize("medium-indent"));
        Label UsernameLabel = new Label(properties.loadDialog("username") + ":\t" + profile.username);
        Label WinsLabel = new Label(properties.loadDialog("wins") + ":\t" + profile.wins);
        Label LosesLabel = new Label(properties.loadDialog("loses") + ":\t" + profile.loses);
        Label PointsLabel = new Label(properties.loadDialog("points") + ":\t" + profile.points);
        HBox gameBox = new HBox(properties.loadSize("medium-indent"), WinsLabel, LosesLabel, PointsLabel);
        profileBox.getChildren().addAll(UsernameLabel, gameBox);
        HBox alignmentBox = new HBox(profileBox);
        profileBox.setId("live-game-box");
        profileBox.setPadding(new Insets(properties.loadSize("medium-indent")));
        alignmentBox.setAlignment(Pos.CENTER);
        mainPane.setCenter(alignmentBox);
        Button menuButton = new Button(properties.loadDialog("menu"));
        VBox menuButtonBox = new VBox(menuButton);
        menuButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        menuButtonBox.setPadding(new Insets(properties.loadSize("medium-indent")));
        mainPane.setBottom(menuButtonBox);

        StageHolder.stage.setScene(scene);

        menuButton.setOnAction(this::menuButtonPressed);
    }

    private void menuButtonPressed(ActionEvent actionEvent) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.main();
    }
}
