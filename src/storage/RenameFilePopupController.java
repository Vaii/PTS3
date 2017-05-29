package storage;

import com.dropbox.core.DbxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by wei-qiang on 27-May-17.
 */
public class RenameFilePopupController implements Initializable {
    public StorageController parent;
    private Stage stage;
    public Folder folder;
    public boolean createFolder;
    public  StorageDropbox storage;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField tbName;

    public RenameFilePopupController(){
    }
    public RenameFilePopupController(StorageDropbox storage){
        this.storage = storage;
        createFolder = true;
    }
    public RenameFilePopupController(Folder renameFolder, StorageDropbox storage){
        folder = renameFolder;
        this.storage = storage;
        createFolder = false;
    }

    @FXML
    private void handleButtonActionCancel(ActionEvent event){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonActionSubmit(ActionEvent event){
        if(createFolder == true){
            try {
                storage.addNewfolder(tbName.getText());
            } catch (DbxException e) {
                e.printStackTrace();
            }
        }
        else {
            storage.moveFile(folder.getFile().path, folder.getFile().path.substring(0, folder.getFile().path.lastIndexOf("/")) + "/" + tbName.getText());
        }
        try {
            parent.loadDirectory(storage.getCurrentDirectory());
        } catch (DbxException e) {
            e.printStackTrace();
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
