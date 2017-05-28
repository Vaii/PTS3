package git;

import domain.Config;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.eclipse.egit.github.core.RepositoryContents;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GitController implements Initializable {

    @FXML private AnchorPane apLists;
    @FXML private AnchorPane apLogin;
    @FXML private ListView<GitContents> lvContent;
    @FXML private ComboBox<GitRepository> cbRepos;
    @FXML private GridPane gpContents;
    @FXML private Label lblTitle;
    @FXML private Label lblInfo;
    @FXML private TextField tbToken;
    @FXML private Button btnLogout;

    private String selectedDir;
    private String currentDirectory;
    private Git git;

    public GitController()
    {
        git = new Git();
    }

    public void login() {
        try {
            if (Config.getUser().getGithubAuthToken() == null){
               Config.getUser().setGithubAuthToken(tbToken.getText());
            }
            git.login();
            showRepositorys();
        }
        catch (IOException ex){
            JOptionPane.showMessageDialog(null,"Login failed, check settings","Login",JOptionPane.ERROR_MESSAGE);

        }
        hideLogin();

        setPrimaryRepo("PTS3");
        showListViewInfo();
        JOptionPane.showMessageDialog(null, "Logged in" , "Login", JOptionPane.NO_OPTION);

    }

    public void logout() {
        git.logout();
        hideLogin();
        JOptionPane.showMessageDialog(null,  "Logged out succesfully", " Logout", JOptionPane.NO_OPTION);
    }

    private void showRepositorys() throws IOException {
        git.getRepositorys().clear();
        try {
            git.getAllRepos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
        cbRepos.getItems().removeAll(cbRepos.getItems());
        cbRepos.getItems().setAll(git.getRepositorys());

    }

    public void showCommits() {
        lvContent.getItems().removeAll(lvContent.getItems());
        try{
            git.getCommits(cbRepos.getSelectionModel().getSelectedItem());
            lvContent.getItems().setAll(git.getCommits());
        }
        catch (Exception ex){
            if (ex instanceof NullPointerException){
                JOptionPane.showMessageDialog(null, "No repository selected."," Error",JOptionPane.CANCEL_OPTION);
            }
            else{
                JOptionPane.showMessageDialog(null, ex.getMessage()," Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        setLabels("Commit");
    }

    public void showContents() {
        this.lvContent.getItems().removeAll(lvContent.getItems());
        try{
            git.getContents(cbRepos.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), " Error",JOptionPane.ERROR_MESSAGE);
        }
        this.lvContent.getItems().removeAll(lvContent.getItems());
        this.lvContent.getItems().setAll(git.getContents());
        setLabels("Files");
    }

    public void showContents(String path){
        this.lvContent.getItems().removeAll(lvContent.getItems());
        try{
            git.getContents(cbRepos.getSelectionModel().getSelectedItem(),path);
            System.out.println("Entering: " + path);
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), " Error",JOptionPane.ERROR_MESSAGE);
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
        if (lvContent.getSelectionModel().getSelectedItem().getContents().getType().equals(RepositoryContents.TYPE_DIR)) {
            showContents(lvContent.getSelectionModel().getSelectedItem().getContents().getPath());
            currentDirectory = lvContent.getItems().get(0).getContents().getPath();
        }
        selectedDir = lvContent.getItems().get(0).getContents().getPath();
    }

    public boolean setPrimaryRepo(String primaryRepository) {
        for (GitRepository repo : git.getRepositorys()){
            if (repo.getRepository().getName().equals(primaryRepository)){
                cbRepos.getSelectionModel().select(repo);
                System.out.println("Proftaak repository:" + repo.getRepository().getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideLogin();

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
    }

    private void hideLogin(){
        btnLogout.setVisible(false);
        if (git.isLoggedIn){
            apLists.setVisible(true);
            apLogin.setVisible(false);
        }
        else {
            apLists.setVisible(false);
            cbRepos.getItems().clear();
            lvContent.getItems().clear();
            apLogin.setVisible(true);
        }
    }

    private void setLabels(String selectedContent){
        switch (selectedContent){
            case "Commit":
                lblTitle.setText("Commits:");
                lblInfo.setText("Username - Message");
                gpContents.setVisible(false);
                break;
            default:
                lblTitle.setText("Content:");
                lblInfo.setText("Type - Name");
                gpContents.setVisible(true);
        }

    }

}
