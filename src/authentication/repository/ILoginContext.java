package authentication.repository;

import domain.User;

/**
 * Created by Vai on 5/21/17.
 */
public interface ILoginContext {

    boolean deleteUser(User user);
    boolean registerUser(User user);
    User loginUser(String username, String password);
}
