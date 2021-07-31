package apps.game.views;

import Holder.StageHolder;
import Holder.TimeHolder;
import Holder.UsernameHolder;
import Models.Game.*;
import Properties.properties;
import Utils.PageInterface;
import apps.game.controller.GamePanelController;
import apps.live.views.LiveGameView;
import assets.ImageHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GamePanel {
    public Player player;
    public GamePanelController gamePanelController;
    public Label timeLabel;
    public void main(Player player) {
        this.player = player;
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(ImageHandler.getImage("game-panel"));

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);

        GridChoice gridChoice = player.gridChoice;
        Pane ShipPane = new Pane();
        for (Ship ship: player.shipLayout.ships) {
            int x = ship.ship.get(0).x, y = ship.ship.get(0).y;
            int gamePanelX = GridToGamePanelGetX(x, gridChoice);
            int gamePanelY = GridToGamePanelGetY(y, gridChoice);
            LiveGameView.MakeShips(ShipPane, ship, x, gamePanelX, gamePanelY);
        }

        for (Ship ship: player.BrokeShips) {
            int x = ship.ship.get(0).x, y = ship.ship.get(0).y;
            int gamePanelX = GridToGamePanelGetX(x, OppositeGridChoice(gridChoice));
            int gamePanelY = GridToGamePanelGetY(y, OppositeGridChoice(gridChoice));
            LiveGameView.MakeShips(ShipPane, ship, x, gamePanelX, gamePanelY);
        }
        mainPane.getChildren().add(ShipPane);

        if (player.gamePhase == GamePhase.Arranging) {
            BorderPane timeAndReArrange = new BorderPane();
            mainPane.getChildren().add(timeAndReArrange);
            VBox timeBox = new VBox(), reArrangeBox = new VBox(properties.loadSize("medium-indent"));
            timeAndReArrange.setTop(timeBox);
            timeAndReArrange.setBottom(reArrangeBox);
            Label reArrangeLabel = new Label(player.timesReArranged + "/3");
            Button reArrange = new Button(properties.loadDialog("reArrange"));
            Button Ready = new Button(properties.loadDialog("ready"));
            reArrangeBox.setAlignment(Pos.BOTTOM_CENTER);
            reArrangeBox.getChildren().add(reArrangeLabel);
            reArrangeBox.getChildren().add(reArrange);
            reArrangeBox.getChildren().add(Ready);
            timeLabel.setText(String.valueOf(TimeHolder.ArrangementTimeLeft));
            timeBox.setAlignment(Pos.TOP_CENTER);
            timeBox.getChildren().add(timeLabel);
            reArrange.setOnAction(this::reArrangeButtonPressed);
            Ready.setOnAction(this::ReadyButtonPressed);
        }
        Pane ShotPane = new Pane();

        if (player.gamePhase == GamePhase.Playing || player.gamePhase == GamePhase.Waiting) {
            for (Coordinate coordinate : player.landedShots) {
                int x = coordinate.x, y = coordinate.y;
                int gamePanelX = GridToGamePanelGetX(x, OppositeGridChoice(gridChoice));
                int gamePanelY = GridToGamePanelGetY(y, OppositeGridChoice(gridChoice));
                ImageView ShotPic = ImageHandler.getImage("landed-shot");
                ShotPic.setLayoutX(gamePanelX);
                ShotPic.setLayoutY(gamePanelY);
                ShotPane.getChildren().add(ShotPic);
            }
            for (Coordinate coordinate : player.missedShots) {
                int x = coordinate.x, y = coordinate.y;
                int gamePanelX = GridToGamePanelGetX(x, OppositeGridChoice(gridChoice));
                int gamePanelY = GridToGamePanelGetY(y, OppositeGridChoice(gridChoice));
                ImageView ShotPic = ImageHandler.getImage("missed-shot");
                ShotPic.setLayoutX(gamePanelX);
                ShotPic.setLayoutY(gamePanelY);
                ShotPane.getChildren().add(ShotPic);
            }

            for (Coordinate coordinate : player.enemyLandedShots) {
                int x = coordinate.x, y = coordinate.y;
                int gamePanelX = GridToGamePanelGetX(x, gridChoice);
                int gamePanelY = GridToGamePanelGetY(y, gridChoice);
                ImageView ShotPic = ImageHandler.getImage("landed-shot");
                ShotPic.setLayoutX(gamePanelX);
                ShotPic.setLayoutY(gamePanelY);
                ShotPane.getChildren().add(ShotPic);
            }
            for (Coordinate coordinate : player.enemyMissedShots) {
                int x = coordinate.x, y = coordinate.y;
                int gamePanelX = GridToGamePanelGetX(x, gridChoice);
                int gamePanelY = GridToGamePanelGetY(y, gridChoice);
                ImageView ShotPic = ImageHandler.getImage("missed-shot");
                ShotPic.setLayoutX(gamePanelX);
                ShotPic.setLayoutY(gamePanelY);
                ShotPane.getChildren().add(ShotPic);
            }
            mainPane.getChildren().add(ShotPane);
            BorderPane timeAndReArrange = new BorderPane();
            mainPane.getChildren().add(timeAndReArrange);
            VBox timeBox = new VBox(), reArrangeBox = new VBox();
            timeAndReArrange.setTop(timeBox);
            timeBox.setAlignment(Pos.TOP_CENTER);
            timeBox.getChildren().add(timeLabel);
            timeLabel.setPadding(new Insets(properties.loadSize("big-indent")));
        }


        mainPane.setOnMouseClicked(this::gamePanelClicked);
    }

    private void ReadyButtonPressed(ActionEvent actionEvent) {
        gamePanelController.sendReadyRequest(player);
    }

    private void reArrangeButtonPressed(ActionEvent actionEvent) {
        gamePanelController.reArrange(player);
    }

    private void gamePanelClicked(MouseEvent mouseEvent) {
        gamePanelController.GamePanelClicked((int)mouseEvent.getX(), (int)mouseEvent.getY(), player);
    }

    private int GridToGamePanelGetX(int x, GridChoice gridChoice) {
        return x * properties.loadSize("grid-length") + properties.loadSize(gridChoice + "-grid-startX");
    }

    private int GridToGamePanelGetY(int y, GridChoice gridChoice) {
        return y * properties.loadSize("grid-length") + properties.loadSize(gridChoice + "-grid-startY");
    }

    private GridChoice OppositeGridChoice(GridChoice gridChoice) {
        if (gridChoice == GridChoice.left)
            return GridChoice.right;
        return GridChoice.left;
    }

}
