package scheduling;

/**
 * Created by bob on 10-5-17.
 */
import java.util.ArrayList;

public class ScheduleSection {
    private int weekNumber;
    private ArrayList<ScheduleItem> items;

    public ScheduleSection() {
    }

    public int getWeekNumber() {
        return this.weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public ArrayList<ScheduleItem> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<ScheduleItem> items) {
        this.items = items;
    }
}
