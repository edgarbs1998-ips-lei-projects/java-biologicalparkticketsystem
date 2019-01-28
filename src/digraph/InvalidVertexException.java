package digraph;

/**
 * Class to handle invalid vertex exceptions
 */
public class InvalidVertexException extends RuntimeException {

    /**
     * Invalid vertex exception with a default error message
     */
    public InvalidVertexException() {
        super("The vertex is invalid or does not belong to this graph.");
    }

    /**
     * Invalid vertex exception with a custom error message
     * @param string
     */
    public InvalidVertexException(String string) {
        super(string);
    }
    
}
