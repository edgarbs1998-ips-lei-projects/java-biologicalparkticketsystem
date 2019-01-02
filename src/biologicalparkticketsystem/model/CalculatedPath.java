/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author golds
 */
public class CalculatedPath {
    
    private int cost;
    private List<PointOfInterest> pointsOfInterest;
    private List<Connection> connections;
    
    public CalculatedPath() {
        this.cost = Integer.MAX_VALUE;
        this.pointsOfInterest = new ArrayList<>();
        this.connections = new ArrayList<>();
    }
    
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public List<PointOfInterest> getPointsOfInterest() {
        return this.pointsOfInterest;
    }
    
    public List<Connection> getConnections() {
        return this.connections;
    }
    
}
