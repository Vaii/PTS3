package whiteboard;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by bob on 10-5-17.
 */

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property = "_class")
public abstract class WhiteboardItem {


    private double Width;
    private String id;
    private double Height;
    private double xPos;
    private double yPos;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getId() {
        return id;
    }

    public abstract void setxPos(double xPos);

    public abstract void setyPos(double yPos);
}