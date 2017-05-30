package git;


import domain.Config;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.eclipse.egit.github.core.RepositoryContents;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GitController implements Initializable {

    @FXML private ListView<GitContents> lvContent;
    @FXML private ComboBox<GitRepository> cbRepositorys;
    @FXML private Label lblTitle;
    @FXML private Label lblInfo;

    private String selectedDir;
    private String currentDirectory;
    private Git git;

    public GitController()
    {
        git = new Git();
    }

    public void login() {
        if (Config.getUser().getGithubAuthToken() == null){
            System.out.println("No token set.");
        }
        try {
            if (Config.getUser().getGithubAuthToken().isEmpty()){
                showErrorAlert("No token found, set token in Settings.");
            }
            else {
                git.login();
                showRepositorys();
                setPrimaryRepo("PTS3");
                showListViewInfo();
                showInfoAlert("Logged in successfully");
            }
        }
        catch (IOException ex){
            showErrorAlert("No token found, set token in Settings");
        }
    }

    public void logout() {
        git.logout();
        showInfoAlert("Logged out succesfully");
    }

    private void showRepositorys() throws IOException {
        git.getRepositorys().clear();
        try {
            git.getAllRepos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
            cbRepositorys.getItems().removeAll(cbRepositorys.getItems());
            cbRepositorys.getItems().setAll(git.getRepositorys());
    }

    public void showCommits() {
        lvContent.getItems().removeAll(lvContent.getItems());
        try{
            git.getCommits(cbRepositorys.getSelectionModel().getSelectedItem());
            lvContent.getItems().setAll(git.getCommits());
        }
        catch (Exception ex){
            if (ex instanceof NullPointerException){
                showErrorAlert("No repository selected.");
            }
            else{
                showErrorAlert("Error loading commits.");
            }
        }
        setLabels("Commit");
    }

    public void showContents() {
        this.lvContent.getItems().removeAll(lvContent.getItems());
        try{
            git.getContents(cbRepositorys.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex){
            showErrorAlert("Loading contents error.");
        }
        this.lvContent.getItems().removeAll(lvContent.getItems());
        this.lvContent.getItems().setAll(git.getContents());
        setLabels("Files");
    }

    public void showContents(String path){
        this.lvContent.getItems().removeAll(lvContent.getItems());
        try{
            git.getContents(cbRepositorys.getSelectionModel().getSelectedItem(),path);
            System.out.println("Entering: " + path);
        }
        catch (Exception ex){
            showErrorAlert("Loading contents error.");
        }
        this.lvContent.getItems().removeAll(lvContent.getItems());
        this.lvContent.getItems().setAll(git.getContents());
    }

    public void showListViewInfo(){
        showContents();
        setLabels("");
    }

    public void returnDirectory(){
        if (currentDirectory != null) {
            boolean hasParent = new File(currentDirectory).getParentFile() != null;
            if (hasParent) {
                if (!selectedDir.equals(currentDirectory)){
                    System.out.println("Nope");
                }
                else {
                    showContents((currentDirectory.substring(0,currentDirectory.lastIndexOf('/'))));
                    currentDirectory = selectedDir.substring(0,selectedDir.lastIndexOf('/'));
                    selectedDir = currentDirectory;
                }
            } else {
                showContents();
                System.out.println("Can't go further back.");
            }
        }
        else {
            System.out.println("Nope");
        }
    }

    public void enterDirectory(){
        if (lvContent.getItems().isEmpty()){
            showErrorAlert("No selected item");
        }
        else {
            if (lvContent.getSelectionModel().getSelectedItem().getContents().getType().equals(RepositoryContents.TYPE_DIR)) {
                showContents(lvContent.getSelectionModel().getSelectedItem().getContents().getPath());
                currentDirectory = lvContent.getItems().get(0).getContents().getPath();
            }
            selectedDir = lvContent.getItems().get(0).getContents().getPath();
        }
    }

    public boolean setPrimaryRepo(String primaryRepository) {
        for (GitRepository repo : git.getRepositorys()){
            if (repo.getRepository().getName().equals(primaryRepository)){
                cbRepositorys.getSelectionModel().select(repo);
                System.out.println("Proftaak repository:" + repo.getRepository().getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvContent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    if (lvContent.getSelectionModel().getSelectedItem().getContents().getType().equals(RepositoryContents.TYPE_DIR)) {
                        showContents(lvContent.getSelectionModel().getSelectedItem().getContents().getPath());
                        currentDirectory = lvContent.getItems().get(0).getContents().getPath();
                    }
                    selectedDir = lvContent.getItems().get(0).getContents().getPath();
                }
            }
        });
        if (!(Config.getUser().getGithubAuthToken().isEmpty())){
            login();
        }
        else
        {
            showErrorAlert("No token found, set token in Settings");
        }
    }

    private void setLabels(String selectedContent){
        switch (selectedContent){
            case "Commit":
                lblTitle.setText("Commits:");
                lblInfo.setText("Username - Message");
                break;
            default:
                lblTitle.setText("Content:");
                lblInfo.setText("Type - Name");
        }
    }

    private void showErrorAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR,text,ButtonType.CLOSE);
        alert.setHeaderText("Something went wrong.");
        alert.showAndWait();
    }
    private void showInfoAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,text,ButtonType.CLOSE);
        alert.setHeaderText("Information");
        alert.showAndWait();
    }
}
