package whiteboard.FontysPublisherServer;


import whiteboard.FontysPublisher.RemotePublisher;

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
        RemotePublisher remotePublisher = new RemotePublisher();

        Registry registry = LocateRegistry.createRegistry(portNumber);
        registry.rebind(bindingName, remotePublisher);

        System.out.println("Remote publisher registered.");
        System.out.println("Port number: " + portNumber);
        System.out.println("Binding name: " + bindingName);

    }
}
