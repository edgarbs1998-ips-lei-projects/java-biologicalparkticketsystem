package biologicalparkticketsystem.model.statistic;

import java.io.Serializable;
import java.util.Map;

/**
 * Class to save statistics data
 */
public class Statistics implements Serializable {
    
    private String mapName;
    private double soldTicketsPriceAverage;
    private int soldBikeTickets;
    private int soldFootTickets;
    private Map<Integer, Integer> totalPoisVisits;
    
    public Statistics() { }
    
    public Statistics(String mapName, double soldTicketsPriceAverage, int soldBikeTickets, int soldFootTickets, Map<Integer, Integer> totalPoisVisits) {
        this.mapName = mapName;
        this.soldTicketsPriceAverage = soldTicketsPriceAverage;
        this.soldBikeTickets = soldBikeTickets;
        this.soldFootTickets = soldFootTickets;
        this.totalPoisVisits = totalPoisVisits;
    }
    
    public String getMapName() {
        return this.mapName;
    }
    
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
    
    public double getSoldTicketsPriceAverage() {
        return this.soldTicketsPriceAverage;
    }
    
    public void setSoldTicketsPriceAverage(double soldTicketsPriceAverage) {
        this.soldTicketsPriceAverage = soldTicketsPriceAverage;
    }
    
    public int getSoldBikeTickets() {
        return this.soldBikeTickets;
    }
    
    public void setSoldBikeTickets(int soldBikeTickets) {
        this.soldBikeTickets = soldBikeTickets;
    }
    
    public int getSoldFootTickets() {
        return this.soldFootTickets;
    }
    
    public void setSoldFootTickets(int soldFootTickets) {
        this.soldFootTickets = soldFootTickets;
    }
    
    public Map<Integer, Integer> getTotalPoisVisits() {
        return this.totalPoisVisits;
    }
    
    public void setTotalPoisVisits(Map<Integer, Integer> totalPoisVisits) {
        this.totalPoisVisits = totalPoisVisits;
    }
    
}
