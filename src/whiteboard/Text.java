package whiteboard;

import javafx.scene.text.Font;

public class Text extends WhiteboardItem {

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

}