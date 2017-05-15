package domain;

/**
 * Created by bob on 14-5-17.
 */

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.*;

public class DataSource {

    public DataSource(){
    }

    public static Jongo connect(){
        DB db = new MongoClient("37.139.13.186", 27017).getDB("pts-3");
        return new Jongo(db);
    }
}
