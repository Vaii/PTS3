package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by bob on 10-5-17.
 */
public class User {


    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String USERTYPE = "userType";

    @MongoObjectId
    private String _id;
    private String name;
    private String password;
    private String className;
    private UserType userType;
    private int attribute;
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

    public String toString() {
        return this.name;
    }

    @JsonProperty(USERTYPE)
    public UserType getUserType() {
        return userType;
    }

    @JsonProperty(PASSWORD)
    public String getPassword() {
        return password;
    }



}
