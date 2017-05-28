package scheduling.repository;

import scheduling.ScheduleItem;

import java.util.ArrayList;

/**
 * Created by bob on 26-5-17.
 */
public interface IScheduleContext {
    boolean DeleteById(String id);
    boolean DeleteAll(ArrayList<ScheduleItem> messages);
    void Insert(ScheduleItem scheduleItem);
    ArrayList<ScheduleItem> GetAllScheduleItemsByWeek(int week);
    ArrayList<ScheduleItem> GetAllScheduleItemsByWeek(int week, String UserId);
}
