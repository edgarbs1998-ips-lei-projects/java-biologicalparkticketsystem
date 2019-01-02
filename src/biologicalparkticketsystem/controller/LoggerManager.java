/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.controller;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author goldspy98
 */
public class LoggerManager {
    private static LoggerManager instance = new LoggerManager();
    
    private Logger logger;
    private Logger loggerTicketIssuance;
    private Logger loggerCourseCalculations;
    private Logger loggerStatisticsChecks;
    
    private LoggerManager() {
        try {
            ConfigManager config = ConfigManager.getInstance();
        
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            
            logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setLevel(Level.parse(config.getProperties().getProperty("logLevel")));
            FileHandler loggerFileHandler = new FileHandler(config.getProperties().getProperty("logFile"));
            loggerFileHandler.setFormatter(simpleFormatter);
            logger.addHandler(loggerFileHandler);
            
            loggerTicketIssuance = Logger.getLogger("ticket_issuance");
            FileHandler loggerTicketIssuanceFileHandler = new FileHandler(config.getProperties().getProperty("ticketIssuanceLogFile"));
            loggerTicketIssuanceFileHandler.setFormatter(simpleFormatter);
            loggerTicketIssuance.addHandler(loggerTicketIssuanceFileHandler);
            if (Boolean.parseBoolean(config.getProperties().getProperty("logTicketIssuance")) == true) {
                loggerTicketIssuance.setLevel(Level.INFO);
            } else {
                loggerTicketIssuance.setLevel(Level.OFF);
            }
            
            loggerCourseCalculations = Logger.getLogger("course_calculations");
            loggerCourseCalculations.setLevel(Level.INFO);
            FileHandler loggerCourseCalculationsFileHandler = new FileHandler(config.getProperties().getProperty("courseCalculationsLogFile"));
            loggerCourseCalculationsFileHandler.setFormatter(simpleFormatter);
            loggerCourseCalculations.addHandler(loggerCourseCalculationsFileHandler);
            if (Boolean.parseBoolean(config.getProperties().getProperty("logCourseCalculations")) == true) {
                loggerCourseCalculations.setLevel(Level.INFO);
            } else {
                loggerCourseCalculations.setLevel(Level.OFF);
            }
            
            loggerStatisticsChecks = Logger.getLogger("statistics_checks");
            loggerStatisticsChecks.setLevel(Level.INFO);
            FileHandler loggerStatisticsChecksFileHandler = new FileHandler(config.getProperties().getProperty("statisticsChecksLogFile"));
            loggerStatisticsChecksFileHandler.setFormatter(simpleFormatter);
            loggerStatisticsChecks.addHandler(loggerStatisticsChecksFileHandler);
            if (Boolean.parseBoolean(config.getProperties().getProperty("statisticsChecksLogFile")) == true) {
                loggerStatisticsChecks.setLevel(Level.INFO);
            } else {
                loggerStatisticsChecks.setLevel(Level.OFF);
            }
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, null, ex);
        }
    }
    
    public static LoggerManager getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    
    public Logger getLoggerTicketIssuance() {
        return this.loggerTicketIssuance;
    }
    
    public Logger getLoggerCourseCalculations() {
        return this.loggerCourseCalculations;
    }
    
    public Logger getLoggerStatisticsChecks() {
        return this.loggerStatisticsChecks;
    }
}
