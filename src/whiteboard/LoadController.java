package whiteboard;

import com.sun.javafx.UnmodifiableArrayList;
import domain.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import whiteboard.repository.WhiteboardMongoContext;
import whiteboard.repository.WhiteboardRepository;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Vai on 5/28/17.
 */
public class LoadController implements Initializable {

    @FXML
    ListView whiteboardList;

    private WhiteboardRepository wRepo;

    private WhiteboardController wc;

    private ObservableList<Whiteboard> whiteboards = FXCollections.observableArrayList();
    private ArrayList<Whiteboard> queriedWhiteboards;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wRepo = new WhiteboardRepository(new WhiteboardMongoContext());
        queriedWhiteboards = wRepo.loadWhiteboards(Config.getUser().get_id());
        whiteboards.addAll(queriedWhiteboards);
        whiteboardList.setItems(whiteboards);

        whiteboardList.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                Whiteboard currentSelectedItem = (Whiteboard)whiteboardList.getSelectionModel().getSelectedItem();
                wc.setLoadedWhiteboard(currentSelectedItem);
                Stage stage = (Stage)whiteboardList.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setWc(WhiteboardController wc) {
        this.wc = wc;
    }
}
