package com.smartupds.calendar2pdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public void setStartTime(String startDate) throws ParseException {
        DateFormat format=new SimpleDateFormat(Resources.TIMESTAMP_PATTERN);
        this.startTime=Calendar.getInstance();
        this.startTime.setTime(format.parse(startDate));
    }

    public void setEndTime(String endDate) throws ParseException {
        DateFormat format=new SimpleDateFormat(Resources.TIMESTAMP_PATTERN);
        this.endTime=Calendar.getInstance();
        this.endTime.setTime(format.parse(endDate));
    }

    public int getYear(){
        return this.startTime.get(Calendar.YEAR);
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
