package whiteboard.Shared;

import java.io.Serializable;

/**
 * Created by Vai on 5/30/17.
 */
public class DrawEvent implements Serializable {

    private final double xPos;
    private final double yPos;
    private final String text;
    private final String id;

    public DrawEvent(double xPos, double yPos, String text, String id){
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.id = id;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
