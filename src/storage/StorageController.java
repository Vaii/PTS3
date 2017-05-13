package storage;

import com.dropbox.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

/**
 * Created by wei-qiang on 29-Apr-17.
 */
public class StorageController {

    @FXML
    private Label label;

    @FXML
    private Button authorizebutton;

    @FXML
    private Button sendbutton;
    @FXML
    private Button btnDownload;
    @FXML
    private Button btnUpload;
    @FXML
    private Button btnBack;

    @FXML
    private TextField inputtxt;

    @FXML
    private TextArea outputtxt;

    @FXML
    private ListView<Folder> fileList;

    private StorageDropbox storage;

    private DbxWebAuthNoRedirect webAuth;
    private DbxRequestConfig config;

    private String selectedFolder;
    private Stage stage;

    @FXML
    private void handleButtonActionAuthenticate(ActionEvent event) throws URISyntaxException, IOException {
        final String APP_KEY = "5f5yop2b2qwidq9";
        final String APP_SECRET = "6njyd71d4ncif1d";
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        String authorizeUrl = webAuth.start();
        Desktop d = Desktop.getDesktop();
        d.browse(new URI(authorizeUrl));
    }

    @FXML
    private void handleButtonActionSendAuthenticate(ActionEvent event) throws DbxException {
        DbxAuthFinish authFinish = webAuth.finish(inputtxt.getText());
        String accessToken = authFinish.accessToken;
        DbxClient client = new DbxClient(config, accessToken);
        storage = new StorageDropbox(client);
        //outputtxt.setText("Linked account: " + client.getAccountInfo().displayName);
        loadFiles(storage.currentDirectory);
    }

    @FXML
    private void folderDoubleClick(MouseEvent event) throws DbxException {
        if (selectedFolder == fileList.getSelectionModel().getSelectedItem().getFile().name && fileList.getSelectionModel().getSelectedItem().getFile().isFolder()) {
            loadFiles(fileList.getSelectionModel().getSelectedItem().getFile().path);
            storage.currentDirectory = fileList.getItems().get(0).getFile().path;
        }
        selectedFolder = fileList.getSelectionModel().getSelectedItem().getFile().name;
    }

    @FXML
    private void downloadFile(ActionEvent event) throws FileNotFoundException, DbxException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(fileList.getSelectionModel().getSelectedItem().getFile().name);
        storage.downloadFile(fileChooser.showSaveDialog(stage).getPath(), fileList.getSelectionModel().getSelectedItem().getFile().path);
    }

    @FXML
    private void uploadFile(ActionEvent event) throws FileNotFoundException, DbxException, IOException {
        FileChooser fileChooser = new FileChooser();
        File inputFile = fileChooser.showOpenDialog(stage).getAbsoluteFile();
        storage.uploadFile(inputFile);
    }

    @FXML
    private void backDirectory(ActionEvent event) throws DbxException {
        String directory = storage.currentDirectory.substring(0, storage.currentDirectory.lastIndexOf("/"));
        String parent = directory.substring(0, directory.lastIndexOf("/"));
        if (!parent.contains("/")) {
            parent = "/";
        }
        loadFiles(parent);
        storage.currentDirectory = parent;
    }

    public void loadFiles(String path) throws DbxException {
        fileList.getItems().clear();
        fileList.getItems().addAll(storage.getFiles(path));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}