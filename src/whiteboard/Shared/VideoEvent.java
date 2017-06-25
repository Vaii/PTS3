package whiteboard.Shared;

import java.io.Serializable;

/**
 * Created by Vai on 6/22/17.
 */
public class VideoEvent implements Serializable {

    private final double xPos;
    private final double yPos;
    private final String videoURL;
    private final String ID;

    public VideoEvent(double xPos, double yPos, String videoURL, String ID){
        this.xPos = xPos;
        this.yPos = yPos;
        this.videoURL = videoURL;
        this.ID = ID;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getID() {
        return ID;
    }
}
