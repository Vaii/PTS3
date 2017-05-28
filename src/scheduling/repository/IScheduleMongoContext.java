package scheduling.repository;

import domain.DataSource;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import scheduling.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bob on 26-5-17.
 */
public class IScheduleMongoContext implements IScheduleContext {

    private MongoCollection schedule;

    public IScheduleMongoContext(){
        schedule = DataSource.connect().getCollection("schedule");
    }

    @Override
    public boolean DeleteById(int id) {
        return false;
    }

    @Override
    public boolean DeleteAll(ArrayList<ScheduleItem> messages) {
        return false;
    }

    @Override
    public void Insert(ScheduleItem scheduleItem) {
        schedule.save(scheduleItem);
    }

    @Override
    public ArrayList<ScheduleItem> GetAllScheduleItemsByWeek(int week) {
        MongoCursor<ScheduleItem> scheduleItems = schedule.find("{week:"+ week + "}").as(ScheduleItem.class);
        ArrayList<ScheduleItem> result = new ArrayList<ScheduleItem>();
        while (scheduleItems.hasNext()) {
            result.add(scheduleItems.next());
        }
        return result;
    }

    @Override
    public ArrayList<ScheduleItem> GetAllScheduleItemsByWeek(int week, String UserId) {
        MongoCursor<ScheduleItem> scheduleItems = schedule.find( "{ assignedUsers: { $elemMatch: { name: 'Bob' } } }" ).as(ScheduleItem.class);

        ArrayList<ScheduleItem> result = new ArrayList<ScheduleItem>();
        while (scheduleItems.hasNext()) {
            result.add(scheduleItems.next());
        }
        return result;
    }
}