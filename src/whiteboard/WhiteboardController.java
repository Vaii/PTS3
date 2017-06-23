package whiteboard;

/**
 * Created by bob on 10-5-17.
 */
import android.util.Base64;
import com.sun.deploy.cache.Cache;
import domain.Config;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import whiteboard.Shared.DrawEvent;
import whiteboard.Shared.MoveEvent;
import whiteboard.Shared.PictureEvent;
import whiteboard.Shared.VideoEvent;
import whiteboard.repository.WhiteboardMongoContext;
import whiteboard.repository.WhiteboardRepository;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vai on 4/2/17.
 */
public class WhiteboardController implements Initializable {

    public String getVideoUrl() {
        return videoUrl;
    }

    public WhiteboardRepository wRepo;

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    private final Delta dragDelta = new Delta();
    private Whiteboard whiteboard;

    public Stage getPictureStage() {
        return pictureStage;
    }

    public void setPictureStage(Stage pictureStage) {
        this.pictureStage = pictureStage;
    }

    private Stage pictureStage;

    public Text getUserInput() {
        return userInput;
    }

    public void setUserInput(Text userInput) {
        this.userInput = userInput;
    }

    private Text userInput;

    private String videoUrl;

    private String whiteboardName;

    public void setWhiteboardName(String whiteboardName) {
        this.whiteboardName = whiteboardName;
    }

    public Whiteboard getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(Whiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }

    private ArrayList<Whiteboard> whiteboards;

    private ObservableList<String> whiteboarditemList = FXCollections.observableArrayList();

    private WhiteboardCommunicator communicator;

    private Node selectedNode;

    private Random rnd;

    @FXML
    TabPane whiteboardPane;
    @FXML
    AnchorPane whiteboardScene;


    private double menuItemxPos;
    private double menuItemyPos;
    private ContextMenu deleteMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wRepo = new WhiteboardRepository(new WhiteboardMongoContext());

        deleteMenu = new ContextMenu();
        MenuItem Delete = new MenuItem("Delete");
        deleteMenu.getItems().addAll(Delete);
        deleteMenu.setOnAction(event -> deleteItem());

        rnd = new Random(100000);


        final ContextMenu contextMenu = new ContextMenu();
        MenuItem Text = new MenuItem("Text");
        MenuItem Video = new MenuItem("Video");
        MenuItem Picture = new MenuItem("Picture");
        contextMenu.getItems().addAll(Text,Video,Picture);

        Text.setOnAction(event -> startText());
        Video.setOnAction(event -> startVideo());
        Picture.setOnAction(event -> startPicture());

        whiteboardPane.setOnMousePressed(event -> {
            if(event.isSecondaryButtonDown()){
                contextMenu.show(whiteboardPane, event.getScreenX(), event.getScreenY());
                menuItemxPos = event.getX();
                menuItemyPos = event.getY();
            }
        });

        try{
            this.communicator = new WhiteboardCommunicator(this);
            connectToPublisher();
        }
        catch(RemoteException ex){
            Logger.getLogger(WhiteboardController.class.getName()).log(Level.SEVERE, ex, null);
        }
    }

    private void startVideo(){

        try {
            Stage videoStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Video.fxml"));
            Parent root = loader.load();
            VideoController vc = loader.getController();

            vc.setWhiteboardController(this);
            vc.setVideoStage(videoStage);
            videoStage.setTitle("Enter video Url");
            videoStage.setScene(new Scene(root));
            videoStage.showAndWait();

            if (videoUrl != null) {

                broadcastDrawVideo("Video", menuItemxPos, menuItemyPos, Integer.toString(rnd.nextInt()), videoUrl);

            }
        }
        catch(Exception e){
            Logger.getLogger(WhiteboardController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void startPicture(){

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select picture");
            FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter(
                    "Pictures", "*.jpg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(extensions);
            Picture p = new Picture();

            p.setFile(fileChooser.showOpenDialog(pictureStage));

            if (p.getFile() != null) {

                FileInputStream fis = new FileInputStream(p.getFile());
                byte[] picture = inputStreamToByteArray(fis);
                broadcastDrawPicture("Picture", menuItemxPos, menuItemyPos, Integer.toString(rnd.nextInt()), picture);
            }
        }
        catch(Exception e){
            Logger.getLogger(WhiteboardController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private void startText(){
        try{
            userInput = null;
            Stage textStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Text.fxml"));
            Parent root = loader.load();
            TextController tc = loader.getController();
            tc.setMainController(this);
            tc.setTextStage(textStage);

            textStage.setTitle("Text input");
            textStage.setScene(new Scene(root));
            textStage.showAndWait();

            if(userInput != null)

                broadcastDrawText("Text", menuItemxPos, menuItemyPos, userInput.getText(), Integer.toString(rnd.nextInt()));
            }
        catch(IOException e){
            Logger.getLogger(WhiteboardController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    private void deleteItem(){

    }

    private void connectToPublisher() {

        communicator.connectToPublisher();

        communicator.register("Text");
        communicator.subscribe("Text");

        communicator.register("Picture");
        communicator.subscribe("Picture");

        communicator.register("Video");
        communicator.subscribe("Video");

        communicator.register("Move");
        communicator.subscribe("Move");
    }

    public void broadcastDrawText(String property, double xPos, double yPos, String input, String id){
        DrawEvent drawEvent = new DrawEvent(xPos, yPos, input, id);
        communicator.broadcast(property, drawEvent);
    }

    public void broadcastMoveEvent(String property, double xPos, double yPos, String objectID){
        MoveEvent moveEvent = new MoveEvent(xPos, yPos, objectID);
        communicator.broadcast(property, moveEvent);
    }

    public void broadcastDrawVideo(String property, double xPos, double yPos, String id, String url){
        VideoEvent videoEvent = new VideoEvent(xPos, yPos, url, id);
        communicator.broadcast(property, videoEvent);
    }

    public void broadcastDrawPicture(String property, double xPos, double yPos, String id, byte[] picture){
        PictureEvent pictureEvent = new PictureEvent(xPos, yPos, id, picture);
        communicator.broadcast(property, pictureEvent);
    }

    private void moveNode(String objectId, double xPos, double yPos){

        whiteboardPane.lookup("#" + objectId).setLayoutX(xPos);
        whiteboardPane.lookup("#" + objectId).setLayoutY(yPos);
    }

    private void drawText(String Text, double xPos, double yPos, String id){
        Label label = new Label(Text);
        label.setLayoutX(xPos);
        label.setLayoutY(yPos);

        makeItemDraggable(label);

        label.setId(id);

        addItemToPane(label);

    }

    private void drawPicture(byte[] picture, double xPos, double yPos, String id){

        try{
            File imageToCreate = File.createTempFile("image", "png", Cache.getActiveCacheDir());
            imageToCreate.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(imageToCreate);
            fos.write(picture);
            fos.close();
            String imagepath = imageToCreate.toURI().toString();

            ImageView view = new ImageView(imagepath);

            view.setLayoutX(xPos);
            view.setLayoutY(yPos);
            view.setId(id);
            makeItemDraggable(view);

            addItemToPane(view);
        }
        catch(IOException e){
            Logger.getLogger(WhiteboardController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void drawVideo(String url, double xPos, double yPos, String id){

        WebView view  = new WebView();
        view.getEngine().load(url);
        view.setLayoutY(yPos);
        view.setLayoutX(xPos);
        view.setPrefWidth(400);
        view.setPrefHeight(250);
        view.setId(id);

        makeItemDraggable(view);
        addItemToPane(view);
    }

    public void requestMoveEvent(String property, MoveEvent event){
        Platform.runLater(() ->{
            moveNode(event.getObjectID(), event.getxPos(), event.getyPos());
        });
    }

    public void requestDrawText(String property, DrawEvent event){
        Platform.runLater(() -> {
            drawText(event.getText(), event.getxPos(), event.getyPos(), event.getId());
        });
    }

    public void requestDrawVideo(String property, VideoEvent event){
        Platform.runLater(() ->{
            drawVideo(event.getVideoURL(), event.getxPos(), event.getyPos(), event.getID());
        });
    }

    public void requestDrawPicture(String property, PictureEvent event){
        Platform.runLater(() ->{
            drawPicture(event.getPicture(), event.getxPos(), event.getyPos(), event.getId());
        });
    }


    public byte[] inputStreamToByteArray(InputStream inputStream) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) > 0){
            baos.write(buffer,0,bytesRead);
        }
        return baos.toByteArray();
    }

    private void addItemToPane(Node n){
        Pane pane = new Pane();

        if(whiteboardPane.getSelectionModel().getSelectedItem().getContent() != null){
            pane.getChildren().addAll(whiteboardPane.getSelectionModel().getSelectedItem().getContent());
        }

        pane.getChildren().add(n);
        whiteboardPane.getSelectionModel().getSelectedItem().setContent(pane);
    }

    private void makeItemDraggable(Node n){

        n.setOnMousePressed(event -> {
                dragDelta.setX(n.getLayoutX() - event.getSceneX());
                dragDelta.setY(n.getLayoutY() - event.getSceneY());
                n.setCursor(Cursor.MOVE);
        });
        n.setOnMouseReleased(e -> {
            n.setCursor(Cursor.HAND);
            broadcastMoveEvent("Move", n.getLayoutX(), n.getLayoutY(), n.getId());
        });
        n.setOnMouseDragged(e ->{
            n.setLayoutY(e.getSceneY() + dragDelta.getY());
            n.setLayoutX(e.getSceneX() + dragDelta.getX());
        });
        n.setOnMouseEntered(e -> n.setCursor(Cursor.HAND));

    }

}