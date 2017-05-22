package whiteboard.repository;

import domain.Config;
import whiteboard.Whiteboard;

/**
 * Created by Vai on 5/22/17.
 */
public interface IWhiteboardContext {

    boolean saveWhiteboard(Whiteboard w);
    Whiteboard loadWhiteboard(int userId);
}
