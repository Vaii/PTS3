package chat.repository;

import chat.Message;

import java.util.ArrayList;

/**
 * Created by bob on 14-5-17.
 */
public class ChatRepository {

    private IChatContext context;

    public ChatRepository(IChatContext context){
        this.context = context;
    }

    public ArrayList<Message> getAllMessages(){
        return context.GetAllMessages();
    }

    public void Insert(Message message){
         context.Insert(message);
    }
}
