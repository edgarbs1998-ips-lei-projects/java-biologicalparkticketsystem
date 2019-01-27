package biologicalparkticketsystem.model.course;

public interface ICriteriaStrategy {
    
    double getEdgeWeight(Connection connection);
    
    String getUnit();
    
    String toString();
    
}
