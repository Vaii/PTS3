package authentication.repository;

import domain.User;
import org.json.JSONObject;

/**
 * Created by Vai on 5/21/17.
 */
public interface ILoginContext {

    User loginUser(User user);
}
