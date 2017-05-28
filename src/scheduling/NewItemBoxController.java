//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package scheduling;


import domain.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import scheduling.ScheduleConfig;
import scheduling.ScheduleItemStatus;
import scheduling.repository.IScheduleMongoContext;
import scheduling.repository.ScheduleRepository;

public class NewItemBoxController implements Initializable {
    private ScheduleItem scheduleItem;
    private ScheduleRepository repo;

    @FXML
    private Button btnAddScheduleItem;
    @FXML
    private TextField txtScheduleItemTitle;
    @FXML
    private TextField txtScheduleItemDescription;
    @FXML
    private ListView lvScheduleItemTeamMembers;
    @FXML
    private ComboBox cbtxtScheduleItemStatus;
    @FXML
    private TextField txtScheduleItemStatusPercentage;
    @FXML
    private DatePicker dpDeadline;
    @FXML
    private Label lblAgendaItem;
    @FXML
    private ComboBox<Integer> cbWeek;


    @FXML
    private ComboBox cbCategory;

    public NewItemBoxController() {
    }

    @FXML
    private void addNewSchedulItem(ActionEvent event) throws IOException {
        ArrayList<User> selectedTeammembers = new ArrayList(this.lvScheduleItemTeamMembers.getSelectionModel().getSelectedItems());
        List<TextField> txt = new ArrayList();
        txt.add(this.txtScheduleItemTitle);
        if(InputValidator.checkForStringInput(txt).booleanValue()) {
            repo.Insert(new ScheduleItem(this.txtScheduleItemTitle.getText(),
                    this.txtScheduleItemDescription.getText(),
                    Date.valueOf((LocalDate)this.dpDeadline.getValue()),
                    selectedTeammembers,
                    (ScheduleItemStatus)this.cbtxtScheduleItemStatus.getSelectionModel().getSelectedItem(),
                    Utility.parseIntOrZero(this.txtScheduleItemStatusPercentage.getText()),
                    cbWeek.getSelectionModel().getSelectedItem(),
                    (ScheduleItemCategory) this.cbCategory.getSelectionModel().getSelectedItem()));
            this.closeAction();
        }

    }

    @FXML
    private void closeAction() throws IOException {
        Stage stage = (Stage)this.btnAddScheduleItem.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setStatusPercentage() {
        ScheduleItemStatus currentStatus = (ScheduleItemStatus)this.cbtxtScheduleItemStatus.getSelectionModel().getSelectedItem();
        if(currentStatus == ScheduleItemStatus.INPROGRESS) {
            this.txtScheduleItemStatusPercentage.setDisable(false);
            this.txtScheduleItemStatusPercentage.setText("0%");
        } else if(currentStatus == ScheduleItemStatus.DONE) {
            this.txtScheduleItemStatusPercentage.setDisable(false);
            this.txtScheduleItemStatusPercentage.setText("100%");
        } else {
            this.txtScheduleItemStatusPercentage.setDisable(true);
            this.txtScheduleItemStatusPercentage.setText((String)null);
        }

    }

    @FXML
    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
        this.txtScheduleItemDescription.setText(scheduleItem.getDescription());
        this.txtScheduleItemTitle.setText(scheduleItem.getTitle());
        this.cbtxtScheduleItemStatus.setValue(scheduleItem.getStatus());
        this.dpDeadline.setValue(scheduleItem.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if(scheduleItem.getStatus() == ScheduleItemStatus.INPROGRESS || scheduleItem.getStatus() == ScheduleItemStatus.DONE) {
            this.txtScheduleItemStatusPercentage.setDisable(false);
            this.txtScheduleItemStatusPercentage.setText(String.valueOf(scheduleItem.getPercentComplete()));
        }

        this.btnAddScheduleItem.setText("Bevestigen");
        this.lblAgendaItem.setText("Bewerk Agenda Item");
    }

    public void initialize(URL location, ResourceBundle resources) {
        repo = new ScheduleRepository(new IScheduleMongoContext());
        this.txtScheduleItemStatusPercentage.setDisable(true);
        this.lvScheduleItemTeamMembers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.lvScheduleItemTeamMembers.setItems(ScheduleConfig.getInstance().getUsersObservableList());
        this.cbtxtScheduleItemStatus.getItems().setAll(ScheduleItemStatus.values());
        this.cbCategory.getItems().setAll(ScheduleItemCategory.values());
        this.dpDeadline.setValue(LocalDate.now());
        this.cbtxtScheduleItemStatus.setValue(ScheduleItemStatus.TODO);
        this.cbWeek.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);


    }
}
