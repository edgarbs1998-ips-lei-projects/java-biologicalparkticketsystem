package digraph;

/**
 * Class to handle invalid edge exceptions
 */
public class InvalidEdgeException extends RuntimeException {

    /**
     * Invalid edge exception with a default error message
     */
    public InvalidEdgeException() {
        super("The edge is invalid or does not belong to this graph.");
    }

    /**
     * Invalid edge exception with  custom error message
     * @param string
     */
    public InvalidEdgeException(String string) {
        super(string);
    }
    
}
