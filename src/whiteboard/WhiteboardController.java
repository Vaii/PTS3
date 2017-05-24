package whiteboard;

/**
 * Created by bob on 10-5-17.
 */
import android.util.Base64;
import domain.Config;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import whiteboard.repository.WhiteboardMongoContext;
import whiteboard.repository.WhiteboardRepository;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    private SaveController sc;

    private int selectedTab = 1;

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
    @FXML
    Button saveButton;
    @FXML
    Button newButton;
    @FXML
    Button browseButton;
    @FXML
    ComboBox whiteboardItems;
    @FXML
    TabPane whiteboardPane;
    @FXML
    AnchorPane whiteboardScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wRepo = new WhiteboardRepository(new WhiteboardMongoContext());
        whiteboards = new ArrayList<>();

        whiteboarditemList.add("Select Item");
        whiteboarditemList.add("Text");
        whiteboarditemList.add("Picture");
        whiteboarditemList.add("Video");
        whiteboardItems.setItems(whiteboarditemList);

        whiteboard = new Whiteboard(Integer.toString(whiteboardPane.getTabs().size()), Config.getUser().get_id());
        whiteboards.add(whiteboard);

        newButton.setOnAction(this::addPane);
        whiteboardItems.getSelectionModel().selectFirst();
        saveButton.setOnAction(this::saveWhiteboard);

        whiteboardPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> selectedTab = newValue.getTabPane().getSelectionModel().getSelectedIndex());
    }

    private void saveWhiteboard(ActionEvent actionEvent) {

        Whiteboard wToSave;
        for(Whiteboard w : whiteboards) {
            if (w.getId().equals(Integer.toString(selectedTab))){

                wToSave = w;
                try{
                    Stage nameStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("save.fxml"));
                    Parent root = loader.load();
                    sc = loader.getController();
                    sc.setWc(this);
                    nameStage.setTitle("Enter name");
                    nameStage.setScene(new Scene(root));
                    nameStage.showAndWait();
                    wToSave.setName(whiteboardName);
                    wRepo.saveWhiteboard(wToSave);

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void addPane(ActionEvent actionEvent) {
        Tab tab = new Tab((Integer.toString(whiteboardPane.getTabs().size() +1)));
        whiteboardPane.getTabs().add(tab);
        Whiteboard board = new Whiteboard(Integer.toString(whiteboardPane.getTabs().size() + 1), Config.getUser().get_id());
        whiteboards.add(board);
    }

    @FXML
    private void handleComboboxAction() throws IOException {
        if (whiteboardItems.getSelectionModel().getSelectedItem() != null)
        {
            if (whiteboardItems.getSelectionModel().getSelectedItem().equals("Text")) {
                try {
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

                    if(userInput != null){
                        Label userText = new Label(userInput.getText());

                        if(userInput.getFont() != null){
                            userText.setFont(Font.font(userInput.getFont()));
                        }

                        makeItemDraggable(userText);

                        addItemToList(userText);
                        addItemToPane(userText);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (whiteboardItems.getSelectionModel().getSelectedItem().equals("Picture")) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select picture");
                FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter(
                        "Pictures", "*.jpg", "*.png", "*.gif");
                fileChooser.getExtensionFilters().add(extensions);
                Picture p = new Picture();

                p.setFile(fileChooser.showOpenDialog(pictureStage));

                if(p.getFile() != null){
                    ImageView pictureView = new ImageView(p.getSelectedImage());
                    makeItemDraggable(pictureView);

                    addItemToList(pictureView, p.getFile());
                    addItemToPane(pictureView);
                }

            } else if (whiteboardItems.getSelectionModel().getSelectedItem().equals("Video")) {

                Stage videoStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Video.fxml"));
                Parent root = loader.load();
                VideoController vc = loader.getController();

                vc.setWhiteboardController(this);
                vc.setVideoStage(videoStage);
                videoStage.setTitle("Enter video Url");
                videoStage.setScene(new Scene(root));
                videoStage.showAndWait();

                if(videoUrl != null){

                    WebView view  = new WebView();
                    view.getEngine().load(videoUrl);
                    view.setPrefWidth(400);
                    view.setPrefHeight(250);

                    makeItemDraggable(view);
                    addItemToList(view);
                    addItemToPane(view);
                }
            }
        }
    }

    private void addItemToList(WebView w) {

        for (Whiteboard board : whiteboards){
            if(board.getId().equals(Integer.toString(selectedTab))){

                String id  = Config.getUser().getName() + board.getId() +
                        Integer.toString(board.getItems().size() + 1) ;
                Video video = new Video(w.getEngine().getLocation(), w.getLayoutX(), w.getLayoutY(),
                        id, w.getWidth(), w.getHeight());
                board.addItem(video);
            }
        }
    }

    private void addItemToList(Label l){

        for (Whiteboard board : whiteboards){
            if(board.getId().equals(Integer.toString(selectedTab))){

                String id  = Config.getUser().getName() + board.getId() +
                        Integer.toString(board.getItems().size() + 1) ;
                Text text = new Text(l.getText(), l.getFont().toString(), id, l.getLayoutX(), l.getLayoutY(),
                        l.getWidth(), l.getHeight());
                board.addItem(text);
            }
        }
    }

    private void addItemToList(ImageView I, File F){

        for (Whiteboard board : whiteboards){
            if(board.getId().equals(Integer.toString(selectedTab))){

                String id  = Config.getUser().getName() + board.getId() +
                        Integer.toString(board.getItems().size() + 1) ;

                try{
                    byte[] imageToByte = Files.readAllBytes(F.toPath());
                    Picture picture = new Picture(imageToByte, id, I.getLayoutX(), I.getLayoutY(),
                            I.getImage().getWidth(), I.getImage().getHeight());
                    board.addItem(picture);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void addItemToPane(Node n){
        Pane pane = new Pane();

        if(whiteboardPane.getSelectionModel().getSelectedItem().getContent() != null){
            pane.getChildren().addAll(whiteboardPane.getSelectionModel().getSelectedItem().getContent());
        }

        pane.getChildren().add(n);
        whiteboardPane.getSelectionModel().getSelectedItem().setContent(pane);
    }

    private  void makeItemDraggable(Node n){

        n.setOnMousePressed(event -> {
            dragDelta.setX(n.getLayoutX() - event.getSceneX());
            dragDelta.setY(n.getLayoutY() - event.getSceneY());
            n.setCursor(Cursor.MOVE);
        });
        n.setOnMouseReleased(e -> n.setCursor(Cursor.HAND));
        n.setOnMouseDragged(e ->{
            n.setLayoutY(e.getSceneY() + dragDelta.getY());
            n.setLayoutX(e.getSceneX() + dragDelta.getX());
        });
        n.setOnMouseEntered(e -> n.setCursor(Cursor.HAND));
    }

}