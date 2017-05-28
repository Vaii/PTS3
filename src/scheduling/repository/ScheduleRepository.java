package scheduling.repository;

import scheduling.ScheduleItem;

import java.util.ArrayList;

/**
 * Created by bob on 26-5-17.
 */
public class ScheduleRepository {

    private IScheduleContext context;

    public ScheduleRepository(IScheduleContext context){
        this.context = context;
    }

    public ArrayList<ScheduleItem> GetAllScheduleItemsByWeek(int week){
        return context.GetAllScheduleItemsByWeek(week);
    }

    public ArrayList<ScheduleItem> GetAllScheduleItemsByWeek(int week, String userID){
        return context.GetAllScheduleItemsByWeek(week, userID);
    }

    public boolean DeleteById(String id){return context.DeleteById(id);}

    public void Insert(ScheduleItem scheduleItem){
        context.Insert(scheduleItem);
    }
}
