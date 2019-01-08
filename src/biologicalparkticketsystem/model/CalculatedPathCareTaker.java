/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import java.util.Stack;

/**
 *
 * @author goldspy98
 */
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
