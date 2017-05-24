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
    private static final String ITEMS = "Items";

    @MongoObjectId
    private String _wId;

    private String id;
    private String userID;
    private String Name;
    private ArrayList<WhiteboardItem> items;

    public Whiteboard(String id, String userID){
        this.id = id;
        this.userID = userID;
        items = new ArrayList<>();
    }

    @JsonCreator
    public Whiteboard(@JsonProperty(ID) String id,
                      @JsonProperty(USERID) String userID,
                      @JsonProperty(NAME) String Name,
                      @JsonProperty(ITEMS) ArrayList<WhiteboardItem> items){
        this.id = id;
        this.userID = userID;
        this.Name = Name;
        this.items = items;
    }

    public void addItem(WhiteboardItem Item){
        items.add(Item);
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

    @JsonProperty
    public ArrayList<WhiteboardItem> getItems() {
        return items;
    }
}