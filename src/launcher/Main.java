package launcher;

import chat.Message;
import chat.repository.ChatMongoContext;
import chat.repository.ChatRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Date;

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
        ChatRepository repo = new ChatRepository(new ChatMongoContext());
        repo.Insert(new Message("Hallo - Dit is een Test.", new Date()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
