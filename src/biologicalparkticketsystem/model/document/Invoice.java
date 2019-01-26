package biologicalparkticketsystem.model.document;

import java.io.Serializable;
import java.util.List;

public class Invoice implements Serializable {
    
    private String uid;
    private String issueDate;
    private Client client;
    private List<Item> items;
    private double vat;
    private double baseAmmount;
    private double taxAmmount;
    private double total;
    private String currency;
    
    public Invoice() { }
    
    public Invoice(String uid, String issueDate, Client client, List<Item> items, double vat, double baseAmmount, double taxAmmount, double total, String currency) {
        this.uid = uid;
        this.issueDate = issueDate;
        this.client = client;
        this.items = items;
        this.vat = vat;
        this.baseAmmount = baseAmmount;
        this.taxAmmount = taxAmmount;
        this.total = total;
        this.currency = currency;
    }
    
    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
    
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public double getVat() {
        return this.vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }
    
    public double getBaseAmmount() {
        return this.baseAmmount;
    }

    public void setBaseAmmount(double baseAmmount) {
        this.baseAmmount = baseAmmount;
    }
    
    public double getTaxAmmount() {
        return this.taxAmmount;
    }

    public void setTaxAmmount(double taxAmmount) {
        this.taxAmmount = taxAmmount;
    }
    
    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    @Override
    public String toString() {
        return "Invoice {" + this.uid + "}\n\tissueDate="
            + this.issueDate + "\n\tvat="
            + this.vat + "\n\tbaseAmmount="
            + this.baseAmmount + "\n\ttaxAmmount="
            + this.taxAmmount + "\n\ttotal="
            + this.total + "\n\tcurrency="
            + this.currency + "\n\titems="
            + this.items + "\n\tclient="
            + this.client + "\n}";
    }
    
    public class Item implements Serializable {
        
        private String item;
        private double price;
        private int quantity;
        private double subtotal;
        private double vat;
        private double total;
        
        public Item() { }
        
        public Item(String item, double price, int quantity, double subtotal, double vat, double total) {
            this.item = item;
            this.price = price;
            this.quantity = quantity;
            this.subtotal = subtotal;
            this.vat = vat;
            this.total = total;
        }
        
        public String getItem() {
            return this.item;
        }
        
        public void setItem(String item) {
            this.item = item;
        }
        
        public double getPrice() {
            return this.price;
        }
        
        public void setPrice(double price) {
            this.price = price;
        }
        
        public int getQuantity() {
            return this.quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public double getSubtotal() {
            return this.subtotal;
        }
        
        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
        
        public double getVat() {
            return this.vat;
        }
        
        public void setVat(double vat) {
            this.vat = vat;
        }
        
        public double getTotal() {
            return this.total;
        }
        
        public void setTotal(double total) {
            this.total = total;
        }
        
        @Override
        public String toString() {
            return "\n\t\tItem {" + this.item + "}\n\t\t\tprice="
                + this.price + "\n\t\t\tquantity="
                + this.quantity + "\n\t\t\tsubtotal="
                + this.subtotal + "\n\t\t\tvat="
                + this.vat + "\n\t\t\ttotal="
                + this.total + "\n\t\t}";
        }
        
    }
    
}
