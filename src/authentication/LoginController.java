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

    LoginRepository loginRepo;



    private FHICTController fController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setOnAction(this::tryAuthentication);
        loginRepo = new LoginRepository(new LoginMongoContext());
    }

    private void tryAuthentication(ActionEvent actionEvent) {

        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fhictAuthentication.fxml"));
            Parent root = loader.load();
            fController = loader.getController();
            fController.setThisStage(stage);
            fController.setLoginStage((Stage)login.getScene().getWindow());
            stage.setTitle("Fontys Authentication");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}
