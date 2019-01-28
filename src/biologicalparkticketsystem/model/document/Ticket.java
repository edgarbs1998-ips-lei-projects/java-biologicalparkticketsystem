package biologicalparkticketsystem.model.document;

import java.io.Serializable;
import java.util.List;

/**
 * Class to save ticket data
 */
public class Ticket implements IDocument, Serializable {
    
    private String uid;
    private String issueDate;
    private String clientNif;
    private int pathType;
    private String pathCriteria;
    private List<Item> items;
    private double totalDistance;
    private double totalCost;
    
    public Ticket() { }
    
    public Ticket(String uid, String issueDate, String clientNif, int pathType, String pathCriteria, List<Item> items, double totalDistance, double totalCost) {
        this.uid = uid;
        this.issueDate = issueDate;
        this.clientNif = clientNif;
        this.pathType = pathType;
        this.pathCriteria = pathCriteria;
        this.items = items;
        this.totalDistance = totalDistance;
        this.totalCost = totalCost;
    }
    
    @Override
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
    
    public String getClientNif() {
        return this.clientNif;
    }

    public void setClientNif(String clientNif) {
        this.clientNif = clientNif;
    }
    
    public int getPathType() {
        return this.pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }
    
    public String getPathCriteria() {
        return this.pathCriteria;
    }

    public void setPathCriteria(String pathCriteria) {
        this.pathCriteria = pathCriteria;
    }
    
    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public double getTotalDistance() {
        return this.totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
    
    public double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    @Override
    public String toString() {
        return "Ticket {" + this.uid + "}\n\tissueDate="
            + this.issueDate + "\n\tclientNif="
            + this.clientNif + "\n\tpathType="
            + this.pathType + "\n\tpathCriteria="
            + this.pathCriteria + "\n\ttotalDistance="
            + this.totalDistance + "\n\ttotalCost="
            + this.totalCost + "\n\titems="
            + this.items + "\n}";
    }
    
    /**
     * Class to save ticket item data
     */
    public class Item implements Serializable {
        
        private String pathName;
        private String fromPoint;
        private String toPoint;
        private double distance;
        private double cost;
        
        public Item() { }
        
        public Item(String pathName, String fromPoint, String toPoint, double distance, double cost) {
            this.pathName = pathName;
            this.fromPoint = fromPoint;
            this.toPoint = toPoint;
            this.distance = distance;
            this.cost = cost;
        }
        
        public String getPathName() {
            return this.pathName;
        }
        
        public void setPathName(String pathName) {
            this.pathName = pathName;
        }
        
        public String getFromPoint() {
            return this.fromPoint;
        }
        
        public void setFromPoint(String fromPoint) {
            this.fromPoint = fromPoint;
        }
        
        public String getToPoint() {
            return this.toPoint;
        }
        
        public void setToPoint(String toPoint) {
            this.toPoint = toPoint;
        }
        
        public double getDistance() {
            return this.distance;
        }
        
        public void setDistance(double distance) {
            this.distance = distance;
        }
        
        public double getCost() {
            return this.cost;
        }
        
        public void setCost(double cost) {
            this.cost = cost;
        }
        
        @Override
        public String toString() {
            return "\n\t\tItem {" + this.pathName + "}\n\t\t\tfromPoint="
                + this.fromPoint + "\n\t\t\ttoPoint="
                + this.toPoint + "\n\t\t\tdistance="
                + this.distance + "\n\t\t\tcost="
                + this.cost + "\n\t\t}";
        }
        
    }
}
