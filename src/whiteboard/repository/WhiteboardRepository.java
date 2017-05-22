package whiteboard.repository;

import whiteboard.Whiteboard;

/**
 * Created by Vai on 5/22/17.
 */
public class WhiteboardRepository {

    private IWhiteboardContext wContext;

    public WhiteboardRepository(IWhiteboardContext wContext){
        this.wContext = wContext;
    }

    public boolean saveWhiteboard(Whiteboard w){
        return wContext.saveWhiteboard(w);
    }
}
