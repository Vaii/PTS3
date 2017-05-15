package chat;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sun.plugin.javascript.navig.Anchor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by bob on 7-5-17.
 */
public class chatPanelController implements Initializable {

    @FXML
    private AnchorPane chatPanel;
    @FXML
    private Label chatEmoticonIcon;

    @FXML
    private void OpenEmojiWindow(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Text chatEmoticons = GlyphsDude.createIcon(FontAwesomeIcon.SMILE_ALT, "1.2em");
        chatEmoticons.setFill(Color.WHITE);
        this.chatEmoticonIcon.setGraphic(chatEmoticons);
    }

}
