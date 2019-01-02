package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.CalculatedDijkstra;
import biologicalparkticketsystem.model.CalculatedPath;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.ConnectionBridge;
import biologicalparkticketsystem.model.ConnectionPath;
import biologicalparkticketsystem.model.PointOfInterest;
import digraph.DiGraph;
import digraph.IEdge;
import digraph.IVertex;
import digraph.InvalidVertexException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
            switch(this) {
                case COST: return "€";
                case DISTANCE: return "Meters";
            }
            return "Unknown";
        }
    };
    
    private DiGraph<PointOfInterest, Connection> digraph;
    private CalculatedPath calculatedPath;

    /**
     * Course manager constructor
     */
    public CourseManager() {
        this.digraph = new DiGraph<>();
    }
    
    public DiGraph<PointOfInterest, Connection> getDigraph() {
        return this.digraph;
    }
    
    public void loadCourseMapFile(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            
            Map<Integer, PointOfInterest> loadedPOIs = new HashMap<>(); // Used for performance optimization
            
            // Add POIs
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
            }
            int numberPOIs = scanner.nextInt();
            scanner.nextLine();
            int countPOIs = 0;
            while (countPOIs < numberPOIs && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(", ");
                if (fields.length != 2) {
                    throw new Exception("Expected a POI with 2 fields while reading park map file");
                }
                PointOfInterest poi = new PointOfInterest(
                        Integer.parseInt(fields[0]),
                        fields[1]
                );
                addPointOfInterest(poi);
                loadedPOIs.put(poi.getPoiId(), poi);
                
                ++countPOIs;
            }
            if (numberPOIs != countPOIs) {
                throw new Exception("Expected " + numberPOIs + " POIs from park map file but found " + countPOIs);
            }
            
            // Add connections
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
            }
            int numberConnections = scanner.nextInt();
            scanner.nextLine();
            int countConnections = 0;
            while (countConnections < numberConnections && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(", ");
                if (fields.length != 8) {
                    throw new Exception("Expected a Connection with 8 fields while reading park map file");
                }
                Connection con = null;
                switch (fields[1]) {
                    case "ponte": 
                        con = new ConnectionBridge(
                            Integer.parseInt(fields[0]),
                            fields[2],
                            Integer.parseInt(fields[6]),
                            Integer.parseInt(fields[7]),
                            Boolean.parseBoolean(fields[5])
                        );
                        break;
                        
                    case "caminho":
                        con = new ConnectionPath(
                            Integer.parseInt(fields[0]),
                            fields[2],
                            Integer.parseInt(fields[6]),
                            Integer.parseInt(fields[7]),
                            Boolean.parseBoolean(fields[5])
                        );
                        break;
                }
                int startPoiId = Integer.parseInt(fields[3]);
                int endPoiId = Integer.parseInt(fields[4]);
                addConnection(loadedPOIs.get(startPoiId), loadedPOIs.get(endPoiId), con);
                
                ++countConnections;
            }
            if (numberConnections != countConnections) {
                throw new Exception("Expected " + numberConnections + " Connections from park map file but found " + countConnections);
            }
        } catch (FileNotFoundException ex) {
            throw new CourseManagerException("Specified park map file not found (" + ex.getMessage() + ")");
        } catch (Exception ex) {
            throw new CourseManagerException(ex.getMessage());
        }
    }
    
    public PointOfInterest getPointOfInterestById(int id) throws CourseManagerException {
        IVertex<PointOfInterest> find = null;
        
        for (IVertex<PointOfInterest> v : digraph.vertices()) {
            if (v.element().getPoiId() == id) {
                find = v;
                break;
            }
        }
        
        if(find == null) {
            throw new CourseManagerException("Point of interest with id (" + id + ") does not exist");
        }
        
        return find.element();
    }
    
    private IVertex<PointOfInterest> checkPointOfInterest(PointOfInterest poi) throws CourseManagerException {
        
        if( poi == null) 
            throw new CourseManagerException("Point of interest cannot be null");
        
        IVertex<PointOfInterest> find = null;
        for (IVertex<PointOfInterest> v : digraph.vertices()) {
            if( v.element().equals(poi)) { //equals was overriden in PointOfInterest!!
                find = v;
            }
        }
        
        if( find == null) 
            throw new CourseManagerException("Point of interest with id (" + poi.getPoiId() + ") does not exist");
        
        return find;
    }
    
    /**
     * Adds a point of interest
     * @param poi
     * @throws CourseManagerException
     */
    public void addPointOfInterest(PointOfInterest poi) throws CourseManagerException{
        if( poi == null ) throw new CourseManagerException("Point of interest cannot be null");
        
        try {
            digraph.insertVertex(poi);
        } catch (InvalidVertexException e) {
            throw new CourseManagerException("Point of interest with id (" + poi.getPoiId() + ") already exists");
            
        }
    }
    
    /**
     * Method to add a connection
     * @param poi1
     * @param poi2
     * @param connection
     * @throws CourseManagerException
     */
    public void addConnection(PointOfInterest poi1, PointOfInterest poi2, Connection connection) throws CourseManagerException{
        
        if( connection == null) 
            throw new CourseManagerException("Connection is null");
        
        IVertex<PointOfInterest> p1 = checkPointOfInterest(poi1);
        IVertex<PointOfInterest> p2 = checkPointOfInterest(poi2);
        
        try {
            digraph.insertEdge(p1, p2, connection);
            if (connection instanceof ConnectionPath) {
                digraph.insertEdge(p2, p1, connection);
            }
        } catch (InvalidVertexException e) {
            throw new CourseManagerException("The connection (" + connection.getConnectionName() + ") already exists");
        }
    }
    
    /**
     * Get a connection between 
     * @param poi1
     * @param poi2
     * @return
     * @throws CourseManagerException
     */
    public List<Connection> getConnectionsBetween(PointOfInterest poi1, PointOfInterest poi2) throws CourseManagerException {
        
        List<Connection> connectionList = new ArrayList<>();
        
        IVertex<PointOfInterest> p1 = checkPointOfInterest(poi1);
        IVertex<PointOfInterest> p2 = checkPointOfInterest(poi2);
        
        if (digraph.areAdjacent(p1, p2)) {
            for (IEdge<Connection, PointOfInterest> e1 : digraph.accedentEdges(p1)) {
                for (IEdge<Connection, PointOfInterest> e2 : digraph.incidentEdges(p2)) {
                    if (e1 == e2) {
                        connectionList.add(e1.element());
                        break;
                    }
                }
            }
        }
        
        return connectionList;
    }
    
    @Override
    public String toString() {
        String returnString = "COURSE MANAGER (" + digraph.numVertices() + " Points of Interest | " + digraph.numEdges() + " Connections)\n";
        for(IVertex<PointOfInterest> p1 : digraph.vertices()){            
            for(IVertex<PointOfInterest> p2 : digraph.vertices()){
                if(p1 == p2){
                    continue;
                }
                
                returnString += "\t" + p1.element().toString() + " TO " + p2.element().toString() + "\n";

                List<Connection> connections = getConnectionsBetween(p1.element(), p2.element());
                if(connections.isEmpty()) {
                    returnString += "\t\t(no connections)\n";
                } else {
                    for (Connection connection : connections) {
                        returnString += "\t\t" + connection + "\n";
                    }
                }
                
                returnString += "\n";
            }
        }
        
        return returnString;
    }
    
    /**
     * Calculates the minimum cost path
     * @param criteria
     * @param allowBike
     * @param start
     * @param mustVisitPois
     * @return the cost of the lowest cost path
     * @throws CourseManagerException
     */
    public CalculatedPath minimumCriteriaPath(Criteria criteria,
            boolean allowBike,
            PointOfInterest start,
            List<PointOfInterest> mustVisitPois)
        throws CourseManagerException {
        
        this.calculatedPath = new CalculatedPath();
        
        IVertex<PointOfInterest> startPoi = checkPointOfInterest(start);

        Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras = new HashMap<>();
        
        dijkstraAlgorithm(criteria, allowBike, startPoi, calculatedDijkstras);
        for (PointOfInterest poi : mustVisitPois) {
            IVertex<PointOfInterest> vertexPoi = checkPointOfInterest(poi);
            dijkstraAlgorithm(criteria, allowBike, vertexPoi, calculatedDijkstras);
        }
        
        heapsAlgorithm(mustVisitPois.size(), mustVisitPois, startPoi, calculatedDijkstras);
        
        return this.calculatedPath;
    }
    
    private void heapsAlgorithm(int n, List<PointOfInterest> mustVisitPois,
            IVertex<PointOfInterest> startPoi,
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras) {
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
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras) {
        
        CalculatedPath tempCalculatedPath = new CalculatedPath();
        int cost = 0;
        
        IVertex<PointOfInterest> origin = startPoi;
        IVertex<PointOfInterest> destination;
        for (PointOfInterest poi : mustVisitPois) {
            destination = checkPointOfInterest(poi);
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
            List<Connection> connections)
        throws CourseManagerException {
        
        List<PointOfInterest> tempPois = new ArrayList<>();
        List<Connection> tempConnections = new ArrayList<>();
        
        CalculatedDijkstra calculatedDijkstra = calculatedDijkstras.get(origin);
        
        int cost = (int) Math.round(calculatedDijkstra.getCosts().get(destination));
        
        while (destination != origin) {
            tempPois.add(0, destination.element());
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
            boolean allowBike,
            IVertex<PointOfInterest> orig,
            Map<IVertex<PointOfInterest>, CalculatedDijkstra> calculatedDijkstras) {
        
        Map<IVertex<PointOfInterest>, Double> costs = new HashMap<>();
        Map<IVertex<PointOfInterest>, IVertex<PointOfInterest>> predecessors = new HashMap<>();
        Map<IVertex<PointOfInterest>, IEdge<Connection, PointOfInterest>> edges = new HashMap<>();
        
        Set<IVertex<PointOfInterest>> visited = new HashSet<>();
        Set<IVertex<PointOfInterest>> unvisited = new HashSet<>();
        
        for (IVertex<PointOfInterest> vertex : digraph.vertices()) {
            costs.put(vertex, Double.MAX_VALUE);
            predecessors.put(vertex, null);
            edges.put(vertex, null);
        }
        costs.put(orig, 0.0);
        unvisited.add(orig);
        
        while (!unvisited.isEmpty()) {
            IVertex<PointOfInterest> lowerCostVertex = findLowerCostVertex(unvisited, costs);
            unvisited.remove(lowerCostVertex);
            for (IEdge<Connection, PointOfInterest> edge : digraph.accedentEdges(lowerCostVertex)) {
                if (allowBike == false || (allowBike == true && edge.element().getConnectionNavigability() == allowBike)) {
                    IVertex<PointOfInterest> opposite = digraph.opposite(lowerCostVertex, edge);
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
    
}
