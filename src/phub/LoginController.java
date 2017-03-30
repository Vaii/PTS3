package phub;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Button login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setOnAction(this::tryAuthentication);
    }

    private void tryAuthentication(ActionEvent actionEvent) {
    }
}
