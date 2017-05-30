package storage;

import com.dropbox.core.*;
import domain.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by wei-qiang on 29-May-17.
 */
public class StorageSettingsController implements Initializable{
    private StorageDropbox storage = new StorageDropbox();
    @FXML
    private Button btnAuthorize;
    @FXML
    private Button btnSendToken;
    @FXML
    private Button btnOntkoppel;
    @FXML
    private TextField tbToken;

    @FXML
    private void handleButtonActionAuthorize(ActionEvent event) throws URISyntaxException, IOException {
        storage.authorize();
    }

    @FXML
    private void handleButtonActionSendAuthenticate(ActionEvent event) throws DbxException {
        storage.authenticate(tbToken.getText());
        btnAuthorize.setDisable(true);
        btnSendToken.setDisable(true);
        tbToken.setText("Gekoppeld");
        tbToken.setDisable(true);
        btnOntkoppel.setDisable(false);
    }

    @FXML
    private void handleButtonActionOntkoppel(ActionEvent event){
        storage.ontkoppel();
        btnAuthorize.setDisable(false);
        btnSendToken.setDisable(false);
        tbToken.setText("");
        tbToken.setDisable(false);
        btnOntkoppel.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Config.getUser().getDropboxAuthToken() == null || Config.getUser().getDropboxAuthToken() == ""){
            btnOntkoppel.setDisable(true);
        }
        else{
            btnAuthorize.setDisable(true);
            btnSendToken.setDisable(true);
            tbToken.setText("Gekoppeld");
            tbToken.setDisable(true);
        }
    }
}
