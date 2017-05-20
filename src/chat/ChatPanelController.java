package chat;


import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionWsaServerTube;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by bob on 7-5-17.
 */
public class ChatPanelController implements Initializable {
    private ObservableList<Message> messages = FXCollections.observableArrayList(new ArrayList<Message>());

    @FXML
    private AnchorPane chatPanel;
    @FXML
    private Label chatEmoticonIcon;
    @FXML
    private VBox vboxMessages;
    @FXML
    private TextArea inputMessage;
    @FXML
    private ScrollPane messagesPane;

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
            slowScrollToBottom(messagesPane);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    static void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }

    private void NewMessage(){
        System.out.println("enter pressed");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messages = ChatConfig.getInstance().getMessagesObservableList();
        emojiPanelIsOpen = false;
        Text chatEmoticons = GlyphsDude.createIcon(FontAwesomeIcon.SMILE_ALT, "1.2em");
        chatEmoticons.setFill(Color.WHITE);
        this.chatEmoticonIcon.setGraphic(chatEmoticons);
        messagesPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        inputMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = inputMessage.getText();

                    // do your thing...
                    ChatConfig.getInstance().addMessageToOutbox(new Message(inputMessage.getText(), new Date()));
                    // clear text
                    inputMessage.setText("");
                    keyEvent.consume();
                    inputMessage.requestFocus();
                }
            }
        });

        messages.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
               AddMessage(messages.get(messages.size() - 1));
            }
        });

    }

}
