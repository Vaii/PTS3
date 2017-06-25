package chat.repository;


import chat.ChatConfig;
import chat.Message;
import domain.Utility;
import javafx.application.Platform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * Created by bob on 20-5-17.
 */

@ClientEndpoint
public  class ChatWebSocketClient {
    private static final Logger LOGGER = Logger.getLogger(ChatWebSocketClient.class.getName());
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(InputStream input) throws IOException {
        System.out.println("WebSocket message Received!");
        Message message = (Message)Utility.deserialize(input);
        System.out.println(message.getMessage());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChatConfig.getInstance().addMessageToInbox(message);
            }
        });

    }

    @OnClose
    public void onClose() {
        connectToWebSocket();
    }

    private void connectToWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = URI.create("ws://95.85.22.21:8080/BinaryWebSocketServer/messages");
            container.connectToServer(this, uri);
            System.out.println("Connected with WebSocket server.");
        } catch (DeploymentException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(Message message) {
        try (InputStream input = new ByteArrayInputStream(Utility.serializeToBytes(message));
             OutputStream output = session.getBasicRemote().getSendStream()) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void openConnection(){
        connectToWebSocket();
    }
}
