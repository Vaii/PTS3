package phub.Whiteboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vai on 4/2/17.
 */
public class WhiteboardController implements Initializable {

    private Whiteboard whiteboard;
    private GraphicsContext gc;
    private JavaFXPaintable paintable;

    public Stage getPictureStage() {
        return pictureStage;
    }

    public void setPictureStage(Stage pictureStage) {
        this.pictureStage = pictureStage;
    }

    private Stage pictureStage;

    public Whiteboard getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(Whiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }

    private ObservableList<String> whiteboarditemList = FXCollections.observableArrayList();
    @FXML
    Button saveButton;
    @FXML
    Button newButton;
    @FXML
    Button browseButton;
    @FXML
    Canvas whiteboardCanvas;
    @FXML
    ComboBox whiteboardItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        whiteboarditemList.add("Text");
        whiteboarditemList.add("Picture");
        whiteboarditemList.add("Video");
        whiteboardItems.setItems(whiteboarditemList);
        gc = whiteboardCanvas.getGraphicsContext2D();
        paintable = new JavaFXPaintable(gc);
        whiteboard = new Whiteboard();

    }

    @FXML
    private void handleComboboxAction(){
        if(whiteboardItems.getSelectionModel().getSelectedItem().equals("Text")){
            try {
                Stage textStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("Text.fxml"));
                textStage.setTitle("Text input");
                textStage.setScene(new Scene(root));
                textStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(whiteboardItems.getSelectionModel().getSelectedItem().equals("Picture")){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select picture");
            FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter(
                    "Pictures", "*.jpg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(extensions);
            Picture p = new Picture();
            p.setFile(fileChooser.showOpenDialog(pictureStage));

        }
    }

    private void draw(){
        gc.clearRect(0,0,whiteboardCanvas.getWidth(), whiteboardCanvas.getHeight());
    }
}
