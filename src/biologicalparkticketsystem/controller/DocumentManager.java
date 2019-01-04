package biologicalparkticketsystem.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentManager {
    
    private String documentPath;
    
    public DocumentManager(ConfigManager config) {
//        this.documentPath = config.getProperties().get("generateDocumentsPath").toString();
//        
//        File file = new File(this.documentPath);
//        file.getParentFile().mkdirs();
    }
    
    public void generateTicket() throws FileNotFoundException, IOException {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        
//        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(this.documentPath + "nif_" + simpleDateFormat.format(now) + "_bilhete.pdf"));
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("nif_" + simpleDateFormat.format(now) + "_bilhete.pdf"));
        
        try (Document document = new Document(pdfDocument)) {
        
            PdfFont regular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

            Paragraph emptyParagraph = new Paragraph();
            PdfFont font;
            Paragraph paragraph;
            Table table;

            font = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
            paragraph = new Paragraph("Biological Park - Ticket");
            document.setFont(font).setFontSize(11);
            document.add(paragraph);

            font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            paragraph = new Paragraph("This document grants you access to the purchased points of interest and respective paths.");
            document.setFont(font).setFontSize(9);
            document.add(paragraph);

            document.add(emptyParagraph.setMarginBottom(20));

            table = new Table(5);
            table.setWidth(UnitValue.createPercentValue(100));

            paragraph = new Paragraph("Path type:");
            table.addCell(new Cell().setFont(bold).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            paragraph = new Paragraph("Bike");
            table.addCell(new Cell().setFont(regular).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            table.addCell(new Cell().add(emptyParagraph).setBorder(Border.NO_BORDER));

            paragraph = new Paragraph("Points of interest:");
            table.addCell(new Cell().setFont(bold).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            paragraph = new Paragraph("4");
            table.addCell(new Cell().setFont(regular).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            //---

            paragraph = new Paragraph("Path criteria:");
            table.addCell(new Cell().setFont(bold).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            paragraph = new Paragraph("Distance");
            table.addCell(new Cell().setFont(regular).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            table.addCell(new Cell().add(emptyParagraph).setBorder(Border.NO_BORDER));

            paragraph = new Paragraph("Paths:");
            table.addCell(new Cell().setFont(bold).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            paragraph = new Paragraph("8");
            table.addCell(new Cell().setFont(regular).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            //---

            paragraph = new Paragraph("Issue date:");
            table.addCell(new Cell().setFont(bold).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);

            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paragraph = new Paragraph(simpleDateFormat.format(now));
            table.addCell(new Cell().setFont(regular).add(paragraph).setBorder(Border.NO_BORDER)).setHorizontalAlignment(HorizontalAlignment.LEFT);
            
            table.addCell(new Cell().add(emptyParagraph).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(emptyParagraph).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(emptyParagraph).setBorder(Border.NO_BORDER));

            document.add(table);

            document.close();
            
        }
        
        
//        PdfFont importantNoticeFont = PdfFontFactory.createFont(StandardFonts.COURIER);
//        Paragraph importantNotice = new Paragraph("Important: Form must be filled out in Adobe Reader or Acrobat Professional 8.1 or above. To save completed forms, Acrobat Professional is required. For technical and accessibility assistance, contact the Campus Controller's Office.");
//        importantNotice.setFont(importantNoticeFont).setFontSize(9);
//        importantNotice.setFontColor(Color.RED);
//        doc.add(importantNotice);

    }
}
