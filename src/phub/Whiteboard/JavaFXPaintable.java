package phub.Whiteboard;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Vai on 4/2/17.
 */
public class JavaFXPaintable implements Paintable {

    public GraphicsContext getGraphics() {
        return graphics;
    }

    public void setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
    }

    private GraphicsContext graphics;
    public JavaFXPaintable(GraphicsContext graphics){
        this.graphics = graphics;
    }

    @Override
    public void paint(Text text) {

    }

    @Override
    public void paint(Video video) {

    }

    @Override
    public void paint(Picture picture) {

    }
}
