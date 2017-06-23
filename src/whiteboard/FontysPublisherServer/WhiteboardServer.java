package whiteboard.FontysPublisherServer;


import whiteboard.FontysPublisher.RemotePublisher;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Vai on 5/30/17.
 */
public class WhiteboardServer {

    private static int portNumber = 1099;
    private static String bindingName = "publisher";

    public static void main(String[] args) throws RemoteException{
// Create an instance of RemotePublisher
        RemotePublisher remotePublisher = null;
        try {
            remotePublisher = new RemotePublisher();
            Registry registry = LocateRegistry.createRegistry(portNumber);
            System.setProperty("java.rmi.server.hostname", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
            registry.rebind(bindingName, remotePublisher);

            System.out.println("connection established");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
