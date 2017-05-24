package domain;

/**
 * Created by Vai on 5/22/17.
 */
public class Config {

    public static User getUser() {
        return User;
    }

    public static void setUser(User user) {
        User = user;
    }

    private static User User;

}
