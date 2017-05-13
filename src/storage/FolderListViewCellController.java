package storage;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by wei-qiang on 13-May-17.
 */
public class FolderListViewCellController extends ListCell<Folder> implements Initializable {
    FXMLLoader mLLoader;
    @FXML
    private CheckBox cbSelect;
    @FXML
    private Label lblName;
    @FXML
    private Label lblModified;
    @FXML
    private Label lblSize;
    @FXML
    private FontAwesomeIconView faiIcon;
    @FXML
    private Button btnOption;
    @FXML
    private GridPane gridPane;

    public FolderListViewCellController(){
    }

    @Override
    protected void updateItem(Folder folder, boolean empty) {
        super.updateItem(folder, empty);

        if(folder == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(this.getClass().getResource("FolderListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            lblName.setText(String.valueOf(folder.getFile().name));

            if(folder.getFile().isFile()){
                lblModified.setText(String.valueOf(folder.getFile().asFile().lastModified));
                lblSize.setText(folder.getFile().asFile().humanSize);
            }
            else {
                lblModified.setText("--");
                lblSize.setText("--");
                faiIcon.setIcon(FontAwesomeIcon.FOLDER);
            }
            setText(null);
            setGraphic(this.gridPane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
