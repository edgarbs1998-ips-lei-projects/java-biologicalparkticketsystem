/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.DaoManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author golds
 */
public class StatisticsDAOSerialization implements IStatisticsDAO {
    
    private final String basePath;
    private Map<String, Statistics> map;
    private final static String FILENAME = "statistics.dat";
    private final String mapName;
    
    public StatisticsDAOSerialization (String basePath) {
        ConfigManager config = ConfigManager.getInstance();
        this.mapName = config.getProperties().getProperty("map.name");
        
        this.basePath = basePath;
        
        // Create the path folder if it does not exists
        if (!this.basePath.equals("")) {
            File file = new File(this.basePath);
            file.mkdirs();
        }
        
        this.map = new HashMap<>();
        loadAll();
    }
    
    private void loadAll() {
        try {
            FileInputStream fileIn = new FileInputStream(this.basePath + FILENAME);
            ObjectInputStream input = new ObjectInputStream(fileIn);
            this.map = (Map<String, Statistics>) input.readObject();
            input.close();
            fileIn.close();
        } catch (IOException ex) {
        } catch ( ClassNotFoundException ex) {
            // TODO
            Logger.getLogger(InvoiceDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveAll() {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(this.basePath + FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.map);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            // TODO
            Logger.getLogger(StatisticsDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // TODO
            Logger.getLogger(StatisticsDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Statistics getStatistics() {
        Statistics statistics;
        if (this.map.containsKey(this.mapName)) {
            statistics = this.map.get(this.mapName);
        } else {
            statistics = new Statistics();
            statistics.setMapName(this.mapName);
        }
        return statistics;
    }
    
    @Override
    public boolean insertTicket (Ticket ticket, CalculatedPath calculatedPath) {
        Statistics statistics = getStatistics();
        
        // Sold Tickets Price Average
        Collection<Ticket> tickets = DaoManager.getInstance().getTicketDao().selectTickets();
        double sumTicketsPrice = 0.0;
        for (Ticket tempTicket : tickets) {
            sumTicketsPrice += tempTicket.getTotalCost();
        }
        statistics.setSoldTicketsPriceAverage(sumTicketsPrice / tickets.size());
        
        // Sold Bike-Foot Tickets
        if (ticket.getPathType() == 0) {
            statistics.setSoldFootTickets(statistics.getSoldFootTickets() + 1);
        } else {
            statistics.setSoldBikeTickets(statistics.getSoldBikeTickets() + 1);
        }
        
        // Total Pois Visits
        Map<Integer, Integer> totalPoisVisits = statistics.getTotalPoisVisits();
        if (totalPoisVisits == null) {
            totalPoisVisits = new HashMap<>();
        }
        for (PointOfInterest poi : calculatedPath.getMustVisit()) {
            int poiId = poi.getPoiId();
            
            if (totalPoisVisits.containsKey(poiId)) {
                totalPoisVisits.put(poiId, totalPoisVisits.get(poiId) + 1);
            } else {
                totalPoisVisits.put(poiId, 1);
            }
        }
        statistics.setTotalPoisVisits(totalPoisVisits);
        
        map.put(this.mapName, statistics);
        saveAll();
        return true;
    }
    
    @Override
    public int getSoldBikeTickets() {
        Statistics statistics = getStatistics();
        return statistics.getSoldBikeTickets();
    }
    
    @Override
    public int getSoldFootTickets() {
        Statistics statistics = getStatistics();
        return statistics.getSoldFootTickets();
    }
    
    @Override
    public double getSoldTicketsPriceAverage() {
        Statistics statistics = getStatistics();
        return statistics.getSoldTicketsPriceAverage();
    }
    
    @Override
    public Map<Integer, Integer> getTop10VisitedPois() {
        Statistics statistics = getStatistics();
        Map<Integer, Integer> totalPoisVisits = statistics.getTotalPoisVisits();
        
        return totalPoisVisits.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (eq, e2) -> e2, LinkedHashMap::new
                ));
    }
    
}
