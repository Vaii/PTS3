package whiteboard;

/**
 * Created by bob on 10-5-17.
 */
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