package biologicalparkticketsystem.model.course;

import java.util.Objects;

public abstract class Connection {
    
    // Attributes
    private final int id;
    private final String connectionName;
    private final int cost;
    private final int distance;
    private final boolean navigability;
    
    /**
     * @param id
     * @param connectionName
     * @param cost
     * @param distance
     * @param navigability
     */
    public Connection(int id, String connectionName, int cost, int distance, boolean navigability) {
        this.id = id;
        this.connectionName = connectionName;
        this.cost = cost;
        this.distance = distance;
        this.navigability = navigability;
        
    }
    
    /**
     *
     * @return connection name
     */
    public String getConnectionName() {
        return this.connectionName;
    }
    
    /**
     *
     * @return connection id
     */
    public int getConnectionId() {
        return this.id;
    }
    
    /**
     *
     * @return connection cost
     */
    public int getCostEuros() {
        return this.cost;
    }
    
    /**
     *
     * @return connection distance
     */
    public int getDistance() {
        return this.distance;
    }
    
    /**
     *
     * @return connection navigability (on foot or on bike)
     */
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
    
    public abstract String getTypeName();
    
}
