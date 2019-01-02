package digraph;

/**
 * Data-independent representation of an edge.
 * @param <E> Type of element stored in the edge
 * @param <V> Type of element stored in the vertices that this edge connects.
 */
public interface IEdge<E, V> {
    
    /**
     * Returns the element (object reference) stored in this edge.
     * @return Edge stored element.
     */
    public E element();
    
    /** 
     * Returns reference of the outbound/start vertex that this edge
     * connects to.
     * @return A vertex.
     */
    public IVertex<V> vertexOutbound();
    
    /**
     * Returns reference of the inbound/end vertex that this edge connects to.
     * @return A vertex.
     */
    public IVertex<V> vertexInbound();
    
}
