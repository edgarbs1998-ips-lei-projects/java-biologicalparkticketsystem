package biologicalparkticketsystem.model.course;

/**
 * Class to save data connection of path type
 */
public class ConnectionPath extends Connection {
    
    public ConnectionPath(int id, String connectionName, int cost, int distance, boolean navigability) {
        super(id, connectionName, cost, distance, navigability);
    }
    
    @Override
    public String getTypeName() {
        return "path";
    }
    
}
