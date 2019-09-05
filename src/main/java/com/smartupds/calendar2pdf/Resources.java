package com.smartupds.calendar2pdf;

/** Various common resources
 *
 * @author Yannis Marketakis (marketakis 'at' smartupds 'dot' com)
 */
public class Resources {

    public static final String TIMESTAMP_PATTERN="yyyyMMddHHmmss";
    public static final String UTF_8_ENCODING="UTF-8";

    public static final String ICAL_KEY_BEGIN_VEVENT="BEGIN:VEVENT";
    public static final String ICAL_KEY_END_VEVENT="END:VEVENT";
    public static final String ICAL_KEY_SUMMARY="SUMMARY:";
    public static final String ICAL_KEY_DTSTART="DTSTART:";
    public static final String ICAL_KEY_DTEND="DTEND:";
    public static final String ICAL_KEY_DESCRIPTION="DESCRIPTION:";
    public static final String ICAL_KEY_DTSTAMP="DTSTAMP:";
    public static final String ICAL_KEY_UID="UID:";
    public static final String ICAL_KEY_CREATED="CREATED:";
    public static final String ICAL_KEY_LAST_MODIFIED="LAST-MODIFIED:";
    public static final String ICAL_KEY_LOCATION="LOCATION:";
    public static final String ICAL_KEY_SEQUENCE="SEQUENCE:";
    public static final String ICAL_KEY_STATUS="STATUS:";
    public static final String ICAL_KEY_TRANSP="TRANSP:";

    /* Exporter resources */
    public static final String GREEK_FONT_PATH="resources/arial.ttf";
    public static final int FONT_SIZE=10;
    public static final String PDF_FILENAME_PREFIX="Calendar";
    public static final String PDF_EXTENSION="pdf";
    public static final String PDF_TITLE="";
    public static final String PDF_AUTHOR="Yannis Marketakis";
    public static final String PDF_TABLE_HEADER_FROM_UNTIL="Από - Εως";
    public static final String PDF_TABLE_HEADER_TITLE="Τίτλος";
    public static final String PDF_TABLE_HEADER_DESCRIPTION="Περιγραφή";
}