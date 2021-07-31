package apps.live.views;

import Holder.StageHolder;
import Models.Game.*;
import Models.Live.LiveGame;
import Properties.properties;
import apps.live.controllers.LiveGameController;
import assets.ImageHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LiveGameView {
    LiveGameController liveGameController;
    public LiveGameView(LiveGameController liveGameController) {
        this.liveGameController = liveGameController;
    }
    public void main(LiveGame liveGame) {
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(ImageHandler.getImage("game-panel"));

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);

        Pane ShotPane = new Pane();
        for (Coordinate coordinate : liveGame.rightLandedShots) {
            int x = coordinate.x, y = coordinate.y;
            int gamePanelX = GridToGamePanelGetX(x, GridChoice.right);
            int gamePanelY = GridToGamePanelGetY(y, GridChoice.right);
            ImageView ShotPic = ImageHandler.getImage("landed-shot");
            ShotPic.setLayoutX(gamePanelX);
            ShotPic.setLayoutY(gamePanelY);
            ShotPane.getChildren().add(ShotPic);
        }
        for (Coordinate coordinate : liveGame.leftLandedShots) {
            int x = coordinate.x, y = coordinate.y;
            int gamePanelX = GridToGamePanelGetX(x, GridChoice.left);
            int gamePanelY = GridToGamePanelGetY(y, GridChoice.left);
            ImageView ShotPic = ImageHandler.getImage("landed-shot");
            ShotPic.setLayoutX(gamePanelX);
            ShotPic.setLayoutY(gamePanelY);
            ShotPane.getChildren().add(ShotPic);
        }

        for (Coordinate coordinate : liveGame.rightMissedShots) {
            int x = coordinate.x, y = coordinate.y;
            int gamePanelX = GridToGamePanelGetX(x, GridChoice.right);
            int gamePanelY = GridToGamePanelGetY(y, GridChoice.right);
            ImageView ShotPic = ImageHandler.getImage("missed-shot");
            ShotPic.setLayoutX(gamePanelX);
            ShotPic.setLayoutY(gamePanelY);
            ShotPane.getChildren().add(ShotPic);
        }
        for (Coordinate coordinate : liveGame.leftMissedShots) {
            int x = coordinate.x, y = coordinate.y;
            int gamePanelX = GridToGamePanelGetX(x, GridChoice.left);
            int gamePanelY = GridToGamePanelGetY(y, GridChoice.left);
            ImageView ShotPic = ImageHandler.getImage("missed-shot");
            ShotPic.setLayoutX(gamePanelX);
            ShotPic.setLayoutY(gamePanelY);
            ShotPane.getChildren().add(ShotPic);
        }

        Pane ShipPane = new Pane();
        for (Ship ship: liveGame.leftDeadShips) {
            int x = ship.ship.get(0).x, y = ship.ship.get(0).y;
            int gamePanelX = GridToGamePanelGetX(x, GridChoice.left);
            int gamePanelY = GridToGamePanelGetY(y, GridChoice.left);
            MakeShips(ShipPane, ship, x, gamePanelX, gamePanelY);
        }
        for (Ship ship: liveGame.rightDeadShips) {
            int x = ship.ship.get(0).x, y = ship.ship.get(0).y;
            int gamePanelX = GridToGamePanelGetX(x, GridChoice.right);
            int gamePanelY = GridToGamePanelGetY(y, GridChoice.right);
            MakeShips(ShipPane, ship, x, gamePanelX, gamePanelY);
        }
        mainPane.getChildren().addAll(ShipPane, ShotPane);

        if (liveGame.gamePhase == GamePhase.Playing || liveGame.gamePhase == GamePhase.Waiting) {
            BorderPane timePane = new BorderPane();
            Label timeLabel = new Label( String.valueOf(LocalDateTime.from(LocalDateTime.now()).until(liveGame.localDateTime,
                    ChronoUnit.SECONDS)));
            VBox timeBox = new VBox(properties.loadSize("medium-indent"),
                    new Label(liveGame.leftPlayer+" vs " + liveGame.rightPlayer), timeLabel);
            timeBox.setAlignment(Pos.TOP_CENTER);
            timePane.setTop(timeBox);
            timeBox.setPadding(new Insets(properties.loadSize("big-indent")));
            mainPane.getChildren().add(timePane);
        }
        else {
            BorderPane timePane = new BorderPane();
            VBox timeBox = new VBox(properties.loadSize("medium-indent"),
                    new Label(liveGame.leftPlayer+" vs " + liveGame.rightPlayer));
            timeBox.setAlignment(Pos.TOP_CENTER);
            timePane.setTop(timeBox);
            timeBox.setPadding(new Insets(properties.loadSize("big-indent")));
            mainPane.getChildren().add(timePane);
        }
        BorderPane menuButtonPane = new BorderPane();
        Button button = new Button(properties.loadDialog("menu"));
        VBox menuButtonBox = new VBox(button);
        menuButtonBox.setPadding(new Insets(properties.loadSize("medium-indent")));
        menuButtonPane.setBottom(menuButtonBox);
        menuButtonBox.setAlignment(Pos.BOTTOM_CENTER);

        StageHolder.stage.setScene(scene);
    }

    public static void MakeShips(Pane shipPane, Ship ship, int x, int gamePanelX, int gamePanelY) {
        Orientation orientation = null;
        if (ship.size == 1) {
            orientation = ship.orientation;
        }
        else if (ship.ship.get(1).x > x)
            orientation = Orientation.Vertical;
        else
            orientation = Orientation.Horizontal;
        ImageView ShipPic = null;
        if (ship.size == 4)
            ShipPic = ImageHandler.getImage("huge-ship-" + orientation);
        if (ship.size == 3)
            ShipPic = ImageHandler.getImage("big-ship-" + orientation);
        if (ship.size == 2)
            ShipPic = ImageHandler.getImage("medium-ship-" + orientation);
        if (ship.size == 1)
            ShipPic = ImageHandler.getImage("small-ship-" + orientation);
        shipPane.getChildren().add(ShipPic);
        ShipPic.setLayoutX(gamePanelX);
        ShipPic.setLayoutY(gamePanelY);
    }

    private int GridToGamePanelGetX(int x, GridChoice gridChoice) {
        return x * properties.loadSize("grid-length") + properties.loadSize(gridChoice + "-grid-startX");
    }

    private int GridToGamePanelGetY(int y, GridChoice gridChoice) {
        return y * properties.loadSize("grid-length") + properties.loadSize(gridChoice + "-grid-startY");
    }
}
