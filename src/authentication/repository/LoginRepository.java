package authentication.repository;

import domain.User;

/**
 * Created by Vai on 5/21/17.
 */
public class LoginRepository {

    private ILoginContext loginContext;

    public LoginRepository(ILoginContext loginContext){
        this.loginContext = loginContext;
    }

    public boolean registerUser(User user){

        if(loginContext.registerUser(user)){
            return true;
        }
        else{
            return false;
        }

    }

    public User loginuser(String username, String password){
        return loginContext.loginUser(username, password);
    }
}
