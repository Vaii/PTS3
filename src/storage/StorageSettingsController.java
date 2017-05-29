package storage;

import com.dropbox.core.*;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v2.DbxClientV2;
import domain.Config;
import domain.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jongo.MongoCollection;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

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
