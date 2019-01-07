/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import java.util.Collection;

/**
 *
 * @author goldspy98
 */
public interface IInvoiceDAO {
    
    public boolean insertInvoice(Invoice invoice);
    public Invoice findInvoice(String uid);
    public Collection<Invoice> selectInvoices();
    
}
