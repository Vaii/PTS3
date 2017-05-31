package domain;

/**
 * Created by bob on 10-5-17.
 */

public enum Color {
    RED("Red"),
    SLATEBLUE("Slateblue"),
    CRIMSON("Crimson"),
    FORESTGREEN("ForestGreen"),
    MAGENTA("Magenta");

    private Color(String colorString) {
    }

    public static class ColorConstants {
        public static final String RED_VALUE = "Red";
        public static final String SLATEBLUE_VALUE = "Slateblue";
        public static final String CRIMSON_VALUE = "Crimson";
        public static final String FORESTGREEN_VALUE = "ForestGreen";
        public static final String MAGENTA_VALUE = "Magenta";

        public ColorConstants() {
        }
    }

    public static class WebColors{

        public static String getWebColor(Color color){
            switch (color) {
                case RED:  return "#FF0000";
                case CRIMSON:  return "#DC143C";
                case SLATEBLUE:  return "#ADD8E6";
                case FORESTGREEN:  return "#228B22";
                case MAGENTA:  return "#FF00FF";
            }
            return null;

        }
    }
}