package domain;

/**
 * Created by bob on 10-5-17.
 */
import java.io.*;
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

    public static byte[] serializeToBytes(Object object){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] b = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            b = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return b;
    }

    public static Object deserialize(InputStream b) throws IOException {
        Object o = null ;
        ObjectInputStream oos = new ObjectInputStream(b);
        ObjectInput in = null;
        try {

             o = oos.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return o;
    }
}


