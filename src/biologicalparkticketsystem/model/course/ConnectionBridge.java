package biologicalparkticketsystem.model.course;

/**
 * Class to save data connection of bridge type
 */
public class ConnectionBridge extends Connection {
    
    public ConnectionBridge(int id, String connectionName, int cost, int distance, boolean navigability) {
        super(id, connectionName, cost, distance, navigability);
    }
    
    @Override
    public String getTypeName() {
        return "bridge";
    }
    
}
