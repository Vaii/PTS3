package whiteboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.text.Font;

public class Text extends WhiteboardItem {

    private static final String TEXT = "Text";
    private static final String FONT = "Font";
    private static final String ITEMID = "ItemID";
    private static final String XPOS = "xPos";
    private static final String YPOS = "yPos";
    private static final String WIDTH = "Width";
    private static final String HEIGHT = "Height";

    private String id;
    private double xPos;
    private double yPos;
    private double width;
    private double height;


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    private String Text;

    private String font;

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Text(String text){
        this.Text = text;
    }

    public Text(String text, String font){
        this.Text = text;
        this.font = font;
    }

    @JsonCreator
    public Text(@JsonProperty(TEXT) String Text,
                @JsonProperty(FONT) String font,
                @JsonProperty(ITEMID) String id,
                @JsonProperty(XPOS) double xPos,
                @JsonProperty(YPOS) double yPos,
                @JsonProperty(WIDTH) double width,
                @JsonProperty(HEIGHT) double height){
        this.Text = Text;
        this.font = font;
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
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
}