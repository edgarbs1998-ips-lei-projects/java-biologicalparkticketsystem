package biologicalparkticketsystem.model.course;

import java.util.Stack;

public class CalculatedPathCareTaker {
    
    private final Stack<CalculatedPathMemento> mementoStack;
    
    public CalculatedPathCareTaker() {
        mementoStack = new Stack<>();
    }
    
    public void saveState(CalculatedPath calculatedPath) {
        CalculatedPathMemento calculatedPathMemento = calculatedPath.createMomento();
        mementoStack.push(calculatedPathMemento);
    }
    
    public void restoreState(CalculatedPath calculatedPath) {
        if (mementoStack.isEmpty()) return;
        
        CalculatedPathMemento calculatedPathMemento = mementoStack.pop();
        calculatedPath.setMemento(calculatedPathMemento);
    }
    
    public void clearStates() {
        mementoStack.clear();
    }
    
    public int countStates() {
        return mementoStack.size();
    }
    
}
