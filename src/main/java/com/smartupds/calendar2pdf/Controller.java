package com.smartupds.calendar2pdf;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;

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
            System.out.println(entry);
        }
        System.out.println("Entries Collection size: "+calendarEntries.size());

    }
}

