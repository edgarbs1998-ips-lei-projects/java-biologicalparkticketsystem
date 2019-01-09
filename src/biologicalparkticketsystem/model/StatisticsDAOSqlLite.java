/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.DaoManager;
import biologicalparkticketsystem.controller.LoggerManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.controller.MapManagerException;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author golds
 */
public class StatisticsDAOSqlLite implements IStatisticsDAO {
    
    private MapManager mapManager;
    private Connection connection;
    private final String mapName;
    
    public StatisticsDAOSqlLite(String dbFile, MapManager mapManager) {
        this.mapManager = mapManager;
        
        ConfigManager config = ConfigManager.getInstance();
        this.mapName = config.getProperties().getProperty("map.name");
        
        // Create the path folder if it does not exists
        if (!dbFile.equals("")) {
            File file = new File(dbFile);
            file.getParentFile().mkdirs();
        }
        
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            
            Statement stmt = this.connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Statistics (\n"
                    + " id INTEGER PRIMARY KEY NOT NULL,\n"
                    + " mapName TEXT NOT NULL,\n"
                    + " soldTicketsPriceAverage REAL NOT NULL,\n"
                    + " soldBikeTickets INTEGER NOT NULL,\n"
                    + " soldFootTickets INTEGER NOT NULL\n"
                    + ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS StatisticsPoiVisits (\n"
                    + " id INTEGER PRIMARY KEY NOT NULL,\n"
                    + " mapName TEXT NOT NULL,\n"
                    + " poiId INTEGER NOT NULL,\n"
                    + " visits INTEGER NOT NULL\n"
                    + ");");
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    private Statistics getStatistics() {
        Statistics statistics = new Statistics();
        statistics.setMapName(this.mapName);
        
        try {
            Statement stmt = this.connection.createStatement();
            String sql = "SELECT soldTicketsPriceAverage, soldBikeTickets, soldFootTickets FROM Statistics WHERE mapName LIKE '" + this.mapName + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    statistics.setSoldBikeTickets(rs.getInt("soldBikeTickets"));
                    statistics.setSoldFootTickets(rs.getInt("soldFootTickets"));
                    statistics.setSoldTicketsPriceAverage(rs.getDouble("soldTicketsPriceAverage"));

                    Statement stmtp = this.connection.createStatement();
                    String sqlp = "SELECT poiId, visits FROM StatisticsPoiVisits WHERE mapName LIKE '" + this.mapName + "'";
                    Map<Integer, Integer> totalPoisVisits = new HashMap<>();
                    try (ResultSet rsp = stmtp.executeQuery(sqlp)) {
                        while (rsp.next()) {
                            totalPoisVisits.put(rsp.getInt("poiId"), rsp.getInt("visits"));
                        }
                    }
                    statistics.setTotalPoisVisits(totalPoisVisits);
                }
            }
        } catch (SQLException ex) {
            statistics = new Statistics();
            statistics.setMapName(this.mapName);
            LoggerManager.getInstance().log(ex);
        }
        
        return statistics;
    }
    
    private void updateStatistics(Statistics statistics) {
        String sql = "DELETE FROM Statistics WHERE mapName LIKE ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, this.mapName);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
        
        sql = "DELETE FROM StatisticsPoiVisits WHERE mapName LIKE ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, this.mapName);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
        
        sql = "INSERT INTO Statistics (mapName, soldTicketsPriceAverage, soldBikeTickets, soldFootTickets) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, this.mapName);
            pstmt.setDouble(2, statistics.getSoldTicketsPriceAverage());
            pstmt.setInt(3, statistics.getSoldBikeTickets());
            pstmt.setInt(4, statistics.getSoldFootTickets());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
        
        for (int poi : statistics.getTotalPoisVisits().keySet()) {
            sql = "INSERT INTO StatisticsPoiVisits (mapName, poiId, visits) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
                pstmt.setString(1, this.mapName);
                pstmt.setInt(2, poi);
                pstmt.setInt(3, statistics.getTotalPoisVisits().get(poi));
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                LoggerManager.getInstance().log(ex);
            }
        }
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
        
        updateStatistics(statistics);
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
    public Map<PointOfInterest, Integer> getTop10VisitedPois() {
        Statistics statistics = getStatistics();
        Map<Integer, Integer> totalPoisVisits = statistics.getTotalPoisVisits();
        
        // TODO Improve code
        Map<PointOfInterest, Integer> newTotalPoisVisits = new HashMap<>();
        for (int poiId : totalPoisVisits.keySet()) {
            try {
                PointOfInterest poi = mapManager.getPointOfInterestById(poiId);
                newTotalPoisVisits.put(poi, totalPoisVisits.get(poiId));
            } catch (MapManagerException ex) {
                LoggerManager.getInstance().log(ex);
            }
        }
        
        return newTotalPoisVisits.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (eq, e2) -> e2,
                        LinkedHashMap::new
                ));
    }
    
}
