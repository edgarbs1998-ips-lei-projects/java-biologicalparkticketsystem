package biologicalparkticketsystem.model.course;

import java.util.Stack;

/**
 * Class to take care of calculated paths snapshots
 */
public class CalculatedPathCareTaker {
    
    private final Stack<CalculatedPathMemento> mementoStack;
    
    public CalculatedPathCareTaker() {
        mementoStack = new Stack<>();
    }
    
    /**
     * Method to save state of current calculated path object on a stack
     * @param calculatedPath calculated path instance
     */
    public void saveState(CalculatedPath calculatedPath) {
        CalculatedPathMemento calculatedPathMemento = calculatedPath.createMomento();
        mementoStack.push(calculatedPathMemento);
    }
    
    /**
     * Method to restore state of passed calculated path object
     * @param calculatedPath calculated path instance
     */
    public void restoreState(CalculatedPath calculatedPath) {
        if (mementoStack.isEmpty()) return;
        
        CalculatedPathMemento calculatedPathMemento = mementoStack.pop();
        calculatedPath.setMemento(calculatedPathMemento);
    }
    
    /**
     * Method to clear all previous saved states
     */
    public void clearStates() {
        mementoStack.clear();
    }
    
    /**
     * Method to count the amount of saved states
     * @return size of stack
     */
    public int countStates() {
        return mementoStack.size();
    }
    
}
