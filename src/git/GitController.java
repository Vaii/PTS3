package git;

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
    @FXML private TextField pwfPass;
    @FXML private TextField tbUsername;
    @FXML private TextField tbToken;
    @FXML private ListView<GitContents> lvContent;
    @FXML private CheckBox chkUserPass;
//    @FXML private ListView<GitContents> lvContents;
    @FXML private ComboBox<GitRepository> cbRepos;
    @FXML private GridPane gpCommits;
    @FXML private GridPane gpContents;
    @FXML private Label lblTitle;
    @FXML private Label lblInfo;


    private String selectedPage;
    private String selectedDir;
    private String currentDirectory;
    private Git git;

    public GitController(){
        git = new Git();
    }

    public void login() throws IOException {
        if (chkUserPass.isSelected()){
            String username = tbUsername.getText().toString();
            String pwd = pwfPass.getText().toString();
            if (!tbUsername.getText().toString().isEmpty()  && !pwfPass.getText().toString().isEmpty())
            {
                try{
                    git.login(username,pwd);
                    showRepos();
                    hideLogin();
                    setPrimaryRepo("PTS3");
                    showInfo();
                    JOptionPane.showMessageDialog(null,  "Logged in succesfully with Username & Password", " Login", JOptionPane.NO_OPTION);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), " Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No username or password.", " Error",JOptionPane.CANCEL_OPTION);
            }
        }
        else{
            String token = tbToken.getText().toString();
            if (!token.isEmpty())
            {
                try {
                    git.login(token);
                    showRepos();
                    hideLogin();
                    JOptionPane.showMessageDialog(null,  "Logged in succesfully with Token", " Login", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), " Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No Token.", " Error",JOptionPane.CANCEL_OPTION);
            }
        }
    }

    public void logout() {
        git.logout();
        hideLogin();
        JOptionPane.showMessageDialog(null,  "Logged out succesfully", " Logout", JOptionPane.NO_OPTION);
    }

    private void showRepos() throws IOException {
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

    public void showContents()
    {
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

    public void showInfo(){
        //showCommits();
        showContents();
        showContentPane();
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
        if (lvContent.getSelectionModel().getSelectedItem().contents.getType().equals(RepositoryContents.TYPE_DIR)) {
            showContents(lvContent.getSelectionModel().getSelectedItem().contents.getPath());
            currentDirectory = lvContent.getItems().get(0).contents.getPath();
        }
        selectedDir = lvContent.getItems().get(0).contents.getPath();
    }

    public boolean setPrimaryRepo(String primaryRepository)
    {
        for (GitRepository repo : git.getRepositorys()){
            if (repo.repository.getName().equals(primaryRepository)){
                cbRepos.getSelectionModel().select(repo);
                System.out.println("Proftaak repository:" + repo.repository.getName());
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
                    if (lvContent.getSelectionModel().getSelectedItem().contents.getType().equals(RepositoryContents.TYPE_DIR)) {
                        showContents(lvContent.getSelectionModel().getSelectedItem().contents.getPath());
                        currentDirectory = lvContent.getItems().get(0).contents.getPath();
                    }
                    selectedDir = lvContent.getItems().get(0).contents.getPath();
                }
            }
        });
    }

    private void hideLogin(){
        boolean visible;
        boolean isLoggedIn = git.isLoggedIn;
        if (isLoggedIn){
            tbUsername.setText("");
            pwfPass.setText("");
            apLists.setVisible(true);
            visible = false;
        }
        else {
            apLists.setVisible(false);
            cbRepos.getItems().clear();
            lvContent.getItems().clear();
            visible = true;
        }
        apLogin.setVisible(visible);
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

    private void showContentPane(){
       // apContent.setVisible(true);
    }

}
