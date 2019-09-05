package com.smartupds.calendar2pdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import lombok.Data;

/** This is a POJO class modeling entries in the calendar
 * 
 * @author Yannis Marketakis (marketakis 'at' smartupds 'dot' com)
 */
@Data
public class CalendarEntry implements Comparable<CalendarEntry> {
    private String title;
    private String description;
    private Calendar startTime;
    private Calendar endTime;

    public void setStartTime(String startTimestamp) throws ParseException {
        this.startTime=Calendar.getInstance();
        this.startTime.setTime(Utils.parseDateTime(startTimestamp));
    }

    public void setEndTime(String endTimestamp) throws ParseException {
        this.endTime=Calendar.getInstance();
        this.endTime.setTime(Utils.parseDateTime(endTimestamp));
    }

    public int getYear(){
        return this.startTime.get(Calendar.YEAR);
    }

    public String getDateNumeric(){
        return this.startTime.get(Calendar.YEAR)+
                ""+
                (this.startTime.get(Calendar.MONTH)<10?"0"+this.startTime.get(Calendar.MONTH):this.startTime.get(Calendar.MONTH))+
                ""+
                (this.startTime.get(Calendar.DAY_OF_MONTH)<10?"0"+this.startTime.get(Calendar.DAY_OF_MONTH):this.startTime.get(Calendar.DAY_OF_MONTH));
    }

    public String getDatePrinterFriendly() {
        return this.startTime.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("el-GR")) +
                " " +
                this.startTime.get(Calendar.DAY_OF_MONTH) +
                " " +
                this.startTime.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("el-GR")) +
                " " +
                this.startTime.get(Calendar.YEAR);
    }

    public String getTimeDuration(){
        return (this.startTime.get(Calendar.HOUR_OF_DAY)<10?"0"+this.startTime.get(Calendar.HOUR_OF_DAY):""+this.startTime.get(Calendar.HOUR_OF_DAY))+
                ":"+
                (this.startTime.get(Calendar.MINUTE)<10?"0"+this.startTime.get(Calendar.MINUTE):""+this.startTime.get(Calendar.MINUTE))+
                " - "+
                (this.endTime.get(Calendar.HOUR_OF_DAY)<10?"0"+this.endTime.get(Calendar.HOUR_OF_DAY):""+this.endTime.get(Calendar.HOUR_OF_DAY))+
                ":"+
                (this.endTime.get(Calendar.MINUTE)<10?"0"+this.endTime.get(Calendar.MINUTE):""+this.endTime.get(Calendar.MINUTE));
    }

    @Override
    public int compareTo(CalendarEntry anotherCalendarEntry){
        if(this.startTime.equals(anotherCalendarEntry.getStartTime())){
            return this.getTitle().compareTo(anotherCalendarEntry.getTitle());
        }else{
            return this.startTime.compareTo(anotherCalendarEntry.getStartTime());
        }
    }
}
