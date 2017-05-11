package scheduling;

/**
 * Created by bob on 10-5-17.
 */
public enum ScheduleItemStatus {
    BACKLOG("Backlog"),
    TODO("To Do"),
    INPROGRESS("In Progress"),
    INREVIEW("In Review"),
    DONE("Done");

    private ScheduleItemStatus(String ScheduleItemStatusString) {
    }

    public static class ScheduleItemStatusConstants {
        public static final String BACKLOG_VALUE = "Backlog";
        public static final String TODO_VALUE = "To Do";
        public static final String INPROGRESS_VALUE = "In Progress";
        public static final String INREVIEW_VALUE = "In Review";
        public static final String DONE_VALUE = "Done";

        public ScheduleItemStatusConstants() {
        }
    }
}
