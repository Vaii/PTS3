package git;

import domain.Config;
import git.repository.GitMongoContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GitSettingsController implements Initializable {

    @FXML Button btnRemoveToken;
    @FXML Button btnAuthorizeToken;
    @FXML TextField tbToken;
    @FXML TextField tbRepository;
    @FXML TextArea taInstructionsToken;
    @FXML TextArea taInstructionsRepo;

    git.repository.GitRepository gitRepo;

    public GitSettingsController() {
        gitRepo = new git.repository.GitRepository(new GitMongoContext());
    }

    public void saveToken(){
        if (!tbToken.getText().isEmpty()){
            Config.getUser().setGithubAuthToken(tbToken.getText());
            gitRepo.AddGitToken(tbToken.getText());
            showInfoAlert("Token set: " + tbToken.getText());
        }
        else {
            showErrorAlert("No token found.");
        }
    }
    public void removeToken(){
        gitRepo.RemoveGitToken();
    }

    public void setMainRepository(){
        if(Config.getUser().getGithubAuthToken() != null){

            if (!tbRepository.getText().isEmpty()){
            Config.getUser().setMainRepository(tbRepository.getText());
            gitRepo.EditMainRepository(tbRepository.getText());
            showInfoAlert("Repository set: " + tbRepository.getText());
            }
            else {
                showErrorAlert("No repository name found.");
            }
        }
        else {

        }
    }

    private void showErrorAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR,text, ButtonType.CLOSE);
        alert.setHeaderText("Something went wrong.");
        alert.showAndWait();
    }
    private void showInfoAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,text,ButtonType.CLOSE);
        alert.setHeaderText("Information");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInstructionsRepo.setEditable(false);
        taInstructionsToken.setEditable(false);
        if (Config.getUser().getGithubAuthToken() == null || Config.getUser().getGithubAuthToken() == "") {
            btnRemoveToken.setDisable(true);
        } else {
            tbToken.setText(Config.getUser().getGithubAuthToken());
            tbToken.setDisable(true);
        }
        if (Config.getUser().getMainRepository() == null || Config.getUser().getGithubAuthToken() == "") {

        }
        else {
            tbRepository.setText(Config.getUser().getMainRepository());
        }
    }
}
