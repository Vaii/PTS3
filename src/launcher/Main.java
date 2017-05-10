package launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("launcher.fxml"));
        primaryStage.setTitle("ProftaakHub");
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root, 1280.0D, 720.0D);
        primaryStage.setMinHeight(720.0D);
        primaryStage.setMinWidth(1280.0D);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
