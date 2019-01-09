package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.CalculatedDijkstra;
import biologicalparkticketsystem.model.CalculatedPath;
import biologicalparkticketsystem.model.CalculatedPathCareTaker;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.PointOfInterest;
import digraph.IEdge;
import digraph.IVertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseManager {
    
    /**
     * All the criteria needed for the application
     */
    public enum Criteria {
        DISTANCE, 
        COST;

         /**
          *
          * @return the type of unit that is used for cost and distance, by default is "Unknown"
          */
        public String getUnit() {
            switch (this) {
                case COST: return "€";
                case DISTANCE: return "Meters";
            }
            return "Unknown";
        }
        
        @Override
        public String toString() {
            switch (this) {
                case COST: return "Cost";
                case DISTANCE: return "Distance";
            }
            return "Unknown";
        }
    };
    
    private final MapManager mapManager;
    private CalculatedPathCareTaker calculatedPathCareTaker;
    private CalculatedPath calculatedPath;

    /**
     * Course manager constructor
     * @param mapManager
     */
    public CourseManager(MapManager mapManager) {
        this.mapManager = mapManager;
        this.calculatedPathCareTaker = new CalculatedPathCareTaker();
        this.calculatedPath = null;
    }
    
    public CalculatedPath getCalculatedPath() {
        return this.calculatedPath;
    }
    
    public void undoCalculatedCourse() {
        this.calculatedPathCareTaker.restoreState(this.calculatedPath);
    }
    
    public void clearCalculatedCourses() {
        this.calculatedPathCareTaker.clearStates();
        this.calculatedPath = null;
    }
    
    public int getUndoCalculatedCourses() {
        return this.calculatedPathCareTaker.countStates();
    }
    
    /**
     * Calculates the minimum cost path
     * @param criteria
     * @param navigability
     * @param mustVisitPois
     * @return success
     */
    public boolean minimumCriteriaPath(Criteria criteria,
            boolean navigability,
            List<PointOfInterest> mustVisitPois) throws CourseManagerException {
        
        if (mustVisitPois.isEmpty()) {
            throw new CourseManagerException("To generate a path a minimum of one point of interest must be selected");
        }
        
        try {
            if (this.calculatedPath != null) {
                this.calculatedPathCareTaker.saveState(this.calculatedPath);
            }
            
            this.calculatedPath = new CalculatedPath();

            IVertex<PointOfInterest> startPoi = this.mapManager.checkPointOfInterest(this.mapManager.getStartPoint());

            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras = new HashMap<>();

            dijkstraAlgorithm(criteria, navigability, startPoi, calculatedDijkstras);
            for (PointOfInterest poi : mustVisitPois) {
                IVertex<PointOfInterest> vertexPoi = this.mapManager.checkPointOfInterest(poi);
                dijkstraAlgorithm(criteria, navigability, vertexPoi, calculatedDijkstras);
            }

            heapsAlgorithm(mustVisitPois.size(), mustVisitPois, startPoi, calculatedDijkstras);

            this.calculatedPath.setCriteria(criteria);
            this.calculatedPath.setNavigability(navigability);
            this.calculatedPath.setMustVisit(mustVisitPois);
            
            LoggerManager.getInstance().log(LoggerManager.Component.COURSE_CALCULATIONS);
        } catch (MapManagerException ex) {
            LoggerManager.getInstance().log(ex);
            this.calculatedPath = null;
            return false;
        }
        
        return true;
    }
    
    private void heapsAlgorithm(int n, List<PointOfInterest> mustVisitPois,
            IVertex<PointOfInterest> startPoi,
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras) throws MapManagerException, CourseManagerException {
        if (n == 1) {
            CalculatedPath tempCalculatedPath = calculateMustVisitPOIs(startPoi, mustVisitPois, calculatedDijkstras);
            if (tempCalculatedPath.getCost() <= this.calculatedPath.getCost()) {
                this.calculatedPath = tempCalculatedPath;
            }
        } else {
            for (int i = 0; i < n - 1; ++i) {
                heapsAlgorithm(n - 1, mustVisitPois, startPoi, calculatedDijkstras);
                if ((n & 1) == 0) { // Check if n is even or odd
                    Collections.swap(mustVisitPois, i, n-1);
                } else {
                    Collections.swap(mustVisitPois, 0, n-1);
                }
            }
            heapsAlgorithm(n - 1, mustVisitPois, startPoi, calculatedDijkstras);
        }
    }
    
    private CalculatedPath calculateMustVisitPOIs(IVertex<PointOfInterest> startPoi,
            List<PointOfInterest> mustVisitPois,
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras) throws MapManagerException, CourseManagerException {
        
        CalculatedPath tempCalculatedPath = new CalculatedPath();
        int cost = 0;
        
        IVertex<PointOfInterest> origin = startPoi;
        IVertex<PointOfInterest> destination;
        for (PointOfInterest poi : mustVisitPois) {
            destination = this.mapManager.checkPointOfInterest(poi);
            cost += getMinimumPathFromTwoPOIs(origin, destination, calculatedDijkstras, tempCalculatedPath.getPointsOfInterest(), tempCalculatedPath.getConnections());
            origin = destination;
        }
        destination = startPoi;
        cost += getMinimumPathFromTwoPOIs(origin, destination, calculatedDijkstras, tempCalculatedPath.getPointsOfInterest(), tempCalculatedPath.getConnections());
        
        tempCalculatedPath.getPointsOfInterest().add(0, startPoi.element());
        tempCalculatedPath.setCost(cost);
        
        return tempCalculatedPath;
        
    }
    
    private int getMinimumPathFromTwoPOIs(IVertex<PointOfInterest> origin,
            IVertex<PointOfInterest> destination,
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras,
            List<PointOfInterest> pois,
            List<Connection> connections) throws CourseManagerException {
        
        List<PointOfInterest> tempPois = new ArrayList<>();
        List<Connection> tempConnections = new ArrayList<>();
        
        CalculatedDijkstra calculatedDijkstra = calculatedDijkstras.get(origin);
        
        int cost = (int) Math.round(calculatedDijkstra.getCosts().get(destination));
        
        while (destination != origin) {
            tempPois.add(0, destination.element());
            if (!calculatedDijkstra.getEdges().containsKey(destination) || calculatedDijkstra.getEdges().get(destination) == null) {
                throw new CourseManagerException("It is not possible to calculate a path for the selected points of interest");
            }
            tempConnections.add(0, calculatedDijkstra.getEdges().get(destination).element());
            destination = calculatedDijkstra.getPredecessors().get(destination);
        }
        
        pois.addAll(tempPois);
        connections.addAll(tempConnections);
        
        return cost;
        
    }
    
    /**
     * dijkstra logic
     * @param criteria
     * @param orig
     * @param costs
     * @param predecessors
     * @param edges
     */
    private void dijkstraAlgorithm(Criteria criteria,
            boolean navigability,
            IVertex<PointOfInterest> orig,
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras) {
        
        Map<IVertex<PointOfInterest>, Double> costs = new HashMap<>();
        Map<IVertex<PointOfInterest>, IVertex<PointOfInterest>> predecessors = new HashMap<>();
        Map<IVertex<PointOfInterest>, IEdge<Connection, PointOfInterest>> edges = new HashMap<>();
        
        Set<IVertex<PointOfInterest>> visited = new HashSet<>();
        Set<IVertex<PointOfInterest>> unvisited = new HashSet<>();
        
        for (IVertex<PointOfInterest> vertex : this.mapManager.getDiGraph().vertices()) {
            costs.put(vertex, Double.MAX_VALUE);
            predecessors.put(vertex, null);
            edges.put(vertex, null);
        }
        costs.put(orig, 0.0);
        unvisited.add(orig);
        
        while (!unvisited.isEmpty()) {
            IVertex<PointOfInterest> lowerCostVertex = findLowerCostVertex(unvisited, costs);
            unvisited.remove(lowerCostVertex);
            for (IEdge<Connection, PointOfInterest> edge : this.mapManager.getDiGraph().accedentEdges(lowerCostVertex)) {
                if (navigability == false || (navigability == true && edge.element().getConnectionNavigability() == navigability)) {
                    IVertex<PointOfInterest> opposite = this.mapManager.getDiGraph().opposite(lowerCostVertex, edge);
                    if (!visited.contains(opposite)) {
                        double edgeWeight = 0.0;
                        switch (criteria) {
                            case COST:
                                edgeWeight = edge.element().getCostEuros();
                                break;
                            case DISTANCE:
                                edgeWeight = edge.element().getDistance();
                                break;
                        }

                        double sourceCost = costs.get(lowerCostVertex);
                        if (sourceCost + edgeWeight < costs.get(opposite)) {
                            costs.put(opposite, sourceCost + edgeWeight);
                            predecessors.put(opposite, lowerCostVertex);
                            edges.put(opposite, edge);
                        }
                        unvisited.add(opposite);
                    }
                }
            }
            visited.add(lowerCostVertex);
        }
        
        CalculatedDijkstra calculatedDijkstra = new CalculatedDijkstra();
        calculatedDijkstra.setCosts(costs);
        calculatedDijkstra.setPredecessors(predecessors);
        calculatedDijkstra.setEdges(edges);
        
        calculatedDijkstras.put(orig, calculatedDijkstra);
    }

    private IVertex<PointOfInterest> findLowerCostVertex(Set<IVertex<PointOfInterest>> unvisited, 
            Map<IVertex<PointOfInterest>, Double> costs) {
        
        double min = Double.MAX_VALUE;
        IVertex<PointOfInterest> minCostVertex = null;
        for (IVertex<PointOfInterest> vertex : unvisited){
            if (costs.get(vertex) <= min){
                minCostVertex = vertex;
                min = costs.get(vertex);
            }
        }
        
        return minCostVertex;
    }
    
    @Override
    public String toString() {
        String returnString = "COURSE MANAGER\n";
        
        if (this.calculatedPath == null) {
            returnString += "\t(the path has not yet been calculated)\n";
        } else {
            returnString += "\tBest (" + this.calculatedPath.getCriteria() + ") path for the selected points of interest (onBike: " + this.calculatedPath.getNavigability() + ")\n";
            returnString += "\tTotal cost (" + this.calculatedPath.getCriteria().getUnit() + ") = " + this.calculatedPath.getCost() + "\n";
            
            returnString += "\tPoints of Interest:\n";
            for (PointOfInterest pointOfInterest : this.calculatedPath.getPointsOfInterest()) {
                returnString += "\t\t" + pointOfInterest + "\n";
            }
            
            returnString += "\tConnections:\n";
            for (Connection connection : this.calculatedPath.getConnections()) {
                returnString += "\t\t" + connection + "\n";
            }
        }
        
        returnString += "\n";
        
        return returnString;
    }
    
}
