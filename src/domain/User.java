package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by bob on 10-5-17.
 */
public class User implements Serializable {


    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String USERTYPE = "userType";
    private static final String GITHUB = "Github";
    private static final String DROPBOX = "Dropbox";
    private static final String COLOR = "Color";
    private static final String STUDENTID = "StudentID";


    @MongoObjectId
    private String _id;

    private String name;
    private String githubAuthToken;
    private String dropboxAuthToken;
    private String studentid;
    private UserType userType;
    public Color color;
    private static final Random RANDOM = new Random();
    private static final List<Color> VALUES = Collections.unmodifiableList(Arrays.asList((Color[]) Color.values()));


    public User(String name) {
        this.name = name;
    }

    public User(String name, UserType type, String studentid){
        this.name = name;
        this.userType = type;
        this.studentid = studentid;
        this.color = VALUES.get(RANDOM.nextInt(VALUES.size() -1 ));
    }

    @JsonCreator
    public User(@JsonProperty(NAME) String name,
                @JsonProperty(USERTYPE) UserType type,
                @JsonProperty(GITHUB) String githubAuth,
                @JsonProperty(DROPBOX) String dropboxAuth,
                @JsonProperty(COLOR) Color color,
                @JsonProperty(STUDENTID) String studentid){
        this.name = name;
        this.userType = type;
        this.githubAuthToken = githubAuth;
        this.dropboxAuthToken = dropboxAuth;
        this.color = color;
        this.studentid = studentid;
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

    @JsonProperty(USERTYPE)
    public UserType getUserType() {
        return userType;
    }

    @JsonProperty(GITHUB)
    public String getGithubAuthToken() {
        return githubAuthToken;
    }

    public void setGithubAuthToken(String githubAuthToken) {
        this.githubAuthToken = githubAuthToken;
    }

    @JsonProperty(DROPBOX)
    public String getDropboxAuthToken() {
        return dropboxAuthToken;
    }

    @JsonProperty(STUDENTID)
    public String getStudentid() {
        return studentid;
    }

    public void setDropboxAuthToken(String dropboxAuthToken) {
        this.dropboxAuthToken = dropboxAuthToken;
    }

    public String get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", githubAuthToken='" + githubAuthToken + '\'' +
                ", dropboxAuthToken='" + dropboxAuthToken + '\'' +
                ", studentid='" + studentid + '\'' +
                ", userType=" + userType +
                ", color=" + color +
                '}';
    }
}
