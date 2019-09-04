package com.smartupds.calendar2pdf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/** This is a POJO class modeling entries in the calendar
 * 
 * @author Yannis Marketakis (marketakis 'at' smartupds 'dot' com)
 */
@Data
public class CalendarEntry {
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;

    public void setStartTime(String startDate) throws ParseException {
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        this.startTime=format.parse(startDate);
    }

    public void setEndTime(String endDate) throws ParseException {
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        this.endTime=format.parse(endDate);
    }
}
