package scheduling;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import domain.User;
import domain.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import scheduling.repository.IScheduleMongoContext;
import scheduling.repository.ScheduleRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Created by bob on 26-5-17.
 */
public class ScheduleItemController implements Initializable {

    private ScheduleItem scheduleItem;
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private ScheduleItemCategory category;
    ArrayList<User> team = new ArrayList<>();

    @FXML
    private Text txtTitle;
    @FXML
    private Text txtDescription;
    @FXML
    private Text txtDate;
    @FXML
    private Text txtStatus;
    @FXML
    private HBox hboxTeam;
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblDeleteIcon;
    @FXML
    private Label lblEditIcon;

    @FXML
    private void setTeamMemberLabels(ArrayList<User> users) {
        int i = 0;
        if(users.isEmpty()){
            Label x = new Label("Not assigned yet.");
            this.hboxTeam.getChildren().add(x);
        }
        else{
            for(Iterator var3 = users.iterator(); var3.hasNext(); ++i) {
                User u = (User)var3.next();
                Label x;
                if(i == 3) {
                    x = new Label("+ " + String.valueOf(Integer.valueOf(users.size() - 3)));
                    x.setFont(Font.font (null, 11));
                } else {
                    x = new Label(u.getName());
                    String style = String.format("-fx-background-color: %s; -fx-text-fill: white;  -fx-background-radius: 5; -fx-padding: 0 2", new Object[]{u.color});
                    x.setFont(Font.font (null, 11));
                    x.setStyle(style);

                }

                this.hboxTeam.getChildren().add(x);
            }
        }


    }

    private void setCategoryIcon(){
        Text addItem = new Text();
        switch (category) {
            case DOCUMENTATION:
                addItem = GlyphsDude.createIcon(FontAwesomeIcon.FILE_TEXT_ALT, "1.5em");
                break;
            case RESEARCH:
            addItem =  GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "1.5em");
            break;
            case MEETING:
            addItem =   GlyphsDude.createIcon(FontAwesomeIcon.USERS, "1.5em");
                break;
            case PROGRAMMING:
            addItem = GlyphsDude.createIcon(FontAwesomeIcon.COGS, "1.5em");
                break;
            case TESTING:
            addItem = GlyphsDude.createIcon(FontAwesomeIcon.BUG, "1.5em");
                break;
            case ASSIGNMENT_DEADLINE :
            addItem = GlyphsDude.createIcon(FontAwesomeIcon.EXCLAMATION_TRIANGLE, "1.5em");
                break;
        }
        addItem.setFill(Color.web("#333"));
        // this.lblCategory.setStyle("-fx-background-color: #f00; -fx-background-radius: 0;");
        this.lblCategory.setGraphic(addItem);
    }

    public ScheduleItemController(ScheduleItem scheduleItem){
        this.scheduleItem = scheduleItem;
        this.title.set(scheduleItem.getTitle());
        this.description.set(scheduleItem.getDescription());
        this.date.set(Utility.getDDMMYYDate(scheduleItem.getDeadline()));
        this.status.set(scheduleItem.getFormattedStatus());
        team = scheduleItem.getAssignedUsers();
        category = scheduleItem.getCategory();


    }

    @FXML
    public void showConfigButtons(){
        Text delete =  GlyphsDude.createIcon(FontAwesomeIcon.TRASH, "1.2em");
        delete.setFill(Color.web("#222"));
        this.lblDeleteIcon.setGraphic(delete);
        Text edit =  GlyphsDude.createIcon(FontAwesomeIcon.PENCIL_SQUARE, "1.2em");
        edit.setFill(Color.web("#222"));
        this.lblEditIcon.setGraphic(edit);
    }

    @FXML
    public void hideConfigButtons(){
        this.lblEditIcon.setGraphic(null);
        this.lblDeleteIcon.setGraphic(null);
    }
    @FXML
    private void changeBackgroundColorOn(MouseEvent event) {
        Label c = (Label)event.getSource();
        Text x =  (Text)c.getGraphic();
        x.setFill(Color.web("#f00"));
        c.setGraphic(x);
    }

    @FXML
    private void changeBackgroundColorOff(MouseEvent event) {
        Label c = (Label)event.getSource();
        Text x =  (Text)c.getGraphic();
        x.setFill((Color.web("#333")));
        c.setGraphic(x);
    }

    @FXML
    private void deleteScheduleItem(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Schedule Item");
        alert.setHeaderText("Are you sure about this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (ButtonType.OK == result.get()){
            System.out.println("deleting item");
            ScheduleRepository repo = new ScheduleRepository(new IScheduleMongoContext());
            repo.DeleteById(this.scheduleItem.get_id());
            ScheduleConfig.getInstance().updateSchedule.setValue(true);

        } else {
            // ... user chose CANCEL or closed the dialog
        }


    }
    @FXML
    private void editScheduleItem(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTitle.setText(title.get());
        txtDescription.setText(description.get());
        txtDate.setText(date.get());
        txtStatus.setText(status.get());
        setTeamMemberLabels(team);
        hboxTeam.setSpacing(3);
        setCategoryIcon();
    }

}
