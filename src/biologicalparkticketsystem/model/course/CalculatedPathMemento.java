package biologicalparkticketsystem.model.course;

import biologicalparkticketsystem.model.course.CourseManager.Criteria;
import java.util.ArrayList;
import java.util.List;

public class CalculatedPathMemento {
    
    private final Criteria mementoCriteria;
    private final boolean mementoNavigability;
    private final int mementoCost;
    private final List<PointOfInterest> mementoPointsOfInterest;
    private final List<Connection> mementoConnections;
    private final List<PointOfInterest> mementoMustVisit;
    
    public CalculatedPathMemento(Criteria criteria, boolean navigability, int cost, List<PointOfInterest> pointsOfInterest, List<Connection> connections, List<PointOfInterest> mustVisit) {
        this.mementoCriteria = criteria;
        this.mementoNavigability = navigability;
        this.mementoCost = cost;
        this.mementoPointsOfInterest = new ArrayList<>(pointsOfInterest);
        this.mementoConnections = new ArrayList<>(connections);
        this.mementoMustVisit = new ArrayList<>(mustVisit);
    }
    
    public Criteria getMementoCriteria() {
        return this.mementoCriteria;
    }
    
    public boolean getMementoNavigability() {
        return this.mementoNavigability;
    }
    
    public int getMementoCost() {
        return mementoCost;
    }
    
    public List<PointOfInterest> getMementoPointsOfInterest() {
        return this.mementoPointsOfInterest;
    }
    
    public List<Connection> getMementoConnections() {
        return this.mementoConnections;
    }
    
    public List<PointOfInterest> getMementoMustVisit() {
        return this.mementoMustVisit;
    }
    
}
