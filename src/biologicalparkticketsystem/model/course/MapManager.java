package biologicalparkticketsystem.model.course;

import biologicalparkticketsystem.LoggerManager;
import digraph.DiGraph;
import digraph.IEdge;
import digraph.IVertex;
import digraph.InvalidVertexException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapManager {
    
    private DiGraph<PointOfInterest, Connection> digraph;
    private PointOfInterest startPoint;

    /**
     * Course manager constructor
     * @param mapFilePath
     */
    public MapManager(String mapFilePath) throws MapManagerException {
        this.digraph = new DiGraph<>();
        this.startPoint = null;
        
        this.loadMapFile(mapFilePath);
    }
    
    public DiGraph<PointOfInterest, Connection> getDiGraph() {
        return this.digraph;
    }
    
    public PointOfInterest getStartPoint() {
        return this.startPoint;
    }
    
    private void loadMapFile(String mapFilePath) throws MapManagerException {
        try {
            File mapFile = new File(mapFilePath);
            Scanner scanner = new Scanner(mapFile);

            Map<Integer, PointOfInterest> loadedPOIs = new LinkedHashMap<>(); // Used for performance optimization

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
                    throw new MapManagerException("Expected a POI with 2 fields while reading park map file");
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
                throw new MapManagerException("Expected " + numberPOIs + " POIs from park map file but found " + countPOIs);
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
                    throw new MapManagerException("Expected a Connection with 8 fields while reading park map file");
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
                throw new MapManagerException("Expected " + numberConnections + " Connections from park map file but found " + countConnections);
            }

            // Set startPoint
            this.startPoint = loadedPOIs.entrySet().iterator().next().getValue();
        } catch (FileNotFoundException ex) {
            throw new MapManagerException("Specified park map file not found (" + ex.getMessage() + ")");
        } catch (Exception ex) {
            throw new MapManagerException(ex.getMessage());
        }
    }
    
    public PointOfInterest getPointOfInterestById(int id) throws MapManagerException {
        IVertex<PointOfInterest> find = null;
        
        for (IVertex<PointOfInterest> v : digraph.vertices()) {
            if (v.element().getPoiId() == id) {
                find = v;
                break;
            }
        }
        
        if(find == null) {
            throw new MapManagerException("Point of interest with id (" + id + ") does not exist");
        }
        
        return find.element();
    }
    
    public IVertex<PointOfInterest> checkPointOfInterest(PointOfInterest poi) throws MapManagerException {
        
        if( poi == null) {
            throw new MapManagerException("Point of interest cannot be null");
        }
        
        IVertex<PointOfInterest> find = null;
        for (IVertex<PointOfInterest> v : digraph.vertices()) {
            if( v.element().equals(poi)) { //equals was overriden in PointOfInterest!!
                find = v;
            }
        }
        
        if( find == null) {
            throw new MapManagerException("Point of interest with id (" + poi.getPoiId() + ") does not exist");
        }
        
        return find;
    }
    
    /**
     * Adds a point of interest
     * @param poi
     * @throws MapManagerException
     */
    private void addPointOfInterest(PointOfInterest poi) throws MapManagerException {
        if( poi == null ) {
            throw new MapManagerException("Point of interest cannot be null");
        }
        
        try {
            digraph.insertVertex(poi);
        } catch (InvalidVertexException e) {
            throw new MapManagerException("Point of interest with id (" + poi.getPoiId() + ") already exists");
        }
    }
    
    /**
     * Method to add a connection
     * @param poi1
     * @param poi2
     * @param connection
     * @throws MapManagerException
     */
    private void addConnection(PointOfInterest poi1, PointOfInterest poi2, Connection connection) throws MapManagerException {
        
        if( connection == null) {
            throw new MapManagerException("Connection is null");
        }
        
        IVertex<PointOfInterest> p1 = checkPointOfInterest(poi1);
        IVertex<PointOfInterest> p2 = checkPointOfInterest(poi2);
        
        try {
            digraph.insertEdge(p1, p2, connection);
            if (connection instanceof ConnectionPath) {
                digraph.insertEdge(p2, p1, connection);
            }
        } catch (InvalidVertexException e) {
            throw new MapManagerException("The connection (" + connection.getConnectionName() + ") already exists");
        }
    }
    
    /**
     * Get a connection between 
     * @param poi1
     * @param poi2
     * @return
     * @throws MapManagerException
     */
    private List<Connection> getConnectionsBetween(PointOfInterest poi1, PointOfInterest poi2) throws MapManagerException {
        
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
        try {
            String returnString = "MAP MANAGER (" + digraph.numVertices() + " Points of Interest | " + digraph.numEdges() + " Connections)\n";
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
        } catch (MapManagerException ex) {
            LoggerManager.getInstance().log(ex);
            return null;
        }
    }
    
}
