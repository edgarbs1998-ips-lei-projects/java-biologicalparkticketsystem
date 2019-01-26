package biologicalparkticketsystem.model.document;

import biologicalparkticketsystem.LoggerManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;

public class TicketDAOSerialization implements ITicketDAO {
    
    private String basePath;
    private HashSet<Ticket> list;
    private final static String FILENAME = "tickets.dat";
    
    public TicketDAOSerialization (String basePath) {
        this.basePath = basePath;
        
        // Create the path folder if it does not exists
        if (!this.basePath.equals("")) {
            File file = new File(this.basePath);
            file.mkdirs();
        }
        
        this.list = new HashSet<>();
        loadAll();
    }
    
    private void loadAll() {
        try {
            try (FileInputStream fileIn = new FileInputStream(this.basePath + FILENAME); ObjectInputStream input = new ObjectInputStream(fileIn)) {
                this.list = (HashSet<Ticket>) input.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    private void saveAll() {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(this.basePath + FILENAME); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(this.list);
            }
        } catch (FileNotFoundException ex) {
            LoggerManager.getInstance().log(ex);
        } catch (IOException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    @Override
    public boolean insertTicket (Ticket ticket) {
        if (this.list.contains(ticket)) {
            return false;
        }
        this.list.add(ticket);
        saveAll();
        return true;
    }
    
    @Override
    public Ticket findTicket(String uid) {
        for (Ticket ticket : this.list) {
            if (ticket.getUid().equals(uid)) {
                return ticket;
            }
        }
        return null;
    }
    
    @Override
    public Collection<Ticket> selectTickets() {
        return this.list;
    }
    
}
