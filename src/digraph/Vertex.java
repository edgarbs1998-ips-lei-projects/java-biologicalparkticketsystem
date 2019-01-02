package digraph;

/**
 * Data-independent representation of a vertex.
 * @param <V> Type of element stored in the vertex.
 */
public class Vertex<V> implements IVertex<V> {
    
    // Attributes
    private V element;
    
    /**
     * @param element Element stored in the vertex.
     */
    public Vertex(V element) {
        this.element = element;
    }
    
    @Override
    public V element() {
        return this.element;
    }
    
    /**
     * Set a new element to the vertex.
     * @param element New element to store in the vertex.
     */
    public void setElement(V element) {
        this.element = element;
    }
    
    /**
     * Returns a string representation of the vertex.
     * @return A string representing the vertex.
     */
    @Override
    public String toString() {
        return "Vertex{" + element + "}";
    }
    
}
