package biologicalparkticketsystem.model.course;

import java.util.Objects;

/**
 * Abstract class of connection used by connections types classes
 */
public abstract class Connection {
    
    // Attributes
    private final int id;
    private final String connectionName;
    private final int cost;
    private final int distance;
    private final boolean navigability;
    
    public Connection(int id, String connectionName, int cost, int distance, boolean navigability) {
        this.id = id;
        this.connectionName = connectionName;
        this.cost = cost;
        this.distance = distance;
        this.navigability = navigability;
        
    }
    
    public String getConnectionName() {
        return this.connectionName;
    }
    
    public int getConnectionId() {
        return this.id;
    }
    
    public int getCostEuros() {
        return this.cost;
    }
    
    public int getDistance() {
        return this.distance;
    }
    
    public boolean getNavigability() {
        return this.navigability;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Connection other = (Connection) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.connectionName, other.connectionName);
    }

    @Override
    public int hashCode() {
        int prime = 35;
        int result = 2;
        result = prime * result + Objects.hashCode(this.connectionName);
        result = prime * result + this.id;
        return result;
    }
    
    @Override
    public String toString() {
        return String.format("%s {%s, %s, %dm, €%d}",
                this.getConnectionName(),
                this.getTypeName(),
                (this.getNavigability() ? "bicycle" : "foot"),
                this.getDistance(),
                this.getCostEuros()
        );
    }
    
    /**
     * Method to return the connection type
     * @return type name
     */
    public abstract String getTypeName();
    
}
