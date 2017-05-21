package authentication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button login;
    @FXML
    TextField userName;
    @FXML
    TextField passwordField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setOnAction(this::tryAuthentication);
    }

    private void tryAuthentication(ActionEvent actionEvent) {
        String username = userName.getText();
        String password = passwordField.getText();
    }
}
