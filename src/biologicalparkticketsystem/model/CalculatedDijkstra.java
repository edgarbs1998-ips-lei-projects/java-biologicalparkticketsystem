/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import digraph.IEdge;
import digraph.IVertex;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author golds
 */
public class CalculatedDijkstra {
    
    private Map<IVertex<PointOfInterest>, Double> costs;
    private Map<IVertex<PointOfInterest>, IVertex<PointOfInterest>> predecessors;
    private Map<IVertex<PointOfInterest>, IEdge<Connection, PointOfInterest>> edges;
    
    public CalculatedDijkstra() {}
    
    public Map<IVertex<PointOfInterest>, Double> getCosts() {
        return this.costs;
    }
    
    public void setCosts(Map<IVertex<PointOfInterest>, Double> costs) {
        this.costs = costs;
    }
    
    public Map<IVertex<PointOfInterest>, IVertex<PointOfInterest>> getPredecessors() {
        return this.predecessors;
    }
    
    public void setPredecessors(Map<IVertex<PointOfInterest>, IVertex<PointOfInterest>> predecessors) {
        this.predecessors = predecessors;
    }
    
    public Map<IVertex<PointOfInterest>, IEdge<Connection, PointOfInterest>> getEdges() {
        return this.edges;
    }
    
    public void setEdges(Map<IVertex<PointOfInterest>, IEdge<Connection, PointOfInterest>> edges) {
        this.edges = edges;
    }
    
}
