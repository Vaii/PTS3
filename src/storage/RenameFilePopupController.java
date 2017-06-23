package storage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wei-qiang on 27-May-17.
 */
public class RenameFilePopupController implements Initializable {
    private StorageController parent;
    private Stage stage;
    private Folder folder;
    private boolean createFolder;
    private StorageDropbox storage;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField tbName;

    public RenameFilePopupController() {
    }

    public RenameFilePopupController(StorageDropbox storage) {
        this.storage = storage;
        createFolder = true;
    }

    public RenameFilePopupController(Folder renameFolder, StorageDropbox storage) {
        folder = renameFolder;
        this.storage = storage;
        createFolder = false;
    }

    public StorageController getParent() {
        return parent;
    }

    public void setParent(StorageController parent) {
        this.parent = parent;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public boolean isCreateFolder() {
        return createFolder;
    }

    public void setCreateFolder(boolean createFolder) {
        this.createFolder = createFolder;
    }

    public StorageDropbox getStorage() {
        return storage;
    }

    public void setStorage(StorageDropbox storage) {
        this.storage = storage;
    }

    @FXML
    private void handleButtonActionCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonActionSubmit(ActionEvent event) {
        try {
            if (createFolder) {
                storage.addNewfolder(tbName.getText());
            } else {
                storage.moveFile(folder.getFile().path, folder.getFile().path.substring(0, folder.getFile().path.lastIndexOf("/")) + "/" + tbName.getText());
            }
            parent.loadDirectory(storage.getCurrentDirectory());
        } catch (Exception e) {
            Logger.getLogger(FolderListViewCellController.class.getName()).log(Level.SEVERE, null, e);
        }
        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
