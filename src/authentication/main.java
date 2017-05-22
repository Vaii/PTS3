package authentication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Vai on 5/21/17.
 */


public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage Stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loader.load();
        Stage.setTitle("Inloggen");
        Stage.setScene(new Scene(root));
        Stage.show();
    }
}
