package com.smartupds.calendar2pdf;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**This class is responsible for controlling the entire process (harvesting calendar entries, creating entry POJOs, exporting them in PDF format).
*
* @author Yannis Marketakis (marketakis 'at' smartupds 'dot' com)
*/
public class Controller {

    public static void main(String[] args) throws IOException, ParseException {
        Importer importer=new Importer(new File("C:/users/marketak/Downloads/calendar.ics"));
        Collection<String> results=importer.tokenizeCalendarEntries();
        System.out.println("Entries as String size: "+results.size());

        Collection<CalendarEntry> calendarEntries=new HashSet<>();
        for(String result : results) {
            CalendarEntry entry = importer.createCalendarEntry(result);
            calendarEntries.add(entry);
        }
        System.out.println("Entries Collection size: "+calendarEntries.size());

        System.out.println("Sorting Calendar Entries");
        Map<Integer,Set<CalendarEntry>> sortedCalendars=Controller.sortCalendarEntries(calendarEntries);

    }

    private static Map<Integer,Set<CalendarEntry>> sortCalendarEntries(Collection<CalendarEntry> calendarEntries){
        Map<Integer,Set<CalendarEntry>> sortedCalendars=new TreeMap<>();
        for(CalendarEntry calEntry : calendarEntries){
            if(sortedCalendars.containsKey(calEntry.getYear())){
                Set<CalendarEntry> yearlyCalEntries=sortedCalendars.get(calEntry.getYear());
                yearlyCalEntries.add(calEntry);
                sortedCalendars.put(calEntry.getYear(),yearlyCalEntries);
            }else{
                Set<CalendarEntry> yearlyCalEntries=new TreeSet<>();
                yearlyCalEntries.add(calEntry);
                sortedCalendars.put(calEntry.getYear(),yearlyCalEntries);
            }
        }
        return sortedCalendars;
    }
}