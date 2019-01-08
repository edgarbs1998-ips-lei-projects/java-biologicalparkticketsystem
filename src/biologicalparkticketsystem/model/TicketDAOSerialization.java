/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import biologicalparkticketsystem.controller.LoggerManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author golds
 */
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
            FileInputStream fileIn = new FileInputStream(this.basePath + FILENAME);
            ObjectInputStream input = new ObjectInputStream(fileIn);
            this.list = (HashSet<Ticket>) input.readObject();
            input.close();
            fileIn.close();
        } catch (IOException ex) {
        } catch ( ClassNotFoundException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    private void saveAll() {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(this.basePath + FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.list);
            out.close();
            fileOut.close();
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
