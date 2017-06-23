package whiteboard;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import whiteboard.FontysPublisher.IRemotePropertyListener;
import whiteboard.FontysPublisher.IRemotePublisherForDomain;
import whiteboard.FontysPublisher.IRemotePublisherForListener;
import whiteboard.Shared.DrawEvent;
import whiteboard.Shared.MoveEvent;
import whiteboard.Shared.PictureEvent;
import whiteboard.Shared.VideoEvent;

import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        else if(property.equals("Move")){
            MoveEvent moveEvent = (MoveEvent) evt.getNewValue();
            wController.requestMoveEvent(property, moveEvent);
        }
        else if(property.equals("Video")){
            VideoEvent videoEvent = (VideoEvent) evt.getNewValue();
            wController.requestDrawVideo(property, videoEvent);
        }
        else if(property.equals("Picture")){
            PictureEvent pictureEvent = (PictureEvent) evt.getNewValue();
            wController.requestDrawPicture(property, pictureEvent);
        }


    }

    public void connectToPublisher(){
        try{
            System.setProperty("java.rmi.server.hostname", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
            Registry registry = LocateRegistry.getRegistry("192.168.178.26", portNumber);
            publisherForDomain = (IRemotePublisherForDomain) registry.lookup(bindingName);
            publisherForListener = (IRemotePublisherForListener) registry.lookup(bindingName);
            connected = true;
            System.out.println("Connection with the remotepublisher established");
        }
        catch(RemoteException | NotBoundException | UnknownHostException re){
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

    public void broadcast(String property, Object object){
        if(connected){
            threadPool.execute(() -> {
                try{
                    publisherForDomain.inform(property, null, object);
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
