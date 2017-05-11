package whiteboard;

/**
 * Created by bob on 10-5-17.
 */
public abstract class WhiteboardItem {

    private Point point;
    private double Width;
    private double Height;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}