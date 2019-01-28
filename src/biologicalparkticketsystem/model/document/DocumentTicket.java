package biologicalparkticketsystem.model.document;

import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.LoggerManager;
import biologicalparkticketsystem.model.course.CalculatedPath;
import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.CriteriaStrategyCost;
import biologicalparkticketsystem.model.course.CriteriaStrategyDistance;
import biologicalparkticketsystem.model.course.PointOfInterest;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class DocumentTicket extends ADocument {
    
    private Ticket ticket;
    private String documentPath;
    private String uniqueId;
    private CalculatedPath calculatedPath;
    private Client client;
    private Date now;
    private int pathTotalDistance, pathTotalCost;
    
    public DocumentTicket(String documentPath, String uniqueId, CalculatedPath calculatedPath, Client client) {
        super();
        
        this.ticket = new Ticket();
        this.ticket.setItems(new ArrayList<>());
        
        this.documentPath = documentPath;
        this.uniqueId = uniqueId;
        this.calculatedPath = calculatedPath;
        this.client = client;
        this.now = new Date();
        this.pathTotalDistance = 0;
        this.pathTotalCost = 0;
        
        try {
            fontRegular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    public void generateTicket() throws FileNotFoundException {
        String pdfPath = this.documentPath + (client == null ? "customer" : client.getNif()) + "_" + fileDateFormat.format(now) + "_bilhete.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfPath));
        
        try (Document document = new Document(pdfDocument)) {
            // Generate header
            generateHeader(document);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(30));
            
            // Generate details table
            generateDetailsTable(document);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(20));
            
            // Generate path table
            generatePathTable(document);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(40));
            
            // Dotted line separator
            document.add(new LineSeparator(new CustomDottedLine(pdfDocument.getDefaultPageSize())));
            
            // Break line
            document.add(new Paragraph().setMarginBottom(30));
            
            // Generate detachable table
            generateDetachableTable(document);
            
            // Close document
            document.close();
        }
        
        // Set ticket object data
        ticket.setUid(uniqueId);
        ticket.setIssueDate(dataDateFormat.format(now));
        ticket.setClientNif((client == null ? "customer" : client.getNif()));
        ticket.setPathType((calculatedPath.getNavigability() == true ? 1 : 0));
        ticket.setPathCriteria(calculatedPath.getCriteria().toString());
        ticket.setTotalDistance(this.pathTotalDistance);
        ticket.setTotalCost(this.pathTotalCost);
        
        // Persist the ticket and add it to the statistics
        DaoManager.getInstance().getTicketDao().insertTicket(ticket);
        DaoManager.getInstance().getStatisticsDao().insertTicket(ticket, calculatedPath);
        
        // Log the generated ticket
        LoggerManager.getInstance().log(LoggerManager.Component.TICKETS_ISSUANCE, "uid: " + uniqueId);
        
        // Open the generated file
        openPdfFile(pdfPath);
    }
    
    private void generateHeader(Document document) {
        Paragraph paragraph;
        
        // Document unique identifier
        paragraph = new Paragraph("id: " + uniqueId);
        paragraph.setFont(fontRegular).setFontSize(9);
        paragraph.setFontColor(ColorConstants.GRAY);
        paragraph.setTextAlignment(TextAlignment.RIGHT);
        document.add(paragraph);

        // Title
        paragraph = new Paragraph("Biological Park - Ticket");
        paragraph.setFont(fontBold).setFontSize(18);
        document.add(paragraph);

        // Subtitle
        paragraph = new Paragraph("This document grants you access to the purchased points of interest and respective paths.");
        paragraph.setFont(fontRegular).setFontSize(12);
        document.add(paragraph);
    }
    
    private void generateDetailsTable(Document document) {
        Paragraph paragraph;
        Cell cell;
        
        // Details table
        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100));

        // Details table - Path type
        paragraph = new Paragraph("Path type:");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(15));
        table.addCell(cell);

        paragraph = new Paragraph((calculatedPath.getNavigability() == true ? "On Bike" : "On Foot" ));
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(35));
        table.addCell(cell);

        // Details table - Issue date
        paragraph = new Paragraph("Issue date:");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(15));
        table.addCell(cell);

        paragraph = new Paragraph(dataDateFormat.format(now));
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(35));
        table.addCell(cell);

        // Details table - Patch criteria
        paragraph = new Paragraph("Path criteria:");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(15));
        table.addCell(cell);

        paragraph = new Paragraph(calculatedPath.getCriteria().toString());
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(35));
        table.addCell(cell);

        // Details table - Selected POIs
        paragraph = new Paragraph("Selected POIs:");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(15));
        table.addCell(cell);

        paragraph = new Paragraph(Integer.toString(calculatedPath.getMustVisit().size()));
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(35));
        table.addCell(cell);

        document.add(table);
    }
    
    private void generatePathTable(Document document) {// Path table
        Table table = new Table(5);
        table.setWidth(UnitValue.createPercentValue(100));
        
        // Generate header
        generatePathTableHeader(table);
        
        // Generate data
        generatePathTableData(table);
        
        // Generate fotter
        generatePathTableFooter(table);
        
        document.add(table);
    }
    
    private void generatePathTableHeader(Table table) {
        Paragraph paragraph;
        Cell cell;
        
        // Path table - Header
        paragraph = new Paragraph("Path name");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        table.addCell(cell);

        paragraph = new Paragraph("From point");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        table.addCell(cell);

        paragraph = new Paragraph("To point");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        table.addCell(cell);

        paragraph = new Paragraph("Distance (" + new CriteriaStrategyDistance().getUnit() + ")");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        table.addCell(cell);

        paragraph = new Paragraph("Cost (" + new CriteriaStrategyCost().getUnit() + ")");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        table.addCell(cell);
    }
    
    private void generatePathTableData(Table table) {
        Paragraph paragraph;
        Cell cell;
        
        // Path table - Data
        Iterator<PointOfInterest> pointsOfInterestIterator = calculatedPath.getPointsOfInterest().iterator();
        PointOfInterest firstPoint = pointsOfInterestIterator.next();
        PointOfInterest fromPoint = firstPoint;
        PointOfInterest toPoint = pointsOfInterestIterator.next();
        for (Connection connection : calculatedPath.getConnections()) {
            // Path table - Path name
            paragraph = new Paragraph(connection.getConnectionName());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);

            // Path table - From POI name
            paragraph = new Paragraph(fromPoint.getPoiName());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);

            // Path table - To POI name
            paragraph = new Paragraph(toPoint.getPoiName());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);

            // Path table - Path distance
            this.pathTotalDistance += connection.getDistance();
            paragraph = new Paragraph(Integer.toString(connection.getDistance()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);

            // Path table - Path cost
            this.pathTotalCost += connection.getCostEuros();
            paragraph = new Paragraph(Integer.toString(connection.getCostEuros()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);

            // Save on ticket item
            Ticket.Item ticketItem = this.ticket.new Item();
            ticketItem.setPathName(connection.getConnectionName());
            ticketItem.setFromPoint(fromPoint.getPoiName());
            ticketItem.setToPoint(toPoint.getPoiName());
            ticketItem.setDistance(connection.getDistance());
            ticketItem.setCost(connection.getCostEuros());
            this.ticket.getItems().add(ticketItem);

            fromPoint = toPoint;
            toPoint = (pointsOfInterestIterator.hasNext() ? pointsOfInterestIterator.next() : firstPoint);
        }
    }
    
    private void generatePathTableFooter(Table table) {
        Paragraph paragraph;
        Cell cell;
        
        // Path table - Footer
        table.addFooterCell(new Cell(1, 2).setBorder(Border.NO_BORDER));

        paragraph = new Paragraph("Total");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setTextAlignment(TextAlignment.RIGHT);
        table.addFooterCell(cell);

        paragraph = new Paragraph(Integer.toString(this.pathTotalDistance));
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setTextAlignment(TextAlignment.RIGHT);
        table.addFooterCell(cell);

        paragraph = new Paragraph(Integer.toString(this.pathTotalCost));
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setTextAlignment(TextAlignment.RIGHT);
        table.addFooterCell(cell);
    }
    
    private void generateDetachableTable(Document document) {
        Paragraph paragraph;
        Cell cell;
        
        // Detachable table
        Table table = new Table(2);
        table.setWidth(UnitValue.createPercentValue(100));

        // Detachable table - Ticker date
        paragraph = new Paragraph("Ticket date:");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(15));
        table.addFooterCell(cell);

        paragraph = new Paragraph(dataDateFormat.format(now));
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(85));
        table.addFooterCell(cell);

        // Detachable table - Ticker id
        paragraph = new Paragraph("Ticket id:");
        paragraph.setFont(fontBold).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(15));
        table.addFooterCell(cell);

        paragraph = new Paragraph(uniqueId);
        paragraph.setFont(fontRegular).setFontSize(11);
        cell = new Cell().add(paragraph);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(UnitValue.createPercentValue(85));
        table.addFooterCell(cell);

        document.add(table);

        // Detachable part warning
        paragraph = new Paragraph("(detachable part)");
        paragraph.setFont(fontRegular).setFontSize(9);
        paragraph.setFontColor(ColorConstants.GRAY);
        paragraph.setTextAlignment(TextAlignment.RIGHT);
        document.add(paragraph);
    }
    
    private class CustomDottedLine extends DottedLine {
        protected Rectangle pageSize;

        public CustomDottedLine(Rectangle pageSize) {
            this.pageSize = pageSize;
        }

        @Override
        public void draw(PdfCanvas canvas, Rectangle drawArea) {
            super.draw(canvas, new Rectangle(pageSize.getLeft(), drawArea.getBottom(), pageSize.getWidth(), drawArea.getHeight()));
        }
    }
    
}
