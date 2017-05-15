package chat;

import com.vdurmont.emoji.EmojiManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by bob on 14-5-17.
 */
public class EmojiPanelController implements Initializable{

    @FXML
    private Button btnSmile;
    @FXML
    private AnchorPane emojiAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSmile.setText(EmojiManager.getForAlias("wink").getUnicode());
    }
}
