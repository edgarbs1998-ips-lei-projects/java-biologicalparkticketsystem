package digraph;

/**
 * Data-independent representation of a vertex.
 * @param <V> Type of element stored in the vertex.
 */
public interface IVertex<V> {
    
    /**
     * Returns the element (object reference) stored in this vertex.
     * @return Vertex stored element.
     */
    public V element();
    
}
