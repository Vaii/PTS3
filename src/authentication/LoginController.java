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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {
    @FXML
    ImageView logo;

    LoginRepository loginRepo;

    private FHICTController fController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logo.setOnMouseClicked(event -> tryAuthentication());
        loginRepo = new LoginRepository(new LoginMongoContext());
        File image = new File("img/Logomakr_88LiG6.png");
        Image img = new Image(image.toURI().toString());
        logo.setImage(img);
    }

    private void tryAuthentication() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fhictAuthentication.fxml"));
            Parent root = loader.load();
            fController = loader.getController();
            fController.setThisStage(stage);
            fController.setLoginStage((Stage) logo.getScene().getWindow());
            stage.setTitle("Fontys Authentication");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
