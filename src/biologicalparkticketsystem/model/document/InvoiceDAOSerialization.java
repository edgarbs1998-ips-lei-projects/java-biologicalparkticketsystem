package biologicalparkticketsystem.model.document;

import java.util.Collection;

public class InvoiceDAOSerialization extends DocumentDAOSerialization<Invoice> implements IInvoiceDAO {

    private final static String FILENAME = "invoices.dat";
    
    public InvoiceDAOSerialization(String basePath) {
        super(basePath, FILENAME);
    }
    
    @Override
    public boolean insertInvoice(Invoice invoice) {
        return insert(invoice);
    }
    
    @Override
    public Invoice findInvoice(String uid) {
        return find(uid);
    }
    
    @Override
    public Collection<Invoice> selectInvoices() {
        return selectAll();
    }
    
}
