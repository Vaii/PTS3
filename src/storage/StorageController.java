package storage;

import com.dropbox.core.*;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v2.DbxClientV2;
import domain.Config;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by wei-qiang on 29-Apr-17.
 */
public class StorageController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Label lblDirectory;
    @FXML
    private Button btnRename;
    @FXML
    private Button btnDownload;
    @FXML
    private Button btnNewFolder;
    @FXML
    private Button btnUpload;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private ListView<Folder> fileList;

    private ObservableList<Folder> fileObservableList;
    private StorageDropbox storage;
    private DbxRequestConfig config;
    private String selectedFolder;
    private Stage stage;
    private DbxClientV1 client;
    private DbxClientV2 client2;

    public StorageController() {
        storage = new StorageDropbox();
        fileObservableList = FXCollections.observableArrayList();
    }
    
    @FXML
    private void folderDoubleClick(MouseEvent event) throws DbxException {
        if (fileList.getSelectionModel().getSelectedItems().size() > 1) {
            btnRename.setDisable(true);
            btnRename.setOpacity(0);
            btnDelete.setDisable(false);
            btnDelete.setOpacity(1);
            btnDownload.setDisable(false);
            btnDownload.setOpacity(1);
        } else if (fileList.getSelectionModel().getSelectedItems().size() == 1) {
            btnRename.setDisable(false);
            btnRename.setOpacity(1);
            btnDelete.setDisable(false);
            btnDelete.setOpacity(1);
            btnDownload.setDisable(false);
            btnDownload.setOpacity(1);
        }

        if (selectedFolder == fileList.getSelectionModel().getSelectedItem().getFile().name && fileList.getSelectionModel().getSelectedItem().getFile().isFolder()) {
            loadDirectory(fileList.getSelectionModel().getSelectedItem().getFile().path);
            storage.setCurrentDirectory(fileList.getItems().get(0).getFile().path);
        }
        selectedFolder = fileList.getSelectionModel().getSelectedItem().getFile().name;

    }

    @FXML
    private void handleButtonActionDownloadFile(ActionEvent event) throws FileNotFoundException, DbxException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(fileList.getSelectionModel().getSelectedItem().getFile().name);
        storage.downloadFile(fileChooser.showSaveDialog(stage).getPath(), fileList.getSelectionModel().getSelectedItem().getFile().path);
    }

    @FXML
    private void handleButtonActionRename(ActionEvent event) {
        namePopup(false, fileList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleButtonActionUploadFile(ActionEvent event) throws FileNotFoundException, DbxException, IOException {
        FileChooser fileChooser = new FileChooser();
        File inputFile = fileChooser.showOpenDialog(stage).getAbsoluteFile();
        storage.uploadFile(inputFile);
    }

    @FXML
    private void backDirectory(ActionEvent event) throws DbxException {
        String directory = storage.getCurrentDirectory();
        String parent = directory.substring(0, directory.lastIndexOf('/'));
        if (!parent.contains("/")) {
            parent = "/";
        }
        loadDirectory(parent);
        storage.setCurrentDirectory(parent);
    }

    @FXML
    private void handleButtonActionDelete(ActionEvent event) throws DbxException {
        ObservableList<Folder> selected = fileList.getSelectionModel().getSelectedItems();
        for (Folder f : selected) {
            storage.deleteFile(f.getFile().path);
        }
        loadDirectory(storage.getCurrentDirectory());
    }

    @FXML
    private void addNewfolder(ActionEvent event) throws DbxException {
        namePopup(true, null);
    }


    public void loadDirectory(String path) throws DbxException {
        fileObservableList.clear();
        fileObservableList.addAll(storage.getFiles(path));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblDirectory.setText(path);
            }
        });

    }

    public void namePopup(boolean createFolder, Folder folder) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RenameFilePopup.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Rename");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            RenameFilePopupController controller = loader.getController();
            controller.setStorage(storage);
            controller.setCreateFolder(createFolder);
            if (folder != null) {
                controller.setFolder(folder);
            }
            controller.setParent(this);
            controller.setStage(stage);
            stage.show();
            loadDirectory(storage.getCurrentDirectory());
        } catch (Exception e) {
            Logger.getLogger(StorageController.class.getName()).log(Level.SEVERE, null, e);
        }


    }

    public void initialize(URL url, ResourceBundle rb) {
        if (Config.getUser().getDropboxAuthToken() != "") {
            config = new DbxRequestConfig("Phub/1.0", Locale.getDefault().toString());
            client = new DbxClientV1(config, Config.getUser().getDropboxAuthToken());
            client2 = new DbxClientV2(config, Config.getUser().getDropboxAuthToken(), DbxHost.DEFAULT);
            storage = new StorageDropbox(client, client2);
            try {
                loadDirectory(storage.getCurrentDirectory());
            } catch (Exception e) {
                Logger.getLogger(StorageController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dropbox Error");
            alert.setHeaderText(null);
            alert.setContentText("Koppel uw dropbox account in de settings");
            alert.showAndWait();
            System.out.println("koppel dropbox account!");
            return;
        }

        this.fileList.setItems(fileObservableList);
        this.fileList.setCellFactory((FolderListView) -> {
            return new FolderListViewCellController();
        });
        this.fileList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btnRename.setDisable(true);
        btnRename.setOpacity(0);
        btnDelete.setDisable(true);
        btnDelete.setOpacity(0);
        btnDownload.setDisable(true);
        btnDownload.setOpacity(0);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}