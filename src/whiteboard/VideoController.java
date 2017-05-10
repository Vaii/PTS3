package whiteboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vai on 4/10/17.
 */
public class VideoController implements Initializable {

    @FXML
    Button btnSubmit;
    @FXML
    TextField videoUrl;

    public Stage getVideoStage() {
        return videoStage;
    }

    public void setVideoStage(Stage videoStage) {
        this.videoStage = videoStage;
    }

    private Stage videoStage;

    public WhiteboardController getWhiteboardController() {
        return whiteboardController;
    }

    public void setWhiteboardController(WhiteboardController whiteboardController) {
        this.whiteboardController = whiteboardController;
    }

    private  WhiteboardController whiteboardController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSubmit.setOnAction(this::submitUrl);
    }

    private void submitUrl(ActionEvent actionEvent) {
        if(!videoUrl.getText().isEmpty()){
            whiteboardController.setVideoUrl(videoUrl.getText());
        }

        videoStage.close();
    }
}
