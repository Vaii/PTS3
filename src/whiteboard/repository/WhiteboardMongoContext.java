package whiteboard.repository;

import domain.DataSource;
import org.jongo.MongoCollection;
import whiteboard.Whiteboard;

import java.util.ArrayList;

/**
 * Created by Vai on 5/22/17.
 */
public class WhiteboardMongoContext implements IWhiteboardContext {

    @Override
    public boolean saveWhiteboard(Whiteboard w) {

        try{
            MongoCollection whiteboards = DataSource.connect().getCollection("Whiteboard");
            whiteboards.save(w);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Whiteboard loadSingleWhiteboard(int userId) {
        return null;
    }

    @Override
    public ArrayList<Whiteboard> loadAllWhiteboards(int userId) {
        return null;
    }
}
