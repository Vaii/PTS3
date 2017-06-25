package authentication.repository;

import domain.DataSource;
import domain.User;
import org.jongo.MongoCollection;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vai on 5/21/17.
 */
public class LoginMongoContext implements ILoginContext {

    @Override
    public User loginUser(User user) {
        try {
            MongoCollection users = DataSource.connect().getCollection("Users");

            User isMember = users.findOne("{ StudentID:#}", user.getStudentid()).as(User.class);

            if (isMember == null) {
                users.save(user);
                User newMember = users.findOne("{ StudentID:#}", user.getStudentid()).as(User.class);
                return newMember;
            } else {
                return isMember;
            }
        } catch (Exception e) {
            Logger.getLogger(LoginMongoContext.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
