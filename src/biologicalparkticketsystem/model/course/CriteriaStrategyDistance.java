package biologicalparkticketsystem.model.course;

/**
 * Class used for path calculation distance criteria strategy
 */
public class CriteriaStrategyDistance implements ICriteriaStrategy {
    
    @Override
    public double getEdgeWeight(Connection connection) {
        return connection.getDistance();
    }
    
    @Override
    public String getUnit() {
        return "Meters";
    }
    
    @Override
    public String toString() {
        return "Distance";
    }
    
}
