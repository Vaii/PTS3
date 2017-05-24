package whiteboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

public class Video extends WhiteboardItem {

    private static final String URL = "Url";
    private static final String XPOS = "xPos";
    private static final String YPOS = "yPos";
    private static final String ITEMID = "itemID";
    private static final String WIDTH = "Width";
    private static final String HEIGHT = "Height";

    private String url;
    private double xPos;
    private double yPos;
    private String id;
    private double width;
    private double height;


    public Video(){

    }

    @JsonCreator
    public Video(@JsonProperty(URL)String url,
                 @JsonProperty(XPOS) double xPos,
                 @JsonProperty(YPOS) double yPos,
                 @JsonProperty(ITEMID) String id,
                 @JsonProperty(WIDTH) double width,
                 @JsonProperty(HEIGHT) double height){
        this.url = url;
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    @JsonProperty
    public String getUrl() {
        return url;
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
    public String getId() {
        return id;
    }

    @JsonProperty
    public double getWidth() {
        return width;
    }

    @JsonProperty
    public double getHeight() {
        return height;
    }
}