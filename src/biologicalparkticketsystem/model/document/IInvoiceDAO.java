package biologicalparkticketsystem.model.document;

import java.util.Collection;

public interface IInvoiceDAO {
    
    public boolean insertInvoice(Invoice invoice);
    public Invoice findInvoice(String uid);
    public Collection<Invoice> selectInvoices();
    
}
