package domain;

/**
 * Created by bob on 10-5-17.
 */

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, var3);
        }
        return x;
    }

    public static byte[] serializeToBytes(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] b = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            b = bos.toByteArray();
        } catch (IOException e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return b;
    }

    public static Object deserialize(InputStream b) throws IOException {
        Object o = null;
        ObjectInputStream oos = new ObjectInputStream(b);
        try {
            o = oos.readObject();
        } catch (IOException e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, e);
        }
        return o;
    }
}


