package whiteboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import whiteboard.repository.WhiteboardMongoContext;
import whiteboard.repository.WhiteboardRepository;

import javax.swing.text.html.ListView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vai on 5/28/17.
 */
public class LoadController implements Initializable {

    @FXML
    ListView whiteboardList;

    private WhiteboardRepository wRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wRepo = new WhiteboardRepository(new WhiteboardMongoContext());

    }
}
