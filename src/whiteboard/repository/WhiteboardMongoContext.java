package whiteboard.repository;

import com.mongodb.Mongo;
import domain.DataSource;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import whiteboard.*;

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
    public ArrayList<Whiteboard> loadAllWhiteboards(String userId) {
        ArrayList<Whiteboard> whiteboards = new ArrayList<>();

        MongoCollection whiteboardCollection = DataSource.connect().getCollection("Whiteboard");
        MongoCursor<Whiteboard> userWhiteboards = whiteboardCollection.find("{UserID:#}", userId).as(Whiteboard.class);


        while(userWhiteboards.hasNext()){
                whiteboards.add(userWhiteboards.next());
        }

        return whiteboards;

    }
}
