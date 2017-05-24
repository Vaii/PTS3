package authentication;

import authentication.repository.LoginMongoContext;
import authentication.repository.LoginRepository;
import domain.Config;
import domain.Crypt;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

            Config.setUser(user);

            try {
                Stage stage = (Stage)login.getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/launcher/launcher.fxml"));
                primaryStage.setTitle("ProftaakHub");
                primaryStage.initStyle(StageStyle.DECORATED);
                Scene scene = new Scene(root, 1280.0D, 720.0D);
                primaryStage.setMinHeight(720.0D);
                primaryStage.setMinWidth(1280.0D);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inlog gegevens onjuist");
            alert.setHeaderText("Het ingevoerde wachtwoord of username is onjuist");
            alert.setContentText("Vul de gegevens opnieuw en en probeer opnieuw");
            alert.showAndWait();
        }
    }

}
