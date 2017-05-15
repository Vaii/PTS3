package chat.repository;

import chat.Message;
import java.util.ArrayList;

/**
 * Created by bob on 14-5-17.
 */

public interface IChatContext {

    boolean DeleteById(int id);
    boolean DeleteAll(ArrayList<Message> messages);
    void Insert(Message message);
    ArrayList<Message> GetAllMessages();
}
