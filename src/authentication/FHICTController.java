package authentication;

import authentication.repository.LoginMongoContext;
import authentication.repository.LoginRepository;
import domain.Config;
import domain.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vai on 5/25/17.
 */
public class FHICTController implements Initializable {

    private static final String AUTHLOCATION = "https://identity.fhict.nl/connect/authorize?client_id=i354549-p-hubbyomm" +
            "&scope=fhict%20fhict_personal&response_type=token" +
            "&redirect_uri=http://localhost:44300/&state=phub";

    LoginServer server;

    @FXML
    WebView auth;

    private LoginRepository lRepo;

    private Stage thisStage;
    private Stage loginStage;

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            this.lRepo = new LoginRepository(new LoginMongoContext());
            server = new LoginServer(new LoginRepository(new LoginMongoContext()), auth, this);
            auth.getEngine().load(AUTHLOCATION);
        } catch (IOException e) {
            Logger.getLogger(FHICTController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void succesfullToken() {
        User user = lRepo.getAuthorizedUser(Config.getAccesToken());
        if (user != null) {
            Config.setUser(lRepo.loginuser(user));
            if (Config.getUser() != null) {
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Stage launcher = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/launcher/launcher.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                Logger.getLogger(FHICTController.class.getName()).log(Level.SEVERE, null, e);
                            }
                            launcher.setTitle("ProftaakHub");
                            launcher.initStyle(StageStyle.DECORATED);
                            Scene scene = new Scene(root, 1280.0D, 720.0D);
                            launcher.setMinHeight(720.0D);
                            launcher.setMinWidth(1280.0D);
                            launcher.setScene(scene);
                            launcher.show();

                            thisStage.close();
                            loginStage.close();
                        }
                    });
                } catch (Exception e) {
                    e.toString();
                    Logger.getLogger(FHICTController.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        thisStage.close();
                    }
                });
            }
        }
    }
}
