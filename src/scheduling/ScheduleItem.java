package scheduling;

/**
 * Created by bob on 10-5-17.
 */
import domain.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

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
    public static final String WEEK = "week";
    public static final String CATEGORY = "category";
    private String title;
    private String description;
    private ScheduleItemCategory category;
    private Date deadline;
    private int week;
    private ScheduleItemStatus status;
    private int percentComplete;
    private ArrayList<User> assignedUsers;
    private User user;

    @MongoObjectId
    private String _id;

    @JsonProperty(TITLE)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty(DESCRIPTION)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(CATEGORY)
    public ScheduleItemCategory getCategory() {
        return this.category;
    }

    @JsonProperty(DEADLINE)
    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @JsonProperty(ASSIGNEDUSERS)
    public ArrayList<User> getAssignedUsers() {
        return this.assignedUsers;
    }

    @JsonProperty(STATUS)
    public ScheduleItemStatus getStatus() {
        return this.status;
    }

    public void setStatus(ScheduleItemStatus status) {
        this.status = status;
    }

    @JsonProperty(PERCENTCOMPLETE)
    public int getPercentComplete() {
        return this.percentComplete;
    }

    public void setpercentComplete(int percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String get_id(){ return _id;}

    public int getWeek(){ return week; }

    public String getFormattedStatus() {
        return this.status.equals(ScheduleItemStatus.INPROGRESS)?this.status.toString() + " " + this.percentComplete + "%":this.status.toString();
    }

    public ScheduleItem() {
    }

    @JsonCreator
    public ScheduleItem(@JsonProperty(TITLE) String title, @JsonProperty(DESCRIPTION) String description,
                        @JsonProperty(DEADLINE) Date deadline, @JsonProperty(ASSIGNEDUSERS) ArrayList<User> users,
                        @JsonProperty(STATUS) ScheduleItemStatus status, @JsonProperty(PERCENTCOMPLETE) int percentComplete,
                        @JsonProperty(WEEK) int week, @JsonProperty(CATEGORY) ScheduleItemCategory category) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assignedUsers = users;
        this.status = status;
        this.percentComplete = percentComplete;
        this.week = week;
        this.category = category;
    }
}