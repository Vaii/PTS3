package authentication.repository;

import domain.DataSource;
import domain.User;
import javafx.scene.control.Alert;
import org.jongo.MongoCollection;

/**
 * Created by Vai on 5/21/17.
 */
public class LoginMongoContext implements ILoginContext {

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean registerUser(User user) {
        try{
            MongoCollection users = DataSource.connect().getCollection("Users");

            User exists = users.findOne("{name:#}", user.getName()).as(User.class);

            if(exists == null){
                users.save(user);
                return true;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Account bestaat al");
                alert.setHeaderText("Er bestaat al een account met deze accountnaam");
                alert.setContentText("Vul alstublieft een ander accountnaam in");
                alert.showAndWait();
                return false;
            }

        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public User loginUser(String username, String password) {
        try{
            MongoCollection users = DataSource.connect().getCollection("Users");
            User currentUser = users.findOne("{name:#, password:#}", username, password).as(User.class);
            return currentUser;
        }
        catch(Exception e){
            return null;
        }
    }
}
