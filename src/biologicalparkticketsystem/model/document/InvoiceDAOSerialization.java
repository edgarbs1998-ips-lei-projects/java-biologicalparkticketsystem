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

public class InvoiceDAOSerialization implements IInvoiceDAO {
    
    private String basePath;
    private HashSet<Invoice> list;
    private final static String FILENAME = "invoices.dat";
    
    public InvoiceDAOSerialization (String basePath) {
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
                this.list = (HashSet<Invoice>) input.readObject();
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
    public boolean insertInvoice (Invoice invoice) {
        if (this.list.contains(invoice)) {
            return false;
        }
        this.list.add(invoice);
        saveAll();
        return true;
    }
    
    @Override
    public Invoice findInvoice(String uid) {
        for (Invoice invoice : this.list) {
            if (invoice.getUid().equals(uid)) {
                return invoice;
            }
        }
        return null;
    }
    
    @Override
    public Collection<Invoice> selectInvoices() {
        return this.list;
    }
    
}
