/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.IInvoiceDAO;
import biologicalparkticketsystem.model.IStatisticsDAO;
import biologicalparkticketsystem.model.ITicketDAO;
import biologicalparkticketsystem.model.InvoiceDAOSerialization;
import biologicalparkticketsystem.model.InvoiceDAOSqlLite;
import biologicalparkticketsystem.model.StatisticsDAOSerialization;
import biologicalparkticketsystem.model.StatisticsDAOSqlLite;
import biologicalparkticketsystem.model.TicketDAOSerialization;
import biologicalparkticketsystem.model.TicketDAOSqlLite;

/**
 *
 * @author golds
 */
public class DaoManager {
    
    private static DaoManager instance = new DaoManager();
    
    private ITicketDAO ticketDao;
    private IInvoiceDAO invoiceDao;
    private IStatisticsDAO statisticsDao;
    
    private DaoManager() { }
    
    public static DaoManager getInstance() {
        return instance;
    }
    
    public void init(ConfigManager config, MapManager mapManager) {
        switch (config.getProperties().getProperty("persistence.type")) {
            case "serialization":
                this.ticketDao = new TicketDAOSerialization(config.getProperties().getProperty("persistence.erialization.folder"));
                this.invoiceDao = new InvoiceDAOSerialization(config.getProperties().getProperty("persistence.erialization.folder"));
                this.statisticsDao = new StatisticsDAOSerialization(config.getProperties().getProperty("persistence.erialization.folder"), mapManager);
                break;
            case "sqllite":
                this.ticketDao = new TicketDAOSqlLite(config.getProperties().getProperty("persistence.sqllite.file"));
                this.invoiceDao = new InvoiceDAOSqlLite(config.getProperties().getProperty("persistence.sqllite.file"));
                this.statisticsDao = new StatisticsDAOSqlLite(config.getProperties().getProperty("persistence.sqllite.file"), mapManager);
                break;
            default:
                throw new IllegalArgumentException("ticket dao type does not exists");
        }
    }
    
    public ITicketDAO getTicketDao() {
        return this.ticketDao;
    }
    
    public IInvoiceDAO getInvoiceDao() {
        return this.invoiceDao;
    }
    
    public IStatisticsDAO getStatisticsDao() {
        return this.statisticsDao;
    }
    
}
