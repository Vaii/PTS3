package whiteboard;

import whiteboard.FontysPublisher.IRemotePropertyListener;
import whiteboard.FontysPublisher.IRemotePublisherForDomain;
import whiteboard.FontysPublisher.IRemotePublisherForListener;
import whiteboard.Shared.DrawEvent;
import whiteboard.Shared.MoveEvent;

import java.beans.PropertyChangeEvent;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vai on 5/30/17.
 */
public class WhiteboardCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {

    private final WhiteboardController wController;

    private IRemotePublisherForDomain publisherForDomain;
    private IRemotePublisherForListener publisherForListener;
    private static int portNumber = 1099;
    private static String bindingName = "publisher";
    private boolean connected = false;

    private final int nrThreads = 10;
    private ExecutorService threadPool = null;


    public WhiteboardCommunicator(WhiteboardController wController) throws RemoteException{
        this.wController = wController;
        threadPool = Executors.newFixedThreadPool(nrThreads);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        String property = evt.getPropertyName();

        if(property.equals("Text")){
            DrawEvent drawEvent = (DrawEvent) evt.getNewValue();
            wController.requestDrawText(property, drawEvent);
        }
        if(property.equals("Move")){
            MoveEvent moveEvent = (MoveEvent) evt.getNewValue();
            wController.requestMoveEvent(property, moveEvent);
        }


    }

    public void connectToPublisher(){
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", portNumber);
            publisherForDomain = (IRemotePublisherForDomain) registry.lookup(bindingName);
            publisherForListener = (IRemotePublisherForListener) registry.lookup(bindingName);
            connected = true;
            System.out.println("Connection with the remotepublisher established");
        }
        catch(RemoteException | NotBoundException re){
            connected = false;
            System.err.println("Cannot establish connection to the remote publisher");
            System.err.println("Run WhiteboardServer to start remote publisher");
        }
    }

    public void register(String property){
        if(connected){
            try{
                publisherForDomain.registerProperty(property);
            }
            catch(RemoteException ex){
                Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void subscribe(String property){
        if(connected){
            final IRemotePropertyListener listener = this;
            threadPool.execute(() -> {
                try{
                    publisherForListener.subscribeRemoteListener(listener, property);
                }
                catch(RemoteException ex){
                    Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void unsubscribe(String property){
        if(connected){
            final IRemotePropertyListener listener = this;
            threadPool.execute(() -> {
                try{
                    publisherForListener.unsubscribeRemoteListener(listener, property);
                }
                catch(RemoteException ex){
                    Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void broadcast(String property, DrawEvent drawEvent){
        if(connected){
            threadPool.execute(() -> {
                try{
                    publisherForDomain.inform(property, null, drawEvent);
                }
                catch(RemoteException ex){
                    Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void broadcast(String property, MoveEvent moveEvent){
        if(connected){
            threadPool.execute(() ->{
                try{
                    publisherForDomain.inform(property, null, moveEvent);
                }
                catch(RemoteException ex){
                    Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void stop(){
        if(connected){
            try{
                publisherForListener.unsubscribeRemoteListener(this,null);
            }
            catch(RemoteException ex){
                Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try{
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch(NoSuchObjectException ex){
            Logger.getLogger(WhiteboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
