package chat;

import chat.repository.ChatWebSocketClient;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by bob on 20-5-17.
 */
public class ChatConfig {
    private static ChatConfig instance;
    ChatWebSocketClient client = new ChatWebSocketClient();

    private final ObservableList<Message> inbox = FXCollections.observableArrayList(new ArrayList<Message>());
    private final ObservableList<Message> outbox = FXCollections.observableArrayList(new ArrayList<Message>());

    public ObservableList<Message> getMessagesObservableList() {
        return this.inbox;
    }

    public void addMessageToOutbox(Message m) {
        this.outbox.add(m);
    }

    public void addMessageToInbox(Message m) {
        this.inbox.add(m);
    }


    public ChatConfig() {
        client.openConnection();
        outbox.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("sending message with websockets");
                client.sendMessage(outbox.get(outbox.size() - 1));
            }
        });
    }

    public static ChatConfig getInstance() {
        if(instance == null) {
            Class var0 = ChatConfig.class;
            synchronized(ChatConfig.class) {
                    instance = new ChatConfig();
                    instance.init();
            }
        }

        return instance;
    }

    public void init() {

    }


}
