package biologicalparkticketsystem.model.document;

import biologicalparkticketsystem.model.course.CalculatedPath;
import biologicalparkticketsystem.LoggerManager;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DocumentManager {
    
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
            new DocumentTicket(documentPath, uniqueId, calculatedPath, client).generateTicket();
            new DocumentInvoice(documentPath, uniqueId, calculatedPath, companyData, client, vat).generateInvoice();
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
}
