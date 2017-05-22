package git;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML private ListView<GitRepository> lvRepo;
    @FXML private ListView<GitCommit> lvCommit;
    @FXML private CheckBox chkUserPass;
    @FXML private ListView<GitContents> lvContents;
    @FXML private AnchorPane apContent;

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
        git.repositorys.clear();
        try {
            git.getAllRepos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
        lvRepo.getItems().removeAll(lvRepo.getItems());
        lvRepo.getItems().setAll(git.repositorys);

    }

    public void showCommits() {

        lvCommit.getItems().removeAll(lvCommit.getItems());
        try{
            git.getCommits(lvRepo.getSelectionModel().getSelectedItem());
            lvCommit.getItems().setAll(git.commits);
        }
        catch (Exception ex){
            if (ex instanceof NullPointerException){
                JOptionPane.showMessageDialog(null, "No repository selected."," Error",JOptionPane.CANCEL_OPTION);
            }
            else{
                JOptionPane.showMessageDialog(null, ex.getMessage()," Error",JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    public void showContents()
    {
        this.lvContents.getItems().removeAll(lvContents.getItems());
        try{
            git.getContents(lvRepo.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), " Error",JOptionPane.ERROR_MESSAGE);
        }
        this.lvContents.getItems().removeAll(lvContents.getItems());
        this.lvContents.getItems().setAll(git.contents);
    }

    public void showContents(String path){

        this.lvContents.getItems().removeAll(lvContents.getItems());
        try{
            git.getContents(lvRepo.getSelectionModel().getSelectedItem(),path);
            System.out.println("Entering: " + path);
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), " Error",JOptionPane.ERROR_MESSAGE);
        }
        this.lvContents.getItems().removeAll(lvContents.getItems());
        this.lvContents.getItems().setAll(git.contents);
    }

    public void showInfo(){
        showCommits();
        showContents();
        showContentPane();
    }

    public void returnDirectory(){
        if (currentDirectory != null) {
            boolean hasParent = new File(currentDirectory).getParentFile() != null;
            if (hasParent) {
                if (!selectedDir.equals(currentDirectory)){
                    System.out.println("Nope");
                }
                else {
                    //System.out.println("Ervoor: " + selectedDir);
                    showContents((currentDirectory.substring(0,currentDirectory.lastIndexOf('/'))));
                    currentDirectory = selectedDir.substring(0,selectedDir.lastIndexOf('/'));
                    selectedDir = currentDirectory;
                    // System.out.printf("Erna: " + selectedDir);

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
        if (lvContents.getSelectionModel().getSelectedItem().contents.getType().equals(RepositoryContents.TYPE_DIR)) {
            showContents(lvContents.getSelectionModel().getSelectedItem().contents.getPath());
            currentDirectory = lvContents.getItems().get(0).contents.getPath();
        }
        selectedDir = lvContents.getItems().get(0).contents.getPath();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideLogin();
        lvContents.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    if (lvContents.getSelectionModel().getSelectedItem().contents.getType().equals(RepositoryContents.TYPE_DIR)) {
                        showContents(lvContents.getSelectionModel().getSelectedItem().contents.getPath());
                        currentDirectory = lvContents.getItems().get(0).contents.getPath();
                    }
                    selectedDir = lvContents.getItems().get(0).contents.getPath();
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
            apContent.setVisible(false);
            visible = false;
        }
        else {
            apLists.setVisible(false);
            lvRepo.getItems().clear();
            lvCommit.getItems().clear();
            lvContents.getItems().clear();
            visible = true;
        }
        apLogin.setVisible(visible);



    }

    private void showContentPane(){
        apContent.setVisible(true);
    }



}
