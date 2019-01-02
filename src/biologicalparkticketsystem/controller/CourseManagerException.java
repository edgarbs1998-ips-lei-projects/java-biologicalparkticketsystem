package biologicalparkticketsystem.controller;

public class CourseManagerException extends RuntimeException {

    /**
     * Course manager exception with a custom error message
     * @param string
     */
    public CourseManagerException(String string) {
        super(string);
    }
    
}
