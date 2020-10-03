package com.example.redesocial.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateHandler implements Comparator<Date> {

    public static long convertToLong (String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Const.LOCALE_BRAZIL);
        Date d = format.parse(date);
        return d.getTime();
    }

    @Override
    public int compare(Date a, Date b) {
        return a.compareTo(b);
    }
}
