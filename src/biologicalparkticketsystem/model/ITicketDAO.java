/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import java.util.Collection;

/**
 *
 * @author golds
 */
public interface ITicketDAO {
    
    public boolean insertTicket(Ticket ticket);
    public Ticket findTicket(String uid);
    public Collection<Ticket> selectTickets();
    
}
