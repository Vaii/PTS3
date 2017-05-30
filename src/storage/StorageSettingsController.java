package storage;

import com.dropbox.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by wei-qiang on 29-May-17.
 */
public class StorageSettingsController {
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
    }

    @FXML
    private void handleButtonActionOntkoppel(ActionEvent event){
        storage.ontkoppel();
    }
}
