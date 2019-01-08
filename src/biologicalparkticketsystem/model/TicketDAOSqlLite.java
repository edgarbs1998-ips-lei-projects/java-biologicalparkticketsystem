/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

import biologicalparkticketsystem.controller.LoggerManager;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author golds
 */
public class TicketDAOSqlLite implements ITicketDAO {
    
    private Connection connection;
    
    public TicketDAOSqlLite(String dbFile) {
        // Create the path folder if it does not exists
        if (!dbFile.equals("")) {
            File file = new File(dbFile);
            file.getParentFile().mkdirs();
        }
        
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            
            Statement stmt = this.connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Ticket (\n"
                    + " uid TEXT NOT NULL,\n"
                    + " issueDate TEXT NOT NULL,\n"
                    + " clientNif TEXT NOT NULL,\n"
                    + " pathType INTEGER NOT NULL,\n"
                    + " pathCriteria TEXT NOT NULL,\n"
                    + " totalDistance REAL NOT NULL,\n"
                    + " totalCost REAL NOT NULL\n"
                    + ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS TicketItem (\n"
                    + " uid TEXT NOT NULL,\n"
                    + " pathName TEXT NOT NULL,\n"
                    + " fromPoint TEXT NOT NULL,\n"
                    + " toPoint TEXT NOT NULL,\n"
                    + " distance REAL NOT NULL,\n"
                    + " cost REAL NOT NULL\n"
                    + ");");
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    private HashSet<Ticket> selectSQL (String uid) {
        HashSet<Ticket> list = new HashSet<>();
        try {
            Statement stmt = this.connection.createStatement();
            String sql = "SELECT uid, issueDate, clientNif, pathType, pathCriteria, totalDistance, totalCost FROM Ticket";
            if (uid != null) {
                sql += " WHERE uid = '" + uid + "'";
            }
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    
                    ticket.setUid(rs.getString("uid"));
                    ticket.setIssueDate(rs.getString("issueDate"));
                    ticket.setClientNif(rs.getString("clientNif"));
                    ticket.setPathType(rs.getInt("pathType"));
                    ticket.setPathCriteria(rs.getString("pathCriteria"));
                    ticket.setTotalDistance(rs.getDouble("totalDistance"));
                    ticket.setTotalCost(rs.getDouble("totalCost"));
                    
                    ticket.setItems(new ArrayList<>());
                    
                    Statement stmti = this.connection.createStatement();
                    String sqli = "SELECT pathName, fromPoint, toPoint, distance, cost FROM TicketItem WHERE uid = '" + ticket.getUid() + "'";
                    try (ResultSet rsi = stmti.executeQuery(sqli)) {
                        while (rsi.next()) {
                            Ticket.Item item = ticket.new Item();
                            
                            item.setPathName(rsi.getString("pathName"));
                            item.setFromPoint(rsi.getString("fromPoint"));
                            item.setToPoint(rsi.getString("toPoint"));
                            item.setDistance(rsi.getDouble("distance"));
                            item.setCost(rsi.getDouble("cost"));
                            
                            ticket.getItems().add(item);
                        }
                    }
                    
                    list.add(ticket);
                }
            }
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
        return list;
    }
    
    @Override
    public boolean insertTicket (Ticket ticket) {
        String sql = "INSERT INTO Ticket (uid, issueDate, clientNif, pathType, pathCriteria, totalDistance, totalCost) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, ticket.getUid());
            pstmt.setString(2, ticket.getIssueDate());
            pstmt.setString(3, ticket.getClientNif());
            pstmt.setInt(4, ticket.getPathType());
            pstmt.setString(5, ticket.getPathCriteria());
            pstmt.setDouble(6, ticket.getTotalDistance());
            pstmt.setDouble(7, ticket.getTotalCost());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
            return false;
        }
        
        sql = "INSERT INTO TicketItem (uid, pathName, fromPoint, toPoint, distance, cost) VALUES (?, ?, ?, ?, ?, ?)";
        for (Ticket.Item item : ticket.getItems()) {
            try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
                pstmt.setString(1, ticket.getUid());
                pstmt.setString(2, item.getPathName());
                pstmt.setString(3, item.getFromPoint());
                pstmt.setString(4, item.getToPoint());
                pstmt.setDouble(5, item.getDistance());
                pstmt.setDouble(6, item.getCost());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                LoggerManager.getInstance().log(ex);
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public Ticket findTicket (String uid) {
        HashSet<Ticket> list = selectSQL(uid);
        return (list.size() > 0) ? (Ticket)list.toArray()[0] : null;
    }
    
    @Override
    public Collection<Ticket> selectTickets() {
        return selectSQL(null);
    }
    
}
