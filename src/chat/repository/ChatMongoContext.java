package chat.repository;

import chat.Message;
import domain.DataSource;
import org.jongo.MongoCollection;

import java.util.ArrayList;


/**
 * Created by bob on 14-5-17.
 */
public class ChatMongoContext implements IChatContext {



    @Override
    public boolean DeleteById(int id) {
        return false;
    }

    @Override
    public boolean DeleteAll(ArrayList<Message> messages) {
        return false;
    }

    @Override
    public void Insert(Message message) {
        MongoCollection friends = DataSource.connect().getCollection("chat");
        friends.save(message);
    }

    @Override
    public ArrayList<Message> GetAllMessages() {
        return null;
    }
}
