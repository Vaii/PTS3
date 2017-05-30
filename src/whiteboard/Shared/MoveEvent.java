package whiteboard.Shared;

import java.io.Serializable;

/**
 * Created by Vai on 5/30/17.
 */
public class MoveEvent implements Serializable {

    private final double xPos;
    private final double yPos;
    private final String objectID;

    public MoveEvent(double xPos, double yPos, String objectID){
        this.xPos = xPos;
        this.yPos = yPos;
        this.objectID = objectID;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public String getObjectID() {
        return objectID;
    }
}
