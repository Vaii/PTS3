package authentication;

import authentication.repository.LoginMongoContext;
import authentication.repository.LoginRepository;
import domain.Crypt;
import domain.User;
import domain.UserType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vai on 5/21/17.
 */
public class RegisterController implements Initializable {

    @FXML
    Button registerButton;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    ComboBox userType;

    public LoginRepository getLoginRepo() {
        return loginRepo;
    }

    public void setLoginRepo(LoginRepository loginRepo) {
        this.loginRepo = loginRepo;
    }

    private LoginRepository loginRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userType.setItems(FXCollections.observableArrayList(UserType.values()));
        registerButton.setOnAction(this::createAccount);
    }

    private void createAccount(ActionEvent actionEvent) {

        if(username.getText() != null && password.getText() != null && userType.getSelectionModel().getSelectedItem() != null){

            String encrypted = Crypt.hashPassword(password.getText());
            User newUser = new User(username.getText(), encrypted , UserType.valueOf(userType.getSelectionModel().getSelectedItem().toString()));

            if(loginRepo.registerUser(newUser)){
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            }
            else{

            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registratie error");
            alert.setHeaderText("Er ging iets mis bij het registreren");
            alert.setContentText("Zorg er voor dat alle velden ingevuld zijn");
            alert.showAndWait();
        }
    }
}
