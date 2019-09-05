package com.smartupds.calendar2pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Map;

/** This class is responsible for exporting the calendar entries in PDF format
 *
 * @author Yannis Marketakis (marketakis 'at' smartupds 'dot' com)
 */
public class Exporter {
    private Font greekFont;
    private Font greekFontBold;


    public Exporter() throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont(Resources.GREEK_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        baseFont.setSubset(true);
        this.greekFont = new Font(baseFont, Resources.FONT_SIZE, Font.NORMAL);
        this.greekFontBold = new Font(baseFont, Resources.FONT_SIZE, Font.BOLD);
    }

    public void createPdfWithCalendarEntries(int year,Collection<CalendarEntry> calendarEntries) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Resources.PDF_FILENAME_PREFIX+"-"+year+"."+Resources.PDF_EXTENSION));
        document.setPageSize(PageSize.A4);
        document.setMargins(40, 40, 40, 40);
        document.setMarginMirroring(true);

        document.open();
        document.addTitle(Resources.PDF_TITLE+" - "+year);
        document.addAuthor(Resources.PDF_AUTHOR);
        document.addCreator(Resources.PDF_AUTHOR);

        Map<String,Paragraph> paragraphs=new TreeMap<>();
        for(CalendarEntry calendarEntry : calendarEntries) {
            if(paragraphs.containsKey(calendarEntry.getDateNumeric())){
                Paragraph paragraph=paragraphs.get(calendarEntry.getDateNumeric());
                PdfPTable table=(PdfPTable)paragraph.get(1);
                // Add Table contents
                table.addCell(this.getTableBodyCell(calendarEntry.getTimeDuration()));
                table.addCell(this.getTableBodyCell(calendarEntry.getTitle()));
                table.addCell(this.getTableBodyCell(calendarEntry.getDescription()));
                paragraphs.put(calendarEntry.getDateNumeric(),paragraph);
            }else{
                Paragraph paragraph=new Paragraph();
                paragraph.add(this.getHeading(calendarEntry.getDatePrinterFriendly()));

                // Add Table Headers
                PdfPTable table = new PdfPTable(3);
                table.setWidths(new float[]{2.2f,5.0f,7.5f});
                table.setWidthPercentage(100);
                table.addCell(this.getTableHeaderCell(Resources.PDF_TABLE_HEADER_FROM_UNTIL));
                table.addCell(this.getTableHeaderCell(Resources.PDF_TABLE_HEADER_TITLE));
                table.addCell(this.getTableHeaderCell(Resources.PDF_TABLE_HEADER_DESCRIPTION));

                // Add Table contents
                table.addCell(this.getTableBodyCell(calendarEntry.getTimeDuration()));
                table.addCell(this.getTableBodyCell(calendarEntry.getTitle()));
                table.addCell(this.getTableBodyCell(calendarEntry.getDescription()));
                paragraph.add(table);
                paragraphs.put(calendarEntry.getDateNumeric(),paragraph);
            }
        }
        // Export Contents in PDF
        for(String dateNumeric : paragraphs.keySet()){
            Paragraph paragraph=paragraphs.get(dateNumeric);
            document.add(paragraph);
            document.add( Chunk.NEWLINE );

        }

        document.close();
    }

    private Element getHeading(String headingString){
        return new Phrase(headingString, this.greekFontBold);
    }

    private PdfPCell getTableHeaderCell(String content){
        PdfPCell cellGenre = new PdfPCell(new Phrase(content, this.greekFont));
        cellGenre.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cellGenre;
    }

    private PdfPCell getTableBodyCell(String content){
        PdfPCell cellGenre = new PdfPCell(new Phrase(content, this.greekFont));
        return cellGenre;
    }
}