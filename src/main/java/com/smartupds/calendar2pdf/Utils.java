package com.smartupds.calendar2pdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static Date parseDateTime(String timestamp) throws ParseException {
        Calendar calendar=Calendar.getInstance();
        DateFormat format=new SimpleDateFormat(Resources.TIMESTAMP_PATTERN);
        calendar.setTime(format.parse(timestamp));
        if(TimeZone.getDefault().inDaylightTime(calendar.getTime())){
            calendar.add(Calendar.HOUR,3);
        }else{
            calendar.add(Calendar.HOUR,2);
        }
        return calendar.getTime();
    }
}