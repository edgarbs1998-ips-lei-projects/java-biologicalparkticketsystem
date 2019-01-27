package biologicalparkticketsystem.model.course;

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
