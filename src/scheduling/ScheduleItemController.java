package scheduling;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import domain.User;
import domain.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


/**
 * Created by bob on 26-5-17.
 */
public class ScheduleItemController implements Initializable {

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
        this.title.set(scheduleItem.getTitle());
        this.description.set(scheduleItem.getDescription());
        this.date.set(Utility.getDDMMYYDate(scheduleItem.getDeadline()));
        this.status.set(scheduleItem.getFormattedStatus());
        team = scheduleItem.getAssignedUsers();
        category = scheduleItem.getCategory();


    }

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
