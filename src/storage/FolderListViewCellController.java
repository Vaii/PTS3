package storage;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wei-qiang on 13-May-17.
 */
public class FolderListViewCellController extends ListCell<Folder> implements Initializable {
    FXMLLoader mLLoader;

    @FXML
    private Label lblName;
    @FXML
    private Label lblModified;
    @FXML
    private Label lblSize;
    @FXML
    private FontAwesomeIconView faiIcon;
    @FXML
    private GridPane gridPane;

    public FolderListViewCellController() {
    }

    @Override
    protected void updateItem(Folder folder, boolean empty) {
        super.updateItem(folder, empty);

        if (folder == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(this.getClass().getResource("FolderListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    Logger.getLogger(FolderListViewCellController.class.getName()).log(Level.SEVERE, null, e);
                }
            }

            lblName.setText(String.valueOf(folder.getFile().name));

            if (folder.getFile().isFile()) {
                lblModified.setText(String.valueOf(folder.getFile().asFile().lastModified));
                lblSize.setText(folder.getFile().asFile().humanSize);
                String extension = folder.getFile().path.substring(folder.getFile().path.lastIndexOf('.'));
                switch (extension) {
                    case ".pdf":
                        faiIcon.setIcon(FontAwesomeIcon.FILE_PDF_ALT);
                        break;
                    case ".zip":
                    case ".rar":
                        faiIcon.setIcon(FontAwesomeIcon.FILE_ZIP_ALT);
                        break;
                    case ".txt":
                        faiIcon.setIcon(FontAwesomeIcon.FILE_TEXT_ALT);
                        break;
                    default:
                        faiIcon.setIcon(FontAwesomeIcon.FILE_ALT);
                        break;
                }
            } else {
                lblModified.setText("--");
                lblSize.setText("--");
                faiIcon.setIcon(FontAwesomeIcon.FOLDER_ALT);
            }
            setText(null);
            setGraphic(this.gridPane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
