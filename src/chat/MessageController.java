package chat;

import domain.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by bob on 14-5-17.
 */
public class MessageController implements Initializable{

    final ImageView imageView = new ImageView("http://i.imgur.com/X1rrNrg.png");
    final Circle clip = new Circle(12.5, 12.5, 12.5);

    private StringProperty message = new SimpleStringProperty();
    private StringProperty messageDate = new SimpleStringProperty();
    private StringProperty username = new SimpleStringProperty();
    private Message messageObject;
    @FXML
    private Text txtMessage;
    @FXML
    private Text txtMessageDate;
    @FXML
    private Text txtUserName;

    @FXML
    private Pane imagePane;

    public MessageController(Message message){
        messageObject = message;
        this.message.set(message.getMessage());
        this.messageDate.set(message.getDate().toString());
        username.set(message.getUser().getName());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtMessage.setText(message.get());
        txtMessageDate.setText(messageDate.get());
        txtUserName.setText(username.get());
        imageView.setClip(clip);
        imagePane.getChildren().add(imageView);
        txtUserName.setFill(javafx.scene.paint.Color.web(Color.WebColors.getWebColor(messageObject.getUser().getColor())));



    }
}
