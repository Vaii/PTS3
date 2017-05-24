package whiteboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Whiteboard implements Serializable {

    private static final String ID = "ID";
    private static final String USERID = "UserID";
    private static final String NAME = "Name";

    @MongoObjectId
    private String _wId;

    private String id;
    private String userID;
    private String Name;
    private ArrayList<Node> items;

    public Whiteboard(String id, String userID){
        this.id = id;
        this.userID = userID;
        items = new ArrayList<>();
    }

    @JsonCreator
    public Whiteboard(@JsonProperty(ID) String id,
                      @JsonProperty(USERID) String userID,
                      @JsonProperty(NAME) String Name){
        this.id = id;
        this.userID = userID;
        this.Name = Name;
    }

    public void addItem(Node n){
        items.add(n);
    }

    @JsonProperty(ID)
    public String getId() {
        return id;
    }

    @JsonProperty(USERID)
    public String getUserID() {
        return userID;
    }

    @JsonProperty(NAME)
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Node> getItems() {
        return items;
    }
}