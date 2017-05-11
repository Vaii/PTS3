package scheduling;

/**
 * Created by bob on 10-5-17.
 */
import domain.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleItem {
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DEADLINE = "deadline";
    public static final String STATUS = "status";
    public static final String ASSIGNEDUSERS = "assignedUsers";
    public static final String USER = "user";
    public static final String PERCENTCOMPLETE = "percentComplete";
    private String title;
    private String description;
    private Date deadline;
    private ScheduleItemStatus status;
    private int percentComplete;
    private ArrayList<User> assignedUsers;
    private User user;

    @JsonProperty("title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("deadline")
    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @JsonProperty("assignedUsers")
    public ArrayList<User> getAssignedUsers() {
        return this.assignedUsers;
    }

    @JsonProperty("status")
    public ScheduleItemStatus getStatus() {
        return this.status;
    }

    public void setStatus(ScheduleItemStatus status) {
        this.status = status;
    }

    @JsonProperty("percentComplete")
    public int getPercentComplete() {
        return this.percentComplete;
    }

    public void setpercentComplete(int percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String getFormattedStatus() {
        return this.status.equals(ScheduleItemStatus.INPROGRESS)?this.status.toString() + " " + this.percentComplete + "%":this.status.toString();
    }

    public ScheduleItem() {
    }

    @JsonCreator
    public ScheduleItem(@JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("deadline") Date deadline, @JsonProperty("assignedUsers") ArrayList<User> users, @JsonProperty("status") ScheduleItemStatus status, @JsonProperty("percentComplete") int percentComplete) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assignedUsers = users;
        this.status = status;
        this.percentComplete = percentComplete;
    }
}