package com.smartupds.calendar2pdf;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/** This class is responsible for importing calendar entries. More specifically 
 * it uses as input a calendar file (ICS), tokenizes the different calendar entries 
 * and harvest the corresponding information. 
 * 
 * @author Yannis Marketakis (marketakis 'at' smartupds 'dot' com)
 */
public class Importer {
    private String calendarContents;
    
    public Importer(File calendarFile) throws IOException{
        this.calendarContents=FileUtils.readFileToString(calendarFile, "UTF-8");
    }
    
    protected Collection<String> tokenizeCalendarEntries(){
        Set<String> retCollection=new HashSet<>();
        this.calendarContents=this.calendarContents.substring(this.calendarContents.indexOf("BEGIN:VEVENT"));
        while(true){
            if(this.calendarContents.startsWith("BEGIN:VEVENT")){
                int endIndex=this.calendarContents.indexOf("END:VEVENT")+"END:VEVENT".length()+2;
                retCollection.add(this.calendarContents.substring(0,endIndex));
                this.calendarContents=this.calendarContents.substring(endIndex);
            }else{
                break;
            }
        }
        return retCollection;
    }

    protected CalendarEntry createCalendarEntry(String calendarEntryString) throws ParseException {
        CalendarEntry newEntry=new CalendarEntry();
        String tempString=calendarEntryString.substring(calendarEntryString.indexOf("SUMMARY:"));
        newEntry.setTitle(tempString.substring(0,tempString.indexOf("\n")-1).replace("SUMMARY:",""));
        tempString=calendarEntryString.substring(calendarEntryString.indexOf("DTSTART:"));
        newEntry.setStartTime(tempString.substring(0,tempString.indexOf("\n")-1).replace("DTSTART:","").replace("T","").replace("Z",""));
        tempString=calendarEntryString.substring(calendarEntryString.indexOf("DTEND:"));
        newEntry.setEndTime(tempString.substring(0,tempString.indexOf("\n")-1).replace("DTEND:","").replace("T","").replace("Z",""));
        String descriptionText="";
        boolean foundDescriptionText=false;
        for(String line : calendarEntryString.split("\n")){
            line=line.trim();
            if(line.equals("DESCRIPTION:")){
                break;
            }else if(line.startsWith("DESCRIPTION:")){
                descriptionText=line.substring("DESCRIPTION:".length(),line.length());
                foundDescriptionText=true;
            }else if(foundDescriptionText){
                if(line.startsWith("BEGIN:VEVENT") ||
                        line.startsWith("DTSTART:") ||
                        line.startsWith("DTEND:") ||
                        line.startsWith("DTSTAMP:") ||
                        line.startsWith("UID:") ||
                        line.startsWith("CREATED:") ||
                        line.startsWith("LAST-MODIFIED:") ||
                        line.startsWith("LOCATION:") ||
                        line.startsWith("SEQUENCE:") ||
                        line.startsWith("STATUS:") ||
                        line.startsWith("SUMMARY:") ||
                        line.startsWith("TRANSP:") ||
                        line.startsWith("END:VEVENT")){
                    break;
                }else{
                    descriptionText+=" "+line.substring(0,line.length());
                }
            }
        }
        newEntry.setDescription(descriptionText);
        return newEntry;
    }
}