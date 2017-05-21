package authentication;

import authentication.repository.LoginMongoContext;
import authentication.repository.LoginRepository;
import domain.Crypt;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.jvm.hotspot.asm.Register;
import sun.security.util.Password;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button login;
    @FXML
    TextField userName;
    @FXML
    PasswordField userPassword;
    @FXML
    Label registerAccount;

    RegisterController rc;

    LoginRepository loginRepo;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setOnAction(this::tryAuthentication);
        registerAccount.setOnMouseClicked(this::registerAccountScreen);
        loginRepo = new LoginRepository(new LoginMongoContext());
    }

    private void registerAccountScreen(MouseEvent mouseEvent) {

        try {
            Stage registerStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            rc = loader.getController();
            rc.setLoginRepo(loginRepo);
            registerStage.setTitle("Registreren");
            registerStage.setScene(new Scene(root));
            registerStage.showAndWait();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void tryAuthentication(ActionEvent actionEvent) {

        String encrypted = Crypt.hashPassword(userPassword.getText());
        User user = loginRepo.loginuser(userName.getText(), encrypted);

        if(user != null){
            System.out.println("it worked");
        }
        else{
            System.out.println("it didnt");
        }
    }

}
