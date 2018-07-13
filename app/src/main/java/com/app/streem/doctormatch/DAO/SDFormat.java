package com.app.streem.doctormatch.DAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SDFormat {
    static SimpleDateFormat sdata = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));
    static SimpleDateFormat shora = new SimpleDateFormat("HH:mm", new Locale("pt","BR"));
    static Calendar c = Calendar.getInstance();

    public static String miliToDate(String dt){
        Date d = new Date(Long.valueOf(dt));
        String result = sdata.format(d);

        return result;
    }

    public static String dateToMili(String dt){
        Date d = null;
        try {
            d = sdata.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(d.getTime());
    }

    public static String dateToString(Date dt){
        String result = sdata.format(dt);

        return result;
    }

    public static String horaToMili(String h, String m){
        c.set(1993,7,10, Integer.valueOf(h), Integer.valueOf(m));
        Date d = c.getTime();

        return String.valueOf(d.getTime());
    }

    public static String miliToHora(String dt){
        Date d = null;
        try {
            d = shora.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(d.getTime());
    }
}
