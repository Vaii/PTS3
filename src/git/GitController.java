package git;

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

/**
 * Created by Ken on 19-3-2017.
 */
public class GitController implements Initializable {

    @FXML private AnchorPane apLists;
    @FXML private AnchorPane apLogin;
    @FXML private TextField pwfPass;
    @FXML private TextField tbUsername;
    @FXML private ListView lvRepo;
    @FXML private ListView lvCommit;
    @FXML private CheckBox chkUserPass;


    Git git = new Git();


    public void login() throws IOException {
         String username = tbUsername.getText().toString();
         String pwd = pwfPass.getText().toString();
         if (!tbUsername.getText().toString().isEmpty()  && !tbUsername.getText().toString().isEmpty())
         {
            try{
                git.login(username,pwd);
                showRepos();
                hideLogin();
                JOptionPane.showMessageDialog(null,  "Logged in succesfully", " Login", 1);
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), " Error",0);
            }
         }
         else {
             JOptionPane.showMessageDialog(null, "No username or password.", " Error",2);
         }
      }

    public void logout() {
          git.logout();
          hideLogin();
          JOptionPane.showMessageDialog(null,  "Logged out succesfully", " Logout", 1);
      }

    public void showRepos() throws IOException {
          git.repositorys.clear();
          try {
              git.getAllRepos();
          }
          catch (Exception ex){
              JOptionPane.showMessageDialog(null, ex.getMessage()," Error",0);
          }
          lvRepo.getItems().removeAll(lvRepo.getItems());
          lvRepo.getItems().setAll(git.repositorys);

      }

    public void showCommits() throws IOException {

          lvCommit.getItems().removeAll(lvCommit.getItems());
          try{
              git.getCommits((GitRepository) lvRepo.getSelectionModel().getSelectedItem());
          }
          catch (Exception ex){
              if (ex instanceof NullPointerException){
                  JOptionPane.showMessageDialog(null, "No repository selected."," Error",2);
              }
              else{
                  JOptionPane.showMessageDialog(null, ex.getMessage()," Error",0);
              }
          }
          lvCommit.getItems().setAll(git.commits);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideLogin();
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
