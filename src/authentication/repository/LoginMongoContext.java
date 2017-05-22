package authentication.repository;

import domain.DataSource;
import domain.User;
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
            users.save(user);
            return true;
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
