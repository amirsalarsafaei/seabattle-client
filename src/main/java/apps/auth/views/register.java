package apps.auth.views;

import Holder.StageHolder;
import Properties.properties;
import Utils.PageInterface;
import apps.auth.controller.Authenticate;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class register implements PageInterface {
    public Label errorLabel = new Label();
    TextField usernameField;
    PasswordField passwordField, password2Field;
    public void main() {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        VBox mainBox = new VBox(properties.loadSize("medium-indent"));
        mainPane.setCenter(mainBox);
        mainBox.setAlignment(Pos.CENTER);

        errorLabel.setId("error-label");

        Label usernameLabel = new Label(properties.loadDialog("username"));
        usernameField = new TextField();
        HBox usernameBox = new HBox(properties.loadSize("medium-indent"), usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label(properties.loadDialog("password"));
        passwordField = new PasswordField();
        HBox passwordBox = new HBox(properties.loadSize("medium-indent"), passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        Label password2Label = new Label(properties.loadDialog("password2"));
        password2Field = new PasswordField();
        HBox password2Box = new HBox(properties.loadSize("medium-indent"), password2Label, password2Field);
        password2Box.setAlignment(Pos.CENTER);

        Button Register = new Button(properties.loadDialog("register"));

        mainBox.getChildren().add(usernameBox);
        mainBox.getChildren().add(passwordBox);
        mainBox.getChildren().add(password2Box);
        mainBox.getChildren().add(errorLabel);
        mainBox.getChildren().add(Register);
        StageHolder.stage.setScene(scene);

        Register.setOnAction(this::registerButtonPressed);
    }

    private void registerButtonPressed(ActionEvent actionEvent) {
        Authenticate authenticate = new Authenticate();
        authenticate.signUp(usernameField.getText(), passwordField.getText(), password2Field.getText(), this);
    }


}
