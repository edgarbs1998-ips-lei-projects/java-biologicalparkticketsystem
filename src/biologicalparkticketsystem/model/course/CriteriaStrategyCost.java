package biologicalparkticketsystem.model.course;

/**
 * Class used for path calculation cost criteria strategy
 */
public class CriteriaStrategyCost implements ICriteriaStrategy {
    
    @Override
    public double getEdgeWeight(Connection connection) {
        return connection.getCostEuros();
    }
    
    @Override
    public String getUnit() {
        return "€";
    }
    
    @Override
    public String toString() {
        return "Cost";
    }
    
}
