package whiteboard;

/**
 * Created by bob on 10-5-17.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;


public class Picture extends WhiteboardItem {

    private static final String IMAGE = "Image";
    private static final String ITEMID = "ItemID";
    private static final String XPOS = "xPos";
    private static final String YPOS = "yPos";
    private static final String WIDTH = "Width";
    private static final String HEIGHT = "Height";

    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private String id;

    public java.io.File getFile() {
        return File;
    }

    public void setFile(java.io.File file) {
        File = file;
    }


    public Image getSelectedImage() throws MalformedURLException {

        if(File != null){
            String imagePath = File.toURI().toURL().toString();
            selectedImage = new Image(imagePath);
            return selectedImage;
        }
        else{
            return null;
        }
    }

    private byte[] Image;
    private Image selectedImage;

    private File File;

    public Picture(){

    }

    @JsonCreator
    public Picture(@JsonProperty(IMAGE) byte[] Image,
                   @JsonProperty(ITEMID) String id,
                   @JsonProperty(XPOS) double xPos,
                   @JsonProperty(YPOS) double yPos,
                   @JsonProperty(WIDTH) double width,
                   @JsonProperty(HEIGHT) double height){
        this.Image = Image;
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    @JsonProperty
    public double getxPos() {
        return xPos;
    }

    @JsonProperty
    public double getyPos() {
        return yPos;
    }

    @JsonProperty
    public double getWidth() {
        return width;
    }

    @JsonProperty
    public double getHeight() {
        return height;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @Override
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    @Override
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    @JsonProperty
    public byte[] getImage() {
        return Image;
    }
}