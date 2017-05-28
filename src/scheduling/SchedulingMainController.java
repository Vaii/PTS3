package scheduling;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scheduling.repository.IScheduleMongoContext;
import scheduling.repository.ScheduleRepository;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.text.FontWeight.*;

/**
 * Created by bob on 13-5-17.
 */
public class SchedulingMainController implements Initializable {

    private ScheduleRepository repo;
    private int currentWeek;
    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    private Label lblAddItemIcon;

    // VBOXES & buttons
    @FXML Button btnWeek1;
    @FXML Button btnWeek2;
    @FXML Button btnWeek3;
    @FXML Button btnWeek4;


    // Weeks
    @FXML VBox vboxWeekA;
    @FXML VBox vboxWeekB;
    @FXML VBox vboxWeekC;
    @FXML VBox vboxWeekD;
    @FXML VBox vboxWeekE;

    @FXML
    Button btnAddNewItem;


    private void setVBoxesHeaders(int weeks){
        int i = weeks;
        for( Node node: mainAnchorPane.getChildren()) {
            if( node instanceof VBox) {
                Text txt = new Text("WEEK " + weeks);
                txt.setX(110);
                txt.setY(25);
                txt.setFont(Font.font(null, BOLD, 13));
                txt.setFill(Color.web("#000"));
                AnchorPane header = new AnchorPane(txt);
                header.setStyle("-fx-background-color: #ccc;");


                header.setMinHeight(40);
                ((VBox) node).getChildren().add(header);
                weeks++;
            }

        }
    }

    private void clearVBoxes(){
        vboxWeekA.getChildren().clear();
        vboxWeekB.getChildren().clear();
        vboxWeekC.getChildren().clear();
        vboxWeekD.getChildren().clear();
        vboxWeekE.getChildren().clear();
    }
    @FXML
    private void loadWeek1(ActionEvent event){
        currentWeek = 1;
        setActiveButton((Button)event.getSource());
        setVBoxesHeaders(1);
        loadAllScheduleItems(1);
    }

    @FXML
    private void loadWeek6(ActionEvent event){
        currentWeek = 6;
        setActiveButton((Button)event.getSource());
        setVBoxesHeaders(6);
        loadAllScheduleItems(6);
    }

    @FXML
    private void loadWeek11(ActionEvent event){
        currentWeek = 11;
        setActiveButton((Button)event.getSource());
        setVBoxesHeaders(11);
        loadAllScheduleItems(11);
    }

    @FXML
    private void loadWeek16(ActionEvent event){
        currentWeek = 16;
        setActiveButton((Button)event.getSource());
        setVBoxesHeaders(16);
        loadAllScheduleItems(16);
    }

    private void changePeriod(){

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

    public void openFormWindow(ScheduleItem scheduleItem, boolean isUpdate) throws IOException {
        Stage s = new Stage();
        s.setTitle("Add New Schedule Item");
        s.initModality(Modality.APPLICATION_MODAL);
        s.initStyle(StageStyle.UTILITY);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("NewItemBox.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        NewItemBoxController ct = (NewItemBoxController)loader.getController();
        if(isUpdate) {
            ct.setScheduleItem(scheduleItem);
        }

        Scene scene = new Scene(page);
        s.setScene(scene);
        s.setResizable(false);
        s.sizeToScene();
        s.showAndWait();
    }

    public void revertButtonStyles(){
        btnWeek1.setStyle("-fx-background-color: #ccc; -fx-background-radius: 0;");
        btnWeek1.setTextFill(Color.web("#000"));
        btnWeek2.setStyle("-fx-background-color: #ccc; -fx-background-radius: 0;");
        btnWeek2.setTextFill(Color.web("#000"));
        btnWeek3.setStyle("-fx-background-color: #ccc; -fx-background-radius: 0;");
        btnWeek3.setTextFill(Color.web("#000"));
        btnWeek4.setStyle("-fx-background-color: #ccc; -fx-background-radius: 0;");
        btnWeek4.setTextFill(Color.web("#000"));
    }

    public void setActiveButton(Button btn){
        clearVBoxes();
        revertButtonStyles();
        btn.setStyle("-fx-background-color: limegreen; -fx-background-radius: 0;");
        btn.setTextFill(Color.web("#fff"));
    }

    public void addScheduleItems(int week){

        for (ScheduleItem item: repo.GetAllScheduleItemsByWeek(week)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleItem.fxml"));
            // Create a controller instance
            ScheduleItemController controller = new ScheduleItemController(item);
            // Set it in the FXMLLoader
            loader.setController(controller);
            try {
                AnchorPane ms = loader.load();
                VBox box = (VBox) this.mainAnchorPane.lookup("#vboxWeek" + getWeekChar(week));
                box.getChildren().add(ms);
                System.out.println("new ScheduleItem added");
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private void loadAllScheduleItems(int week){
        addScheduleItems(week);
        addScheduleItems(week + 1);
        addScheduleItems( week + 2);
        addScheduleItems(week + 3);
        addScheduleItems(week + 4);
    }

    private String getWeekChar(int week){
        switch (week) {
            case 1:
            case 6:
            case 11:
            case 16:
                return "A";
            case 2:
            case 7:
            case 12:
            case 17:
                return "B";
            case 3:
            case 8:
            case 13:
            case 18:
                return "C";
            case 4:
            case 9:
            case 14:
            case 19:
                return "D";
            case 5:
            case 10:
            case 15:
            case 20:
                return "E";
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repo = new ScheduleRepository(new IScheduleMongoContext());

        Text addItem = GlyphsDude.createIcon(FontAwesomeIcon.PLUS_CIRCLE, "1.5em");
        addItem.setFill(Color.WHITE);
        this.lblAddItemIcon.setGraphic(addItem);
        setVBoxesHeaders(1);
        btnAddNewItem.setId("BUTTON");
        btnWeek1.setStyle("-fx-background-color: limegreen; -fx-background-radius: 0;");
        btnWeek1.setTextFill(Color.web("#fff"));
        loadAllScheduleItems(1);


        ScheduleConfig.getInstance().updateSchedule.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // Only if completed
                if (newValue) {
                    clearVBoxes();
                    setVBoxesHeaders(currentWeek);
                    loadAllScheduleItems(currentWeek);
                    ScheduleConfig.getInstance().updateSchedule.setValue(false);
                }
            }
        });
    }
}
