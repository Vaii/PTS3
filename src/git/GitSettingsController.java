package git;

import domain.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GitSettingsController implements Initializable {

    @FXML Button btnRemoveToken;
    @FXML Button btnAuthorizeToken;
    @FXML TextField tbToken;
    @FXML TextField tbRepository;

    public GitSettingsController() {
    }

    public void saveToken(){
        if (!tbToken.getText().isEmpty()){
            Config.getUser().setGithubAuthToken(tbToken.getText());
        }
        else {
            JOptionPane.showMessageDialog(null,"No token","Error",JOptionPane.CANCEL_OPTION);
        }
    }
    public void removeToken(){
        Config.getUser().setGithubAuthToken(null);
        //Config.getUser().setMainRepository("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
