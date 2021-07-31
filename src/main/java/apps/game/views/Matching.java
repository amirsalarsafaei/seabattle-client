package apps.game.views;

import Holder.StageHolder;
import Properties.properties;
import Utils.PageInterface;
import apps.game.controller.MatchingController;
import assets.ImageHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static java.lang.Thread.sleep;

public class Matching implements PageInterface {
    public void main() {
        BorderPane mainPane = new BorderPane();

        VBox mainBox = new VBox(properties.loadSize("medium-indent"));
        mainBox.setAlignment(Pos.CENTER);
        mainPane.setCenter(mainBox);

        mainBox.getChildren().add(ImageHandler.getImage("loading"));
        mainBox.getChildren().add(new Label(properties.loadDialog("matchmaking")));
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);
        StageHolder.stage.show();
        MatchingController matchingController = new MatchingController();
        matchingController.main();
    }
}
