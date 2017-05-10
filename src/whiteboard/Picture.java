package whiteboard;

/**
 * Created by bob on 10-5-17.
 */
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;

public class Picture extends WhiteboardItem {

    public java.io.File getFile() {
        return File;
    }

    public void setFile(java.io.File file) {
        File = file;
    }


    public Image getSelectedImage() throws MalformedURLException {

        if(File != null){
            String imagePath = File.toURI().toURL().toString();
            selectedImage = new Image(imagePath);
            return selectedImage;
        }
        else{
            return null;
        }
    }

    private Image selectedImage;


    private File File;

}