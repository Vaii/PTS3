package phub.Whiteboard;


public abstract class WhiteboardItem {

	private Point point;
	private double Width;
	private double Height;

	abstract void paintUsing(Paintable paintable);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}