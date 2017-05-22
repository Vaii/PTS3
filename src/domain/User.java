package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.Serializable;

/**
 * Created by bob on 10-5-17.
 */
public class User implements Serializable {


    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String USERTYPE = "userType";

    @MongoObjectId
    private String _id;
    private String name;
    private String password;
    private String githubAuthToken;
    private String dropboxAuthToken;
    private UserType userType;
    public Color color;

    public User(String name) {
        this.name = name;
    }

    @JsonCreator
    public User(@JsonProperty(NAME) String name,
                @JsonProperty(PASSWORD) String password,
                @JsonProperty(USERTYPE) UserType type){
        this.name = name;
        this.password = password;
        this.userType = type;
    }

    public User(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @JsonProperty(NAME)
    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", color=" + color +
                '}';
    }

    @JsonProperty(USERTYPE)
    public UserType getUserType() {
        return userType;
    }

    @JsonProperty(PASSWORD)
    public String getPassword() {
        return password;
    }

    public String getGithubAuthToken() {
        return githubAuthToken;
    }

    public void setGithubAuthToken(String githubAuthToken) {
        this.githubAuthToken = githubAuthToken;
    }

    public String getDropboxAuthToken() {
        return dropboxAuthToken;
    }

    public void setDropboxAuthToken(String dropboxAuthToken) {
        this.dropboxAuthToken = dropboxAuthToken;
    }
}
