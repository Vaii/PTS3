package whiteboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vai on 4/3/17.
 */
public class TextController implements Initializable {

    @FXML
    Button placeButton;
    @FXML
    TextArea userText;
    @FXML
    ComboBox availableFonts;


    public Stage getTextStage() {
        return textStage;
    }

    public void setTextStage(Stage textStage) {
        this.textStage = textStage;
    }

    private Stage textStage;

    public WhiteboardController getMainController() {
        return mainController;
    }

    public void setMainController(WhiteboardController mainController) {
        this.mainController = mainController;
    }

    private WhiteboardController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placeButton.setOnAction(this::setPaintedText);
        ObservableList<String> fonts = FXCollections.observableArrayList();
        fonts.addAll(Font.getFamilies());
        availableFonts.setItems(fonts);
    }

    private void setPaintedText(ActionEvent actionEvent) {


        if(!userText.getText().isEmpty()){

            if(availableFonts.getSelectionModel().getSelectedItem() != null){
                Text t = new Text(userText.getText(), availableFonts.getSelectionModel().getSelectedItem().toString());
                mainController.setUserInput(t);
                textStage.close();
            }
            else{
                Text t = new Text(userText.getText());
                mainController.setUserInput(t);
                textStage.close();
            }
        }
        else{
            textStage.close();
        }

    }




}
