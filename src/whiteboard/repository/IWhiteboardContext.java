package whiteboard.repository;

import domain.Config;
import whiteboard.Whiteboard;

import java.util.ArrayList;

/**
 * Created by Vai on 5/22/17.
 */
public interface IWhiteboardContext {

    boolean saveWhiteboard(Whiteboard w);
    Whiteboard loadSingleWhiteboard(int userId);
    ArrayList<Whiteboard> loadAllWhiteboards(String userId);
}
