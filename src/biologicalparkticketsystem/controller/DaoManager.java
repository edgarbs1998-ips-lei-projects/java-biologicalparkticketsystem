/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.controller;

import biologicalparkticketsystem.model.ITicketDAO;
import biologicalparkticketsystem.model.TicketDAOSerialization;
import biologicalparkticketsystem.model.TicketDAOSqlLite;

/**
 *
 * @author golds
 */
public class DaoManager {
    
    private static DaoManager instance = new DaoManager();
    
    private ITicketDAO ticketDao;
    
    private DaoManager() { }
    
    public static DaoManager getInstance() {
        return instance;
    }
    
    public void init(ConfigManager config) {
        switch (config.getProperties().getProperty("persistence.type")) {
            case "serialization":
                this.ticketDao = new TicketDAOSerialization(config.getProperties().getProperty("persistence.erialization.folder"));
                break;
            case "sqllite":
                this.ticketDao = new TicketDAOSqlLite(config.getProperties().getProperty("persistence.sqllite.file"));
                break;
            default:
                throw new IllegalArgumentException("ticket dao type does not exists");
        }
    }
    
    public ITicketDAO getTicketDao() {
        return this.ticketDao;
    }
    
}
