package scheduling;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.text.Text;

/**
 * Created by bob on 26-5-17.
 */
public enum ScheduleItemCategory {
    DOCUMENTATION("Documentation"),
    RESEARCH("Research"),
    MEETING("Meeting"),
    PROGRAMMING("Programming"),
    TESTING("Testing"),
    ASSIGNMENT_DEADLINE("Assignment Deadline");

    private ScheduleItemCategory(String ScheduleItemCategoryString) {
    }

    public static class ScheduleItemCategoryConstants {
        public static final String DOCUMENTATION_VALUE = "Documentation";
        public static final String RESEARCH_VALUE = "Research";
        public static final String MEETING_VALUE = "Meeting";
        public static final String PROGRAMMING_VALUE = "Programming";
        public static final String TESTING_VALUE = "Testing";
        public static final String ASSIGNMENT_DEADLINE = "Assignment Deadline";

        public ScheduleItemCategoryConstants() {
        }
    }

    public  class ScheduleItemCategoryIcons {
        public  final Text DOCUMENTATION_ICON = GlyphsDude.createIcon(FontAwesomeIcon.FILE_TEXT_ALT, "1.5em");
        public  final Text RESEARCH_ICON  = GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "1.5em");
        public  final Text MEETING_ICON  = GlyphsDude.createIcon(FontAwesomeIcon.USERS, "1.5em");
        public  final Text PROGRAMMING_ICON  = GlyphsDude.createIcon(FontAwesomeIcon.COGS, "1.5em");
        public  final Text TESTING_ICON  = GlyphsDude.createIcon(FontAwesomeIcon.BUG, "1.5em");
        public  final Text ASSIGNMENT_DEADLINE_ICON  = GlyphsDude.createIcon(FontAwesomeIcon.EXCLAMATION_TRIANGLE, "1.5em");

        public ScheduleItemCategoryIcons() {
        }

        public  Text getIconByCategory(ScheduleItemCategory cat){
            switch (cat) {
                case DOCUMENTATION:  return DOCUMENTATION_ICON;
                case RESEARCH:  return RESEARCH_ICON;
                case MEETING:  return MEETING_ICON;
                case PROGRAMMING:  return PROGRAMMING_ICON;
                case TESTING:  return TESTING_ICON;
                case ASSIGNMENT_DEADLINE :  return ASSIGNMENT_DEADLINE_ICON;
            }
            return null;
        }
    }
}
