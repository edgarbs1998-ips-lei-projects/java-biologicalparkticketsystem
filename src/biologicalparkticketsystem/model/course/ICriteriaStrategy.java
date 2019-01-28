package biologicalparkticketsystem.model.course;

/**
 * Interface to define criteria strategy used for path calculation
 */
public interface ICriteriaStrategy {
    
    /**
     * Method to retrieve the edge weight depending on the strategy
     * @param connection connection to get weight
     * @return edge weight
     */
    double getEdgeWeight(Connection connection);
    
    /**
     * Method to return criteria unit value
     * @return criteria unit
     */
    String getUnit();
    
    /**
     * Method to return criteria name
     * @return criteria name
     */
    @Override
    String toString();
    
}
