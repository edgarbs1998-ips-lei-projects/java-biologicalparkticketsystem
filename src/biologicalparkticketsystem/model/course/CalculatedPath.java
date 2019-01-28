package biologicalparkticketsystem.model.course;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to save data of calculated path
 */
public class CalculatedPath {
    
    private ICriteriaStrategy criteria;
    private boolean navigability;
    private int cost;
    private List<PointOfInterest> pointsOfInterest;
    private List<Connection> connections;
    private List<PointOfInterest> mustVisit;
    
    public CalculatedPath() {
        this.cost = Integer.MAX_VALUE;
        this.pointsOfInterest = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.mustVisit = new ArrayList<>();
    }
    
    public ICriteriaStrategy getCriteria() {
        return this.criteria;
    }
    
    public void setCriteria(ICriteriaStrategy criteria) {
        this.criteria = criteria;
    }
    
    public boolean getNavigability() {
        return this.navigability;
    }
    
    public void setNavigability(boolean navigability) {
        this.navigability = navigability;
    }
    
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public List<PointOfInterest> getPointsOfInterest() {
        return this.pointsOfInterest;
    }
    
    public List<Connection> getConnections() {
        return this.connections;
    }
    
    public List<PointOfInterest> getMustVisit() {
        return this.mustVisit;
    }
    
    public void setMustVisit(List<PointOfInterest> mustVisit) {
        this.mustVisit = mustVisit;
    }
    
    /**
     * Method to create a snapshot of the class data
     * @return calculated path memento instance
     */
    public CalculatedPathMemento createMomento() {
        return new CalculatedPathMemento(
                this.criteria,
                this.navigability,
                this.cost,
                this.pointsOfInterest,
                this.connections,
                this.mustVisit
        );
    }
    
    /**
     * Class to restore a snapshot of the class data
     * @param memento calculated path memento instance
     */
    public void setMemento(CalculatedPathMemento memento) {
        this.criteria = memento.getMementoCriteria();
        this.navigability = memento.getMementoNavigability();
        this.cost = memento.getMementoCost();
        this.pointsOfInterest = memento.getMementoPointsOfInterest();
        this.connections = memento.getMementoConnections();
        this.mustVisit = memento.getMementoMustVisit();
    }
    
}
