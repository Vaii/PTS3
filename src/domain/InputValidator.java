package domain;

/**
 * Created by bob on 10-5-17.
 */
import domain.Utility;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class InputValidator {
    public InputValidator() {
    }

    public static Boolean checkIfDateIsNotNull(Date date) {
        return Boolean.valueOf(false);
    }

    public static Boolean checkForStringInput(List<TextField> text) {
        String error = "";
        Boolean errorCheck = Boolean.valueOf(true);
        Iterator var3 = text.iterator();

        while(var3.hasNext()) {
            TextField item = (TextField)var3.next();
            if(item.getText().trim().isEmpty()) {
                errorCheck = Boolean.valueOf(false);
                error = error + item.getPromptText() + Utility.newline;
                item.setStyle(" -fx-text-box-border: red; -fx-focus-color: red ;");
            }
        }

        if(!errorCheck.booleanValue()) {
            showErrorAlert("Validatiefout", "Je hebt de volgende velden niet (juist) ingevuld:", error);
        }

        System.out.println("Input Error " + errorCheck.toString());
        return errorCheck;
    }

    private static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
