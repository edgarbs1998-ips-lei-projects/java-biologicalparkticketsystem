package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.CalculatedPath;
import biologicalparkticketsystem.model.Client;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.Invoice;
import biologicalparkticketsystem.model.PointOfInterest;
import biologicalparkticketsystem.model.Ticket;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
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
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DocumentManager {
    
    private final static int INVOICE_DETAILS_CELL_HEIGHT = 14;
    private final static String CURRENCY = "EUR";
    
    private final String documentPath;
    private final Client companyData;
    private final double vat;
    
    public DocumentManager(String documentPath, Client companyData, double vat) {
        this.documentPath = documentPath;
        this.companyData = companyData;
        this.vat = vat;
        
        // Create the path folder if it does not exists
        if (!this.documentPath.equals("")) {
            File file = new File(this.documentPath);
            file.mkdirs();
        }
    }
    
    public void generateDocuments(CalculatedPath calculatedPath, Client client) {
        String uniqueId = UUID.randomUUID().toString();
        
        try {
            this.generateTicket(uniqueId, calculatedPath, client);
            this.generateInvoice(uniqueId, calculatedPath, client);
            
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    private void generateTicket(String uniqueId, CalculatedPath calculatedPath, Client client) throws FileNotFoundException, IOException {
        Ticket ticket = new Ticket();
        ticket.setUid(uniqueId);
        ticket.setItems(new ArrayList<>());
        
        int totalDistance = 0; // Path total distance
        int totalCost = 0; // Path total cost
        
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        
        String pdfPath = this.documentPath + (client == null ? "customer" : client.getNif()) + "_" + simpleDateFormat.format(now) + "_bilhete.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfPath));
        
        try (Document document = new Document(pdfDocument)) {
        
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            
            Paragraph paragraph;
            Table table;
            Cell cell;
            
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

            // Break line
            document.add(new Paragraph().setMarginBottom(30));
            
            // Details table
            table = new Table(4);
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
            
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paragraph = new Paragraph(simpleDateFormat.format(now));
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
            
            // Break line
            document.add(new Paragraph().setMarginBottom(20));
            
            // Path table
            table = new Table(5);
            table.setWidth(UnitValue.createPercentValue(100));
            
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
            
            paragraph = new Paragraph("Distance (" + CourseManager.Criteria.DISTANCE.getUnit() + ")");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Cost (" + CourseManager.Criteria.COST.getUnit() + ")");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);

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
                totalDistance += connection.getDistance();
                paragraph = new Paragraph(Integer.toString(connection.getDistance()));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Path table - Path cost
                totalCost += connection.getCostEuros();
                paragraph = new Paragraph(Integer.toString(connection.getCostEuros()));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Save on ticket item
                Ticket.Item ticketItem = ticket.new Item();
                ticketItem.setPathName(connection.getConnectionName());
                ticketItem.setFromPoint(fromPoint.getPoiName());
                ticketItem.setToPoint(toPoint.getPoiName());
                ticketItem.setDistance(connection.getDistance());
                ticketItem.setCost(connection.getCostEuros());
                ticket.getItems().add(ticketItem);
                
                fromPoint = toPoint;
                toPoint = (pointsOfInterestIterator.hasNext() ? pointsOfInterestIterator.next() : firstPoint);
            }
            
            // Path table - Footer
            table.addFooterCell(new Cell(1, 2).setBorder(Border.NO_BORDER));
            
            paragraph = new Paragraph("Total");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addFooterCell(cell);
            
            paragraph = new Paragraph(Integer.toString(totalDistance));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addFooterCell(cell);
            
            paragraph = new Paragraph(Integer.toString(totalCost));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addFooterCell(cell);
            
            document.add(table);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(40));
            
            // Dotted line separator
            document.add(new LineSeparator(new CustomDottedLine(pdfDocument.getDefaultPageSize())));
            
            // Break line
            document.add(new Paragraph().setMarginBottom(30));
            
            // Detachable table
            table = new Table(2);
            table.setWidth(UnitValue.createPercentValue(100));
            
            // Detachable table - Ticker date
            paragraph = new Paragraph("Ticket date:");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setWidth(UnitValue.createPercentValue(15));
            table.addFooterCell(cell);
            
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paragraph = new Paragraph(simpleDateFormat.format(now));
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

            document.close();
            
        }
        
        ticket.setIssueDate(simpleDateFormat.format(now));
        ticket.setClientNif((client == null ? "customer" : client.getNif()));
        ticket.setPathType((calculatedPath.getNavigability() == true ? 1 : 0));
        ticket.setPathCriteria(calculatedPath.getCriteria().toString());
        ticket.setTotalDistance(totalDistance);
        ticket.setTotalCost(totalCost);
        
        DaoManager.getInstance().getTicketDao().insertTicket(ticket);
        DaoManager.getInstance().getStatisticsDao().insertTicket(ticket, calculatedPath);
        
        LoggerManager.getInstance().log(LoggerManager.Component.TICKETS_ISSUANCE, "uid: " + uniqueId);
        
        openPdf(pdfPath);
    }
    
    private void generateInvoice(String uniqueId, CalculatedPath calculatedPath, Client client) throws FileNotFoundException, IOException {
        Invoice invoice = new Invoice();
        invoice.setUid(uniqueId);
        invoice.setClient(client);
        invoice.setItems(new ArrayList<>());
        
        double totalAmmount = 0; // Total ammount
        double baseAmmount; // Base ammount
        double taxAmmount; // Tax ammount
        
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        
        String pdfPath = this.documentPath + (client == null ? "customer" : client.getNif()) + "_" + simpleDateFormat.format(now) + "_fatura.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfPath));
        
        try (Document document = new Document(pdfDocument)) {
            
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            
            Paragraph paragraph;
            Table table;
            Cell cell;
            
            // Document unique identifier
            paragraph = new Paragraph("id: " + uniqueId);
            paragraph.setFont(fontRegular).setFontSize(9);
            paragraph.setFontColor(ColorConstants.GRAY);
            paragraph.setTextAlignment(TextAlignment.RIGHT);
            document.add(paragraph);

            // Title
            paragraph = new Paragraph("Biological Park - Invoice");
            paragraph.setFont(fontBold).setFontSize(18);
            document.add(paragraph);

            // Break line
            document.add(new Paragraph().setMarginBottom(20));
            
            // Details table
            table = new Table(2);
            table.setWidth(UnitValue.createPercentValue(100));
            
            // Details table - Issue date
            paragraph = new Paragraph("Issue date:");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setWidth(UnitValue.createPercentValue(15));
            table.addCell(cell);
            
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paragraph = new Paragraph(simpleDateFormat.format(now));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setWidth(UnitValue.createPercentValue(85));
            table.addCell(cell);
            
            document.add(table);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(20));
            
            // FromTo table
            table = new Table(2);
            table.setWidth(UnitValue.createPercentValue(100));

            // FromTo table - Headers
            paragraph = new Paragraph("From:");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setWidth(UnitValue.createPercentValue(50));
            table.addHeaderCell(cell);
            
            paragraph = new Paragraph("To:");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setWidth(UnitValue.createPercentValue(50));
            table.addHeaderCell(cell);
            
            // FromTo table - Nif
            paragraph = new Paragraph("NIF: " + this.companyData.getNif());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(24);
            table.addCell(cell);
            
            paragraph = new Paragraph((client == null ? "NIF: Customer" : "NIF: " + client.getNif()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(24);
            table.addCell(cell);
            
            // FromTo table - Name
            paragraph = new Paragraph(this.companyData.getName());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            paragraph = new Paragraph((client == null ? "" : client.getName()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            // FromTo table - Address Address
            paragraph = new Paragraph(this.companyData.getAddress().getAddress());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            paragraph = new Paragraph((client == null ? "" : client.getAddress().getAddress()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            // FromTo table - Address Postal Code
            paragraph = new Paragraph(this.companyData.getAddress().getPostalCode());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            paragraph = new Paragraph((client == null ? "" : client.getAddress().getPostalCode()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            // FromTo table - Address Location
            paragraph = new Paragraph(this.companyData.getAddress().getLocation());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            paragraph = new Paragraph((client == null ? "" : client.getAddress().getLocation()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            // FromTo table - Address Country
            paragraph = new Paragraph(this.companyData.getAddress().getCountry());
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            paragraph = new Paragraph((client == null ? "" : client.getAddress().getCountry()));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setBorder(Border.NO_BORDER);
            cell.setHeight(INVOICE_DETAILS_CELL_HEIGHT);
            table.addCell(cell);
            
            document.add(table);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(30));
            
            // Invoice table
            table = new Table(6);
            table.setWidth(UnitValue.createPercentValue(100));
            
            // Invoice table - Header
            paragraph = new Paragraph("Item");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Price");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Quantity");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Subtotal");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("VAT");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Total");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            // Group connections
            Map<Integer, List<Connection>> groupConnections = new HashMap<>();
            for (Connection connection : calculatedPath.getConnections()) {
                if (groupConnections.containsKey(connection.getConnectionId())) {
                    List<Connection> tempConnection = groupConnections.get(connection.getConnectionId());
                    tempConnection.add(connection);
                } else {
                    List<Connection> tempConnection = new ArrayList<>();
                    tempConnection.add(connection);
                    groupConnections.put(connection.getConnectionId(), tempConnection);
                }
            }

            // Invoice table - Data
            for (List<Connection> connections : groupConnections.values()) {
                // Invoice table - Item name
                paragraph = new Paragraph(connections.get(0).getConnectionName());
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                table.addCell(cell);
                
                // Invoice table - Price
                double basePrice = connections.get(0).getCostEuros() * (100.0 - this.vat) / 100.0;
                paragraph = new Paragraph(Double.toString(basePrice));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Invoice table - Quantity
                paragraph = new Paragraph(Integer.toString(connections.size()));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Invoice table - Subtotal
                paragraph = new Paragraph(Double.toString(basePrice * connections.size()));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Invoice table - VAT
                paragraph = new Paragraph(Double.toString(this.vat));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Invoice table - Total
                double totalPrice = connections.get(0).getCostEuros() * connections.size();
                totalAmmount += totalPrice;
                paragraph = new Paragraph(Double.toString(totalPrice));
                paragraph.setFont(fontRegular).setFontSize(11);
                cell = new Cell().add(paragraph);
                cell.setTextAlignment(TextAlignment.RIGHT);
                table.addCell(cell);
                
                // Save on invoice item
                Invoice.Item invoiceItem = invoice.new Item();
                invoiceItem.setItem(connections.get(0).getConnectionName());
                invoiceItem.setPrice(basePrice);
                invoiceItem.setQuantity(connections.size());
                invoiceItem.setSubtotal(basePrice * connections.size());
                invoiceItem.setVat(this.vat);
                invoiceItem.setTotal(totalPrice);
                invoice.getItems().add(invoiceItem);
            }
            
            document.add(table);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(10));
            
            // Total table
            table = new Table(6);
            table.setWidth(UnitValue.createPercentValue(100));
            
            // Total table - Header
            paragraph = new Paragraph("TAX");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("%");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);
            
            paragraph = new Paragraph("Base ammount");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Tax ammount");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Total");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph("Currency");
            paragraph.setFont(fontBold).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            // Total table - Data
            paragraph = new Paragraph("VAT");
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            paragraph = new Paragraph(Double.toString(this.vat));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);
            
            baseAmmount = totalAmmount * (100.0 - this.vat) / 100.0;
            paragraph = new Paragraph(Double.toString(baseAmmount));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);

            taxAmmount = totalAmmount * this.vat / 100.0;
            paragraph = new Paragraph(Double.toString(taxAmmount));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);

            paragraph = new Paragraph(Double.toString(totalAmmount));
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            cell.setTextAlignment(TextAlignment.RIGHT);
            table.addCell(cell);

            paragraph = new Paragraph(CURRENCY);
            paragraph.setFont(fontRegular).setFontSize(11);
            cell = new Cell().add(paragraph);
            table.addCell(cell);
            
            document.add(table);
        }
        
        invoice.setIssueDate(simpleDateFormat.format(now));
        invoice.setVat(this.vat);
        invoice.setBaseAmmount(baseAmmount);
        invoice.setTaxAmmount(taxAmmount);
        invoice.setTotal(totalAmmount);
        invoice.setCurrency(CURRENCY);
        
        DaoManager.getInstance().getInvoiceDao().insertInvoice(invoice);
        
        openPdf(pdfPath);
    }
    
    private void openPdf(String path) {
        try {
            File pdfFile = new File(path);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    //System.out.println("Awt Desktop is not supported!");
                }
            } else {
                //System.out.println("File is not exists!");
            }
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
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
