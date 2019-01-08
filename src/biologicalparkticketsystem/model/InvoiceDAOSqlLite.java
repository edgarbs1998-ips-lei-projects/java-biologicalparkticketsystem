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
public class InvoiceDAOSqlLite implements IInvoiceDAO {
    
    private Connection connection;
    
    public InvoiceDAOSqlLite(String dbFile) {
        // Create the path folder if it does not exists
        if (!dbFile.equals("")) {
            File file = new File(dbFile);
            file.getParentFile().mkdirs();
        }
        
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            
            Statement stmt = this.connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Invoice (\n"
                    + " uid TEXT NOT NULL,\n"
                    + " issueDate TEXT NOT NULL,\n"
                    + " vat REAL NOT NULL,\n"
                    + " baseAmmount REAL NOT NULL,\n"
                    + " taxAmmount REAL NOT NULL,\n"
                    + " total REAL NOT NULL,\n"
                    + " currency TEXT NOT NULL\n"
                    + ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS InvoiceItem (\n"
                    + " uid TEXT NOT NULL,\n"
                    + " item TEXT NOT NULL,\n"
                    + " price REAL NOT NULL,\n"
                    + " quantity INTEGER NOT NULL,\n"
                    + " subtotal REAL NOT NULL,\n"
                    + " vat REAL NOT NULL,\n"
                    + " total REAL NOT NULL\n"
                    + ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Client (\n"
                    + " uid TEXT NOT NULL,\n"
                    + " name TEXT NOT NULL,\n"
                    + " nif TEXT NOT NULL,\n"
                    + " address TEXT NOT NULL,\n"
                    + " postalCode TEXT NOT NULL,\n"
                    + " location TEXT NOT NULL,\n"
                    + " country TEXT NOT NULL\n"
                    + ");");
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
    }
    
    private HashSet<Invoice> selectSQL (String uid) {
        HashSet<Invoice> list = new HashSet<>();
        try {
            Statement stmt = this.connection.createStatement();
            String sql = "SELECT uid, issueDate, vat, baseAmmount, taxAmmount, total, currency FROM Invoice";
            if (uid != null) {
                sql += " WHERE uid = '" + uid + "'";
            }
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    
                    invoice.setUid(rs.getString("uid"));
                    invoice.setIssueDate(rs.getString("issueDate"));
                    invoice.setVat(rs.getDouble("vat"));
                    invoice.setBaseAmmount(rs.getDouble("baseAmmount"));
                    invoice.setTaxAmmount(rs.getDouble("taxAmmount"));
                    invoice.setTotal(rs.getDouble("total"));
                    invoice.setCurrency(rs.getString("currency"));
                    
                    
                    invoice.setItems(new ArrayList<>());
                    
                    Statement stmti = this.connection.createStatement();
                    String sqli = "SELECT item, price, quantity, subtotal, vat, total FROM InvoiceItem WHERE uid = '" + invoice.getUid() + "'";
                    try (ResultSet rsi = stmti.executeQuery(sqli)) {
                        while (rsi.next()) {
                            Invoice.Item item = invoice.new Item();
                            
                            item.setItem(rsi.getString("item"));
                            item.setPrice(rsi.getDouble("price"));
                            item.setQuantity(rsi.getInt("quantity"));
                            item.setSubtotal(rsi.getDouble("subtotal"));
                            item.setVat(rsi.getDouble("vat"));
                            item.setTotal(rsi.getDouble("total"));
                            
                            invoice.getItems().add(item);
                        }
                    }
                    
                    Statement stmtc = this.connection.createStatement();
                    String sqlc = "SELECT name, nif, address, postalCode, location, country FROM Client WHERE uid = '" + invoice.getUid() + "'";
                    try (ResultSet rsc = stmtc.executeQuery(sqlc)) {
                        while (rsc.next()) {
                            Client client = new Client();
                            
                            client.setName(rsc.getString("name"));
                            client.setNif(rsc.getString("nif"));
                            
                            Client.Address address = client.new Address();
                            address.setAddress(rsc.getString("address"));
                            address.setPostalCode(rsc.getString("postalCode"));
                            address.setLocation(rsc.getString("location"));
                            address.setCountry(rsc.getString("country"));
                            
                            client.setAddress(address);
                            
                            invoice.setClient(client);
                        }
                    }
                    
                    list.add(invoice);
                }
            }
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
        }
        return list;
    }
    
    @Override
    public boolean insertInvoice (Invoice invoice) {
        String sql = "INSERT INTO Invoice (uid, issueDate, vat, baseAmmount, taxAmmount, total, currency) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, invoice.getUid());
            pstmt.setString(2, invoice.getIssueDate());
            pstmt.setDouble(3, invoice.getVat());
            pstmt.setDouble(4, invoice.getBaseAmmount());
            pstmt.setDouble(5, invoice.getTaxAmmount());
            pstmt.setDouble(6, invoice.getTotal());
            pstmt.setString(7, invoice.getCurrency());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
            return false;
        }
        
        sql = "INSERT INTO InvoiceItem (uid, item, price, quantity, subtotal, vat, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        for (Invoice.Item item : invoice.getItems()) {
            try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
                pstmt.setString(1, invoice.getUid());
                pstmt.setString(2, item.getItem());
                pstmt.setDouble(3, item.getPrice());
                pstmt.setInt(4, item.getQuantity());
                pstmt.setDouble(5, item.getSubtotal());
                pstmt.setDouble(6, item.getVat());
                pstmt.setDouble(7, item.getTotal());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                LoggerManager.getInstance().log(ex);
                return false;
            }
        }
        
        sql = "INSERT INTO Client (uid, name, nif, address, postalCode, location, country) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Client client = invoice.getClient();
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, invoice.getUid());
            pstmt.setString(2, client.getName());
            pstmt.setString(3, client.getNif());
            pstmt.setString(4, client.getAddress().getAddress());
            pstmt.setString(5, client.getAddress().getPostalCode());
            pstmt.setString(6, client.getAddress().getLocation());
            pstmt.setString(7, client.getAddress().getCountry());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LoggerManager.getInstance().log(ex);
            return false;
        }
        
        return true;
    }
    
    @Override
    public Invoice findInvoice (String uid) {
        HashSet<Invoice> list = selectSQL(uid);
        return (list.size() > 0) ? (Invoice)list.toArray()[0] : null;
    }
    
    @Override
    public Collection<Invoice> selectInvoices() {
        return selectSQL(null);
    }
    
}
