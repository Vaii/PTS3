package whiteboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vai on 5/22/17.
 */
public class SaveController implements Initializable {

    @FXML
    TextField wbName;
    @FXML
    Button setName;

    WhiteboardController wc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setName.setOnAction(this::saveName);
    }

    private void saveName(ActionEvent actionEvent) {
        if(!wbName.getText().isEmpty()){
            wc.setWhiteboardName(wbName.getText());
            Stage currentStage = (Stage) setName.getScene().getWindow();
            currentStage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Er ging iets mis");
            alert.setHeaderText("Er is geen naam ingevuld");
            alert.setContentText("Vul een naam in en probeer opnieuw");
            alert.showAndWait();

        }
    }

    public void setWc(WhiteboardController wc) {
        this.wc = wc;
    }
}
