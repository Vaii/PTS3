package git;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
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
    @FXML private ListView lvCommit;
    @FXML private CheckBox chkUserPass;

    private ObservableList<GitRepository> gitRepoObservableList;
    private Git git = new Git();

    public GitController(){
        gitRepoObservableList = FXCollections.observableArrayList();

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
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage()," Error",JOptionPane.ERROR_MESSAGE);
        }
       // lvRepo.getItems().removeAll(lvRepo.getItems());
      //  lvRepo.getItems().setAll(git.repositorys);
        loadRepos(git);

    }

    public void loadRepos(Git git) throws IOException {
        gitRepoObservableList.clear();
        gitRepoObservableList.addAll(git.repositorys);
    }
    public void showCommits() throws IOException {

        lvCommit.getItems().removeAll(lvCommit.getItems());
        try{
            git.getCommits((GitRepository) lvRepo.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex){
            if (ex instanceof NullPointerException){
                JOptionPane.showMessageDialog(null, "No repository selected."," Error",JOptionPane.CANCEL_OPTION);
            }
            else{
                JOptionPane.showMessageDialog(null, ex.getMessage()," Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        lvCommit.getItems().setAll(git.commits);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideLogin();
        this.lvRepo.setItems(gitRepoObservableList);
        this.lvRepo.setCellFactory((GitRepoListView) -> {
            return new GitRepoListViewCellController();
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
            lvRepo.getItems().clear();
            lvCommit.getItems().clear();
            visible = true;
        }
        apLogin.setVisible(visible);



    }



}
