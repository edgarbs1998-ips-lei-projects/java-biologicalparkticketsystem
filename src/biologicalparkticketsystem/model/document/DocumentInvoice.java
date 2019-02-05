package biologicalparkticketsystem.model.document;

import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.model.course.CalculatedPath;
import biologicalparkticketsystem.model.course.Connection;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentInvoice extends ADocument {
    
    private final static int INVOICE_DETAILS_CELL_HEIGHT = 14;
    private final static String CURRENCY = "EUR";
    
    private Invoice invoice;
    private String documentPath;
    private String uniqueId;
    private CalculatedPath calculatedPath;
    private Client company;
    private Client client;
    private Date now;
    private double totalAmmount, baseAmmount, taxAmmount;
    private double vat;
    
    public DocumentInvoice(String documentPath, String uniqueId, CalculatedPath calculatedPath, Client company, Client client, double vat) {
        super();
        
        this.invoice = new Invoice();
        this.invoice.setItems(new ArrayList<>());
        
        this.documentPath = documentPath;
        this.uniqueId = uniqueId;
        this.calculatedPath = calculatedPath;
        this.company = company;
        this.client = client;
        this.now = new Date();
        this.totalAmmount = 0;
        this.baseAmmount = 0;
        this.taxAmmount = 0;
        this.vat = vat;
    }
    
    public void generateInvoice() throws FileNotFoundException {
        String pdfPath = this.documentPath + (client == null ? "customer" : client.getNif()) + "_" + fileDateFormat.format(now) + "_fatura.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfPath));
        
        try (Document document = new Document(pdfDocument)) {
            // Generate header
            generateHeader(document);

            // Break line
            document.add(new Paragraph().setMarginBottom(20));
            
            // Generate details table
            generateDetailsTable(document);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(20));
            
            // Generate fromto table
            generateFromToTable(document);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(30));
            
            // Generate invoice table
            generateInvoiceTable(document);
            
            // Break line
            document.add(new Paragraph().setMarginBottom(10));
            
            // Generate total table
            generateTotalTable(document);
        }
        
        // Set invoice object data
        invoice.setUid(uniqueId);
        invoice.setClient(client);
        invoice.setIssueDate(dataDateFormat.format(now));
        invoice.setVat(this.vat);
        invoice.setBaseAmmount(baseAmmount);
        invoice.setTaxAmmount(taxAmmount);
        invoice.setTotal(totalAmmount);
        invoice.setCurrency(CURRENCY);
        
        // Persist the invoice
        DaoManager.getInstance().getInvoiceDao().insertInvoice(invoice);
        
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
        paragraph = new Paragraph("Biological Park - Invoice");
        paragraph.setFont(fontBold).setFontSize(18);
        document.add(paragraph);
    }
    
    private void generateDetailsTable(Document document) {
        Paragraph paragraph;
        Cell cell;
        
        // Details table
        Table table = new Table(2);
        table.setWidth(UnitValue.createPercentValue(100));

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
        cell.setWidth(UnitValue.createPercentValue(85));
        table.addCell(cell);

        document.add(table);
    }
    
    private void generateFromToTable(Document document) {
        Paragraph paragraph;
        Cell cell;
        
        // FromTo table
        Table table = new Table(2);
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
        paragraph = new Paragraph("NIF: " + this.company.getNif());
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
        paragraph = new Paragraph(this.company.getName());
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

        // Generate table address
        generateFromToTableAddress(table);

        document.add(table);
    }
    
    private void generateFromToTableAddress(Table table) {
        Paragraph paragraph;
        Cell cell;
        
        // FromTo table - Address Address
        paragraph = new Paragraph(this.company.getAddress().getAddress());
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
        paragraph = new Paragraph(this.company.getAddress().getPostalCode());
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
        paragraph = new Paragraph(this.company.getAddress().getLocation());
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
        paragraph = new Paragraph(this.company.getAddress().getCountry());
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
    }
    
    private void generateInvoiceTable(Document document) {
        // Invoice table
        Table table = new Table(6);
        table.setWidth(UnitValue.createPercentValue(100));

        // Generate header
        generateInvoiceTableHeader(table);

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

        // Generate data
        generateInvoiceTableData(table, groupConnections);

        document.add(table);
    }
    
    private void generateInvoiceTableHeader(Table table) {
        Paragraph paragraph;
        Cell cell;
        
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
    }
    
    private void generateInvoiceTableData(Table table, Map<Integer, List<Connection>> groupConnections) {
        Paragraph paragraph;
        Cell cell;
        
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
    }
    
    private void generateTotalTable(Document document) {
        // Total table
        Table table = new Table(6);
        table.setWidth(UnitValue.createPercentValue(100));

        // Generate header
        generateTotalTableHeader(table);

        // Generate data
        generateTotalTableData(table);
        
        document.add(table);
    }
    
    private void generateTotalTableHeader(Table table) {
        Paragraph paragraph;
        Cell cell;
        
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
    }
    
    private void generateTotalTableData(Table table) {
        Paragraph paragraph;
        Cell cell;
        
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
    }
    
}
