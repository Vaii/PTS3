package chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdurmont.emoji.EmojiParser;
import domain.User;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

/**
 * Created by bob on 14-5-17.
 */

public class Message implements java.io.Serializable {

    public static final String MESSAGE = "message";
    public static final String USER = "user";
    public static final String DATE = "date";
    private String message;
    // private User user;
    private Date date;

    // auto
    @MongoObjectId
    private String _id;

    @JsonProperty(MESSAGE)
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty(DATE)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonCreator
    public Message(@JsonProperty(MESSAGE) String message,
                    @JsonProperty(DATE) Date date){
        this.message = EmojiParser.parseToUnicode(message);
        this.date = date;
    }
}
