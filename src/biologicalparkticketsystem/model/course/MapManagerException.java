package biologicalparkticketsystem.model.course;

/**
 * Class to handle map manager exceptions
 */
public class MapManagerException extends Exception {

    /**
     * Creates a new instance of <code>NewException</code> without detail
     * message.
     */
    public MapManagerException() {
        super("an undefined exception has occurred on mapmanager class");
    }

    /**
     * Constructs an instance of <code>NewException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public MapManagerException(String msg) {
        super(msg);
    }
    
}
