package whiteboard.Shared;

import java.io.Serializable;

/**
 * Created by Vai on 6/22/17.
 */
public class PictureEvent implements Serializable {

    private final double xPos;
    private final double yPos;
    private final String id;
    private final byte[] picture;

    public PictureEvent(double xPos, double yPos, String id, byte[] picture){
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        this.picture = picture;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public String getId() {
        return id;
    }

    public byte[] getPicture() {
        return picture;
    }
}
