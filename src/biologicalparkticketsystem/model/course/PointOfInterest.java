package biologicalparkticketsystem.model.course;

import java.util.Objects;

/**
 * Class to save point of interest data
 */
public class PointOfInterest {
    
    private final int id;
    private final String pointName;
    
    public PointOfInterest(int id, String pointName) {
        this.id = id;
        this.pointName = pointName;
    }
    
    public int getPoiId() {
        return id;
    }
    
    public String getPoiName() {
        return pointName;
    }
    
    @Override
    public String toString() {
        return this.getPoiName();
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
        final PointOfInterest other = (PointOfInterest) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.pointName, other.pointName);
    }

    @Override
    public int hashCode() {
        int prime = 21;
        int result = 4;
        result = prime * result + Objects.hashCode(this.pointName);
        result = prime * result + this.id;
        return result;
    }
    
}
