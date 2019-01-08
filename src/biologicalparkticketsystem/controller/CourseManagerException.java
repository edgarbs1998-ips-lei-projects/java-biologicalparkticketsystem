/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.controller;

/**
 *
 * @author golds
 */
public class CourseManagerException extends Exception {

    /**
     * Creates a new instance of <code>NewException</code> without detail
     * message.
     */
    public CourseManagerException() {
        super("an undefined exception has occurred on coursemanager class");
    }

    /**
     * Constructs an instance of <code>NewException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CourseManagerException(String msg) {
        super(msg);
    }
}
