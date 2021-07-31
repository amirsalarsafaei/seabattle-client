package apps.auth.views;
import Holder.StageHolder;
import Properties.properties;
import Utils.PageInterface;
import assets.ImageHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class menu implements PageInterface {
    public void main() {
        BorderPane mainPane = new BorderPane();
        ImageView registerButton = ImageHandler.getImage("register-button");
        ImageView signInButton = ImageHandler.getImage("signIn-button");
        HBox menuHolder = new HBox(properties.loadSize("huge-indent"), registerButton, signInButton);
        mainPane.setCenter(menuHolder);
        menuHolder.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        StageHolder.stage.setScene(scene);
        registerButton.setOnMouseClicked(this::registerButtonClicked);
        signInButton.setOnMouseClicked(this::signInButtonClicked);
    }

    private void signInButtonClicked(MouseEvent mouseEvent) {
        Login login = new Login();
        login.main();
    }

    private void registerButtonClicked(MouseEvent mouseEvent) {
        register register1 = new register();
        register1.main();
    }
}
