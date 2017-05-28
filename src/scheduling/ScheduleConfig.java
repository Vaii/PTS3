package scheduling;

/**
 * Created by bob on 10-5-17.
 */
import domain.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleConfig {
    private static ScheduleConfig instance;
    private ObservableList<ScheduleItem> scheduleItemObservableList;
    private ObservableList<User> usersObservableList;
    private ScheduleItem selectedScheduleItem;
    public BooleanProperty updateSchedule = new SimpleBooleanProperty();

    public ScheduleConfig() {
    }

    public static ScheduleConfig getInstance() {
        if(instance == null) {
            Class var0 = ScheduleConfig.class;
            synchronized(ScheduleConfig.class) {
                if(instance == null) {
                    instance = new ScheduleConfig();
                    instance.init();
                }
            }
        }

        return instance;
    }

    public ObservableList<ScheduleItem> getScheduleItemObservableList() {
        return this.scheduleItemObservableList;
    }

    public ObservableList<User> getUsersObservableList() {
        return this.usersObservableList;
    }

    public void addScheduleItemObservableList(ScheduleItem item) {
        this.scheduleItemObservableList.add(item);
    }

    public void setSelectedScheduleItem(ScheduleItem scheduleItem) {
        this.selectedScheduleItem = scheduleItem;
    }

    public ScheduleItem getsSelectedScheduleItem() {
        return this.selectedScheduleItem;
    }

    public void init() {
        this.scheduleItemObservableList = FXCollections.observableArrayList();
        this.usersObservableList = FXCollections.observableArrayList();
        this.usersObservableList.addAll(new User[]{new User("Bob", Color.SLATEBLUE), new User("Vaii", Color.CRIMSON), new User("Ken", Color.MAGENTA), new User("Wei-qiang", Color.FORESTGREEN)});
        ArrayList<User> users = new ArrayList();
        users.add(new User("Bob", Color.SLATEBLUE));
        users.add(new User("Vaii", Color.CRIMSON));
        Calendar cal = Calendar.getInstance();
        cal.set(1, 2017);
        cal.set(2, 7);
        cal.set(5, 1);
        Date dateRepresentation = cal.getTime();
        // this.scheduleItemObservableList.addAll(new ScheduleItem[]{new ScheduleItem("Klassendiagram", "Eerste versie, voor controle door tutor.", dateRepresentation, users, ScheduleItemStatus.TODO, 0), new ScheduleItem("AcceptatieTestplan", "Eerste versie, voor controle door tutor.", dateRepresentation, users, ScheduleItemStatus.INPROGRESS, 0)});
         this.updateSchedule.setValue(false);
    }
}

