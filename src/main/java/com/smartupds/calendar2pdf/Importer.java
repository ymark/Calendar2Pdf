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
        this.calendarContents=FileUtils.readFileToString(calendarFile, Resources.UTF_8_ENCODING);
    }
    
    protected Collection<String> tokenizeCalendarEntries(){
        Set<String> retCollection=new HashSet<>();
        this.calendarContents=this.calendarContents.substring(this.calendarContents.indexOf(Resources.ICAL_KEY_BEGIN_VEVENT));
        while(true){
            if(this.calendarContents.startsWith(Resources.ICAL_KEY_BEGIN_VEVENT)){
                int endIndex=this.calendarContents.indexOf(Resources.ICAL_KEY_END_VEVENT)+Resources.ICAL_KEY_END_VEVENT.length()+2;
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
        String tempString=calendarEntryString.substring(calendarEntryString.indexOf(Resources.ICAL_KEY_SUMMARY));
        newEntry.setTitle(tempString.substring(0,tempString.indexOf("\n")-1).replace(Resources.ICAL_KEY_SUMMARY,""));
        tempString=calendarEntryString.substring(calendarEntryString.indexOf(Resources.ICAL_KEY_DTSTART));
        newEntry.setStartTime(tempString.substring(0,tempString.indexOf("\n")-1).replace(Resources.ICAL_KEY_DTSTART,"").replace("T",""));
        tempString=calendarEntryString.substring(calendarEntryString.indexOf(Resources.ICAL_KEY_DTEND));
        newEntry.setEndTime(tempString.substring(0,tempString.indexOf("\n")-1).replace(Resources.ICAL_KEY_DTEND,"").replace("T",""));
        String descriptionText="";
        boolean foundDescriptionText=false;
        for(String line : calendarEntryString.split("\n")){
            line=line.trim();
            if(line.equals(Resources.ICAL_KEY_DESCRIPTION)){
                break;
            }else if(line.startsWith(Resources.ICAL_KEY_DESCRIPTION)){
                descriptionText=line.substring(Resources.ICAL_KEY_DESCRIPTION.length(),line.length());
                foundDescriptionText=true;
            }else if(foundDescriptionText){
                if(line.startsWith(Resources.ICAL_KEY_BEGIN_VEVENT) ||
                        line.startsWith(Resources.ICAL_KEY_DTSTART) ||
                        line.startsWith(Resources.ICAL_KEY_DTEND) ||
                        line.startsWith(Resources.ICAL_KEY_DTSTAMP) ||
                        line.startsWith(Resources.ICAL_KEY_UID) ||
                        line.startsWith(Resources.ICAL_KEY_CREATED) ||
                        line.startsWith(Resources.ICAL_KEY_LAST_MODIFIED) ||
                        line.startsWith(Resources.ICAL_KEY_LOCATION) ||
                        line.startsWith(Resources.ICAL_KEY_SEQUENCE) ||
                        line.startsWith(Resources.ICAL_KEY_STATUS) ||
                        line.startsWith(Resources.ICAL_KEY_SUMMARY) ||
                        line.startsWith(Resources.ICAL_KEY_TRANSP) ||
                        line.startsWith(Resources.ICAL_KEY_END_VEVENT)){
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