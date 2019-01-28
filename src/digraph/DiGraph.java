package digraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ADT Graph implementation that stores a collection of edges (and vertices) and
 * where each edge contains the references for the vertices it connects.
 *
 * Does not allow duplicates of stored elements through <b>equals</b> criteria.
 */
public class DiGraph<V, E> implements IDiGraph<V, E> {
    
    // Attributes
    private Map<V, IVertex<V>> vertices;
    private Map<E, List<IEdge<E, V>>> edges;

    /**
     * Creates a empty graph.
     */
    public DiGraph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Iterable<IVertex<V>> vertices() {
        List<IVertex<V>> vertexList = new ArrayList<>();
        for (IVertex<V> v : vertices.values()) {
            vertexList.add(v);
        }
        return vertexList;
    }

    @Override
    public Iterable<IEdge<E, V>> edges() {
        List<IEdge<E, V>> edgeList = new ArrayList<>();
        for (List<IEdge<E, V>> eList : edges.values()) {
            for (IEdge<E, V> e : eList) {
                edgeList.add(e);
            }
        }
        return edgeList;
    }
    
    @Override
    public Iterable<IEdge<E, V>> accedentEdges(IVertex<V> v) throws InvalidEdgeException {
        
        checkVertex(v);
        
        List<IEdge<E,V>> acedentEdges = new ArrayList<>();
        for (List<IEdge<E, V>> edgeList : edges.values()) {
            for (IEdge<E, V> edge : edgeList) {
                if (edge.vertexOutbound() == v) {
                    acedentEdges.add(edge);
                }
            }
        }
    
        return acedentEdges;
        
    }

    @Override
    public Iterable<IEdge<E, V>> incidentEdges(IVertex<V> v) throws InvalidEdgeException {
        
        checkVertex(v);
        
        List<IEdge<E,V>> incidentEdges = new ArrayList<>();
        for (List<IEdge<E, V>> edgeList : edges.values()) {
            for (IEdge<E, V> edge :edgeList) {
                if (edge.vertexInbound() == v) {
                    incidentEdges.add(edge);
                }
            }
        }
    
        return incidentEdges;
        
    }

    @Override
    public IVertex<V> opposite(IVertex<V> v, IEdge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        
        checkVertex(v);
        Edge edge = checkEdge(e);
        
        // Check if this edge connects to vertex v
        if(edge.vertexOutbound() != v) {
            return null;
        }
        
        return edge.vertexInbound();
        
    }

    @Override
    public boolean areAdjacent(IVertex<V> u, IVertex<V> v) throws InvalidVertexException {
        //we allow loops, so we do not check if u == v
        checkVertex(u);
        checkVertex(v);
        
        /* find an edge that contains both u and v */
        for (List<IEdge<E, V>> edgeList : edges.values()) {
            for (IEdge<E, V> edge : edgeList) {
                if(edge.vertexOutbound() == u && edge.vertexInbound() == v) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IVertex<V> insertVertex(V vElement) {
        if (existsVertexWith(vElement)) {
            throw new IllegalArgumentException("There's already a vertex with this element.");
        }

        Vertex newVertex = new Vertex(vElement);

        vertices.put(vElement, newVertex);

        return newVertex;
    }

    @Override
    public IEdge<E, V> insertEdge(IVertex<V> u, IVertex<V> v, E edgeElement) throws InvalidVertexException {
        
        Edge oldEdge = null;

        if (existsEdgeWith(edgeElement)) {
            List<IEdge<E, V>> oldEdges = edges.get(edgeElement);
            
            if (oldEdges.size() != 1) {
                throw new IllegalArgumentException("There's already an edge with this element and direction.");
            }
            
            oldEdge = checkEdge(oldEdges.get(0));
            if (oldEdge.vertexOutbound() != v || oldEdge.vertexInbound() != u) {
                throw new IllegalArgumentException("There's already an edge with this element and direction.");
            }
        }

        Vertex vertexOutbound = checkVertex(u);
        Vertex vertexInbound = checkVertex(v);

        Edge newEdge = new Edge(edgeElement, vertexOutbound, vertexInbound);
        
        List<IEdge<E, V>> newEdges = new ArrayList<>();
        newEdges.add(newEdge);
        
        if (oldEdge != null) {
            newEdges.add(oldEdge);
        }

        edges.put(edgeElement, newEdges);

        return newEdge;

    }

    @Override
    public IEdge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) throws InvalidVertexException {
        if (!existsVertexWith(vElement1)) {
            throw new InvalidVertexException("No vertex contains " + vElement1);
        }
        if (!existsVertexWith(vElement2)) {
            throw new InvalidVertexException("No vertex contains " + vElement2);
        }
        
        return insertEdge(vertexOf(vElement1), vertexOf(vElement2), edgeElement);

    }

    @Override
    public V removeVertex(IVertex<V> v) throws InvalidVertexException {
        checkVertex(v);

        V element = v.element();
        
        //remove incident edges
        Iterable<IEdge<E, V>> incidentEdges = incidentEdges(v);
        for (IEdge<E, V> edge : incidentEdges) {
            edges.remove(edge.element());
        }
        //remove acedent edges
        Iterable<IEdge<E, V>> acedentEdges = accedentEdges(v);
        for (IEdge<E, V> edge : acedentEdges) {
            edges.remove(edge.element());
        }
        
        vertices.remove(v.element());
        
        return element; 
    }

    @Override
    public E removeEdge(IEdge<E, V> e) throws InvalidEdgeException {
        checkEdge(e);

        E element = e.element();
        edges.remove(e.element());

        return element;
    }

    @Override
    public V replace(IVertex<V> v, V newElement) throws InvalidVertexException {
        if (existsVertexWith(newElement)) {
            throw new IllegalArgumentException("There's already a vertex with this element.");
        }

        Vertex vertex = checkVertex(v);

        V oldElement = (V) vertex.element();
        vertex.setElement(newElement);

        return oldElement;
    }

    @Override
    public E replace(IEdge<E, V> e, E newElement) throws InvalidEdgeException {
        if (existsEdgeWith(newElement)) {
            throw new IllegalArgumentException("There's already an edge with this element.");
        }

        Edge edge = checkEdge(e);

        E oldElement = (E) edge.element();
        edge.setElement(newElement);

        return oldElement;
    }
    
    /**
     * Retrieve a vertex by its element
     * @param element
     * @return vertex
     */
    public IVertex<V> getVertexByElement(V element) {
        return vertices.get(element);
    }
    
    /**
     * Retrieve a edge by its edge and vertex
     * @param edge
     * @param vertex
     * @return edge
     */
    public IEdge<E, V> getEdgeByElement(E edge, V vertex) {
        IVertex<V> tempVertex = getVertexByElement(vertex);
        List<IEdge<E, V>> tempEdge = edges.get(edge);
        
        if (tempEdge.get(0).vertexOutbound() == tempVertex) {
            return tempEdge.get(0);
        } else {
            return tempEdge.get(1);
        }
    }

    private Vertex vertexOf(V vElement) {
        for (IVertex<V> v : vertices.values()) {
            if (v.element().equals(vElement)) {
                return (Vertex) v;
            }
        }
        return null;
    }

    private boolean existsVertexWith(V vElement) {
        return vertices.containsKey(vElement);
    }
    
    private boolean existsEdgeWith(E edgeElement) {
        return edges.containsKey(edgeElement);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( 
                String.format("Graph with %d vertices and %d edges:\n", numVertices(), numEdges())
        );
        
        sb.append( "--- Vertices: \n" );
        for (IVertex<V> v : vertices.values()) {
            sb.append("\t").append( v.toString() ).append("\n");
        }
        sb.append( "\n--- Edges: \n");
        for (List<IEdge<E, V>> eList : edges.values()) {
            for (IEdge<E, V> e : eList) {
                sb.append("\t").append( e.toString() ).append("\n");
            }
        }
        return sb.toString();
    }
    

    /**
     * Checks whether a given vertex is valid and belongs to this graph
     *
     * @param v
     * @return
     * @throws InvalidVertexException
     */
    private Vertex checkVertex(IVertex<V> v) throws InvalidVertexException {

        Vertex vertex;
        try {
            vertex = (Vertex) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("Not a vertex.");
        }

        if (!vertices.containsKey((V) vertex.element())) {
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }

        return vertex;
    }

    private Edge checkEdge(IEdge<E, V> e) throws InvalidEdgeException {

        Edge edge;
        try {
            edge = (Edge) e;
        } catch (ClassCastException ex) {
            throw new InvalidVertexException("Not an adge.");
        }

        if (!edges.containsKey((E) edge.element())) {
            throw new InvalidEdgeException("Edge does not belong to this graph.");
        }

        return edge;
    }
}
