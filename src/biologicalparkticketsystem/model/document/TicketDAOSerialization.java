package biologicalparkticketsystem.model.document;

import java.util.Collection;

/**
 * Class to handle ticket persistence based on serialization
 */
public class TicketDAOSerialization extends DocumentDAOSerialization<Ticket> implements ITicketDAO {

    private final static String FILENAME = "tickets.dat";
    
    public TicketDAOSerialization(String basePath) {
        super(basePath, FILENAME);
    }
    
    @Override
    public boolean insertTicket(Ticket ticket) {
        return insert(ticket);
    }
    
    @Override
    public Ticket findTicket(String uid) {
        return find(uid);
    }
    
    @Override
    public Collection<Ticket> selectTickets() {
        return selectAll();
    }
    
}
