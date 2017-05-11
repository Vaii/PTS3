package scheduling;

/**
 * Created by bob on 10-5-17.
 */
import domain.User;
import domain.Utility;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class ScheduleListViewCell extends ListCell<ScheduleItem> implements Initializable {
    FXMLLoader mLLoader;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblStatus;
    @FXML
    private FlowPane teamFlowPane;

    public ScheduleListViewCell() {
    }

    protected void updateItem(ScheduleItem si, boolean empty) {
        super.updateItem(si, empty);
        if(!empty && si != null) {
            if(this.mLLoader == null) {
                this.mLLoader = new FXMLLoader(this.getClass().getResource("ScheduleListViewCell.fxml"));
                this.mLLoader.setController(this);

                try {
                    this.mLLoader.load();
                } catch (IOException var4) {
                    var4.printStackTrace();
                }
            }

            this.lblTitle.setText(si.getTitle());
            this.lblDate.setText(Utility.getDDMMYYDate(si.getDeadline()));
            this.lblStatus.setText(si.getFormattedStatus());
            this.setTeamMemberLabels(si.getAssignedUsers());
            this.setText((String)null);
            this.setGraphic(this.gridPane);
        } else {
            this.setText((String)null);
            this.setGraphic((Node)null);
        }

    }

    @FXML
    private void setTeamMemberLabels(ArrayList<User> users) {
        int i = 0;

        for(Iterator var3 = users.iterator(); var3.hasNext(); ++i) {
            User u = (User)var3.next();
            Label x;
            if(i == 3) {
                x = new Label("+ " + String.valueOf(Integer.valueOf(users.size() - 3)));
            } else {
                x = new Label(u.getName());
                String style = String.format("-fx-background-color: %s; -fx-text-fill: white;  -fx-background-radius: 5; -fx-padding: 0 3;", new Object[]{u.color});
                x.setStyle(style);
            }

            this.teamFlowPane.getChildren().add(x);
        }

    }

    public void initialize(URL location, ResourceBundle resources) {
        this.teamFlowPane.setVgap(4.0D);
        this.teamFlowPane.setHgap(4.0D);
    }
}
