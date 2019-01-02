/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digraph;

/**
 * Interface that describes the behavior of the Graph ADT.
 * @param <E> Type of values stored in edges
 * @param <V> Type of values stored in vertices
 */
public interface IDiGraph<V, E> {

    /**
     * Returns the number of vertices of the graph
     * @return vertex count
     */
    public int numVertices();

    /**
     * Returns the number of edges of the graph
     * @return edge count
     */
    public int numEdges();

    /**
     * Returns the vertices of the graph as an iterable collection
     * @return set of vertices
     */
    public Iterable<IVertex<V>> vertices();

    /**
     * Returns the edges of the graph as an iterable collection.
     * @return set of edges
     */
    public Iterable<IEdge<E, V>> edges();
    
    /**
     * Returns a vertex's accedent edges as an iterable collection.
     * @param v
     * @return set of vertices
     */
    public Iterable<IEdge<E, V>> accedentEdges(IVertex<V> v)
            throws InvalidEdgeException;

    /**
     * Returns a vertex's incident edges as an iterable collection.
     * @param v
     * @return set of vertices
     */
    public Iterable<IEdge<E, V>> incidentEdges(IVertex<V> v)
            throws InvalidEdgeException;

    /**
     * Given a vertex and an edge, returns the opposite vertex.
     * @param v a vertex
     * @param e an edge
     * @return the opposite vertex 
     * @exception InvalidVertexException if the vertex is invalid for the graph.
     * @exception InvalidEdgeException if the edge is invalid for the graph.
     */
    public IVertex<V> opposite(IVertex<V> v, IEdge<E, V> e)
            throws InvalidVertexException, InvalidEdgeException;

    /**
     * Tests whether two vertices are adjacent.
     * @param u a vertex (outbound, if digraph)
     * @param v another vertex (inbound, if digraph)
     * @return true if they are adjacent, false otherwise.
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public boolean areAdjacent(IVertex<V> u, IVertex<V> v)
            throws InvalidVertexException;

    /**
     * Inserts a new vertex with a given element, returning its reference.
     * @param vElement the element to store at the vertex. Cannot be null.
     * @return the reference of the newly created vertex
     */
    public IVertex<V> insertVertex(V vElement);

    /**
     * Inserts a new edge with a given element between two vertices, returning its reference.
     * @param u a vertex (outbound, if digraph)
     * @param v another vertex (inbound, if digraph)
     * @param edgeElement the element to store in the new edge
     * @return the reference for the newly created edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public IEdge<E, V> insertEdge(IVertex<V> u, IVertex<V> v, E edgeElement)
            throws InvalidVertexException;

    
    /**
     * Inserts a new edge with a given element between two vertices, returning its reference.
     * @param vElement1 element to store in the vertex (outbound, if digraph)
     * @param vElement2 element to store in the another vertex (inbound, if digraph)
     * @param edgeElement the element to store in the new edge
     * @return the reference for the newly created edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public IEdge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement)
            throws InvalidVertexException;

    /**
     * Removes a vertex and all its incident edges and returns the element
     * stored at the removed vertex.
     * @param v vertex to remove
     * @return element from the removed vertex
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public V removeVertex(IVertex<V> v) throws InvalidVertexException;

    /**
     * Removes an edge and return its element.
     * @param e the edge to remove
     * @return the element from the removed edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public E removeEdge(IEdge<E, V> e) throws InvalidEdgeException;
    
    /**
     * Replaces the element of a given vertex with a new element and returns the
     * old element.
     * @param v the vertex
     * @param newElement new element to store in v
     * @return the old element previously stored in v
     * @exception InvalidVertexException if the vertex is invalid for the graph.
     */
    public V replace(IVertex<V> v, V newElement) throws InvalidVertexException;
    
    /**
     * Replaces the element of a given edge with a new element and returns the
     * old element.
     * @param e the edge
     * @param newElement new element to store in e
     * @return the old element previously stored in e
     * @exception InvalidEdgeException if the edge is invalid for the graph.
     */
    public E replace(IEdge<E, V> e, E newElement) throws InvalidEdgeException;
    
}
