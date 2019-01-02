package digraph;

/**
 * Data-independent representation of an edge.
 * @param <E> Type of element stored in the edge
 * @param <V> Type of element stored in the vertices that this edge connects.
 */
public class Edge<E, V> implements IEdge<E, V> {
    
    // Attributes
    private E element;
    private IVertex<V> vertexOutbound;
    private IVertex<V> vertexInbound;
    
    /**
     * @param element Element stored in the edge.
     * @param vertexOutbound Edge outbound vertex.
     * @param vertexInbound Edge inbound vertex.
     */
    public Edge(E element,
            IVertex<V> vertexOutbound,
            IVertex<V> vertexInbound) {
        this.element = element;
        this.vertexOutbound = vertexOutbound;
        this.vertexInbound = vertexInbound;
    }
    
    @Override
    public E element() {
        return this.element;
    }
    
    /**
     * Set a new element to the edge.
     * @param element New element to store in the edge.
     */
    public void setElement(E element) {
        this.element = element;
    }
    
    /**
     * Set a new outbound vertex to the edge.
     * @param vertex New edge outbound vertex.
     */
    public void setVertexOutbound(IVertex<V> vertex) {
        this.vertexOutbound = vertex;
    }
    
    /**
     * Set a new inbound vertex to the edge.
     * @param vertex New edge inbound vertex.
     */
    public void setVertexInbound(IVertex<V> vertex) {
        this.vertexInbound = vertex;
    }
    
    /**
     * Check if edge contains the specified vertex.
     * @param vertex Vertex to check.
     * @return A boolean indicating either the edge contains the passed
     * vertex or not
     */
    public boolean contains(IVertex<V> vertex) {
        return (vertexOutbound == vertex || vertexInbound == vertex);
    }
    
    @Override
    public IVertex<V> vertexOutbound() {
        return this.vertexOutbound;
    }
    
    @Override
    public IVertex<V> vertexInbound() {
        return this.vertexInbound;
    }

    /**
     * Returns a string representation of the edge.
     * @return A string representing the edge.
     */
    @Override
    public String toString() {
        return "Edge{{" + element + "}, vertexOutbound="
                + vertexOutbound.toString() + ", vertexInbound="
                + vertexInbound.toString() + '}';
    }
    
}
