package biologicalparkticketsystem.model.document;

import java.util.Collection;

/**
 * Interface to define invoice dao behavior
 */
public interface ITicketDAO {
    
    /**
     * Method to save an ticket instance
     * @param ticket ticket instance to insert
     * @return insert success
     */
    public boolean insertTicket(Ticket ticket);
    
    /**
     * Method to find a ticket by its id
     * @param uid ticket uid
     * @return invoice ticket
     */
    public Ticket findTicket(String uid);
    
    /**
     * Method to select all saved tickets
     * @return collection of tickets
     */
    public Collection<Ticket> selectTickets();
    
}
