package chat;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by bob on 7-5-17.
 */
public class ChatPanelController implements Initializable {

    @FXML
    private AnchorPane chatPanel;
    @FXML
    private Label chatEmoticonIcon;
    @FXML
    private VBox vboxMessages;
    @FXML
    private TextArea inputMessage;

    private Boolean emojiPanelIsOpen;
    private AnchorPane emojiPane;

    @FXML
    private void OpenEmojiWindow() throws IOException {


        if(!emojiPanelIsOpen){
            emojiPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("EmojiPanel.fxml"));
            AnchorPane.setBottomAnchor(emojiPane, 29.0);
            AnchorPane.setRightAnchor(emojiPane, 1.0);
            AnchorPane.setLeftAnchor(emojiPane, 1.0);
            chatPanel.getChildren().add(emojiPane);
            emojiPanelIsOpen = true;
        }
        else{
            chatPanel.getChildren().remove(emojiPane);
            emojiPanelIsOpen = false;
        }

    }

    private void AddMessage(Message message){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
        // Create a controller instance
        MessageController controller = new MessageController(message);
        // Set it in the FXMLLoader
        loader.setController(controller);
        try {
            AnchorPane ms = loader.load();
            vboxMessages.getChildren().add(ms);
            System.out.println("new message added");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void NewMessage(){
        System.out.println("enter pressed");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emojiPanelIsOpen = false;
        Text chatEmoticons = GlyphsDude.createIcon(FontAwesomeIcon.SMILE_ALT, "1.2em");
        chatEmoticons.setFill(Color.WHITE);
        this.chatEmoticonIcon.setGraphic(chatEmoticons);

        inputMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = inputMessage.getText();

                    // do your thing...
                    AddMessage(new Message(inputMessage.getText(), new Date()));
                    // clear text
                    inputMessage.setText("");
                    keyEvent.consume();
                    inputMessage.requestFocus();
                }
            }
        });

    }

}
