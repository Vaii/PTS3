//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package launcher;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LauncherController implements Initializable {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox menuBox;
    @FXML
    private Label lblWhiteboard;
    @FXML
    private Label lblWhiteboardText;
    @FXML
    private Label lblPlanning;
    @FXML
    private Label lblPlanningText;
    @FXML
    private Label lblGit;
    @FXML
    private Label lblGitText;
    @FXML
    private VBox vBoxFiles;
    @FXML
    private Label lblFile;
    @FXML
    private Label lblFileText;
    @FXML
    private AnchorPane anchorPaneSubMenu;
    @FXML
    private Label lblExpandIcon;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private BorderPane contentBorderPane;

    public LauncherController() {
    }

    @FXML
    private void changeBackgroundColorOn(MouseEvent event) {
        VBox c = (VBox)event.getSource();
        Iterator var3 = c.getChildren().iterator();

        while(var3.hasNext()) {
            Node node = (Node)var3.next();
            if(node instanceof Label) {
                ((Label)node).setTextFill(Color.web("#c43235"));
                if(((Label)node).getGraphic() != null) {
                    Text file = (Text)((Label)node).getGraphic();
                    file.setFill(Color.web("#c43235"));
                    ((Label)node).setGraphic(file);
                }
            }
        }

    }

    @FXML
    private void changeBackgroundColorOff(MouseEvent event) {
        VBox c = (VBox)event.getSource();
        Iterator var3 = c.getChildren().iterator();

        while(var3.hasNext()) {
            Node node = (Node)var3.next();
            if(node instanceof Label) {
                ((Label)node).setTextFill(Color.web("#ffffff"));
                if(((Label)node).getGraphic() != null) {
                    Text file = (Text)((Label)node).getGraphic();
                    file.setFill(Color.web("#ffffff"));
                    ((Label)node).setGraphic(file);
                }
            }
        }

    }

    @FXML
    private void openChat() throws IOException {
        if(this.contentBorderPane.getChildren().contains(this.contentBorderPane.lookup("#chatPanel"))) {
            this.contentBorderPane.getChildren().remove(this.contentBorderPane.lookup("#chatPanel"));
        } else {
            AnchorPane chat = (AnchorPane)FXMLLoader.load(this.getClass().getResource("/chat/chatPanel.fxml"));
            AnchorPane.setRightAnchor(chat, Double.valueOf(0.0D));
            AnchorPane.setBottomAnchor(chat, Double.valueOf(0.0D));
            chat.setId("chatPanel");
            chat.setPrefWidth(295.0D);
            this.contentBorderPane.setRight(chat);
        }

    }

    @FXML
    private void loadScheduling() throws IOException {
        AnchorPane schedule = (AnchorPane)FXMLLoader.load(this.getClass().getResource("/scheduling/Scheduling.fxml"));
        this.contentBorderPane.setLeft(schedule);
    }

    @FXML
    private void loadStorage() throws IOException {
        AnchorPane schedule = (AnchorPane)FXMLLoader.load(this.getClass().getResource("/storage/Storage.fxml"));
        this.contentBorderPane.setLeft(schedule);
    }

    @FXML
    private void loadWhiteboard() throws IOException {
        AnchorPane schedule = (AnchorPane)FXMLLoader.load(this.getClass().getResource("/whiteboard/Whiteboard.fxml"));
        this.contentBorderPane.setLeft(schedule);
    }

    @FXML
    private void loadGit() throws IOException {
        AnchorPane schedule = (AnchorPane)FXMLLoader.load(this.getClass().getResource("/git/gitForm.fxml"));
        this.contentBorderPane.setLeft(schedule);
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.mainBorderPane.setMinSize(1280.0D, 720.0D);
        Text whiteboard = GlyphsDude.createIcon(FontAwesomeIcon.PENCIL_SQUARE, "3em");
        whiteboard.setFill(Color.WHITE);
        this.lblWhiteboard.setGraphic(whiteboard);
        Text planning = GlyphsDude.createIcon(FontAwesomeIcon.CALENDAR, "3em");
        planning.setFill(Color.WHITE);
        this.lblPlanning.setGraphic(planning);
        Text git = GlyphsDude.createIcon(FontAwesomeIcon.GIT_SQUARE, "3em");
        git.setFill(Color.WHITE);
        this.lblGit.setGraphic(git);
        Text file = GlyphsDude.createIcon(FontAwesomeIcon.DROPBOX, "3em");
        file.setFill(Color.WHITE);
        this.lblFile.setGraphic(file);
        Text chat = GlyphsDude.createIcon(FontAwesomeIcon.ANGLE_DOUBLE_UP, "1.2em");
        chat.setFill(Color.WHITE);
        this.lblExpandIcon.setGraphic(chat);

        // load whiteboard
        AnchorPane schedule = null;
        try {
            schedule = (AnchorPane) FXMLLoader.load(this.getClass().getResource("/whiteboard/Whiteboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.contentBorderPane.setLeft(schedule);
    }
}
