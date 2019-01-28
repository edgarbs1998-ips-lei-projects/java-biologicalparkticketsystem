package biologicalparkticketsystem.model.document;

import java.util.Collection;

/**
 * Interface to define invoice dao behavior
 */
public interface IInvoiceDAO {
    
    /**
     * Method to save an invoice instance
     * @param invoice invoice instance to insert
     * @return insert success
     */
    public boolean insertInvoice(Invoice invoice);
    
    /**
     * Method to find a invoice by its id
     * @param uid invoice uid
     * @return invoice instance
     */
    public Invoice findInvoice(String uid);
    
    /**
     * Method to select all saved invoices
     * @return collection of invoices
     */
    public Collection<Invoice> selectInvoices();
    
}
