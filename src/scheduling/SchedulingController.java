package scheduling;

/**
 * Created by bob on 10-5-17.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SchedulingController implements Initializable{
    @FXML
    private ListView lvWeekOne;
    @FXML
    public Label lblPeriode;

    public SchedulingController() {
    }

    @FXML
    private void openFormWindow(ActionEvent event) {
        System.out.println("Button + is pressed");
        new NewItemBoxController();

        try {
            this.openFormWindow(new ScheduleItem(), false);
        } catch (Exception var4) {
            System.out.println("Shit went wrong ");
            var4.printStackTrace();
        }

    }

    @FXML
    private void expandScheduleItem(MouseEvent event) {
        ListView currentListview = (ListView)event.getSource();
        ScheduleItem selectedScheduleItem = (ScheduleItem)currentListview.getSelectionModel().getSelectedItem();
        System.out.println("Mousebutton is pressed");
        if(event.getClickCount() == 2 && selectedScheduleItem != null) {
            System.out.println("Mousebutton is pressed twice");

            try {
                this.openFormWindow(selectedScheduleItem, true);
            } catch (Exception var5) {
                System.out.println("Shit went wrong ");
                var5.printStackTrace();
            }
        }

    }

    public void openFormWindow(ScheduleItem scheduleItem, boolean isUpdate) throws IOException {
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("NewItemBox.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        NewItemBoxController ct = (NewItemBoxController)loader.getController();
        if(isUpdate) {
            ct.setScheduleItem(scheduleItem);
        }

        Scene scene = new Scene(page);
        s.setScene(scene);
        s.showAndWait();
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.lvWeekOne.setItems(ScheduleConfig.getInstance().getScheduleItemObservableList());
        this.lvWeekOne.setCellFactory((ScheduleListView) -> {
            return new ScheduleListViewCell();
        });
    }
}
