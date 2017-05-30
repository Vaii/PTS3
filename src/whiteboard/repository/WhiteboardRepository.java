package whiteboard.repository;

import whiteboard.Whiteboard;

import java.util.ArrayList;

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

    public ArrayList<Whiteboard> loadWhiteboards(String userID){
        return wContext.loadAllWhiteboards(userID);
    }
}
