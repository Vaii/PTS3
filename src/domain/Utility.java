package domain;

/**
 * Created by bob on 10-5-17.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String newline = System.getProperty("line.separator");

    public Utility() {
    }

    public static String getDDMMYYDate(Date date) {
        DateFormat formatter = new SimpleDateFormat("EEE, dd/MM/yyyy");
        return formatter.format(date);
    }

    public static int parseIntOrZero(String input) {
        int x = 0;

        try {
            x = Integer.parseInt(input);
        } catch (NumberFormatException var3) {
            ;
        }

        return x;
    }
}
