package biologicalparkticketsystem.model.document;

import java.util.Collection;

public interface ITicketDAO {
    
    public boolean insertTicket(Ticket ticket);
    public Ticket findTicket(String uid);
    public Collection<Ticket> selectTickets();
    
}
