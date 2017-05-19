package git;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ken on 15-5-2017.
 */
public class GitRepoListViewCellController extends ListCell<GitRepository> implements Initializable {
    FXMLLoader mLLoader;
    @FXML private GridPane gridPane;
    @FXML private Text lblLang;
    @FXML private Text lblRepository;
    @FXML private Text lblDate;
    @FXML private FontAwesomeIconView fontIcon;

    @Override
    protected void updateItem(GitRepository gitRepository, boolean empty){
        super.updateItem(gitRepository,empty);

        if(gitRepository.repository == null){
            setText(null);
            setGraphic(null);
        }
        else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("GitHubListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (gitRepository.repository != null){
            lblRepository.setText(gitRepository.repository.getName());
            if (gitRepository.repository.getLanguage() != null){
                lblLang.setText(gitRepository.repository.getLanguage());
            }
            if (gitRepository.repository.isPrivate()){
                fontIcon.setIcon(FontAwesomeIcon.LOCK);
            }
            else if (!gitRepository.repository.isPrivate()){
                fontIcon.setIcon(FontAwesomeIcon.UNLOCK);
            }
            else{
                fontIcon.setText("-");
                return;
            }
        }
        else {
            lblDate.setText("-");
            lblLang.setText("-");
            lblRepository.setText("-");
        }

        setText(null);
        setGraphic(this.gridPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
