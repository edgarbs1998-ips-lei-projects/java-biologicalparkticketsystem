package biologicalparkticketsystem;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsable to manage the logger
 */
public class LoggerManager {
    
    /**
     * Enum to know the component that is logged
     */
    public enum Component {
        GLOBAL, 
        TICKETS_ISSUANCE,
        COURSE_CALCULATIONS,
        STATISTICS_CHECKS;
        
        /**
         * Method that returns the component name
         * @return name of the component
         */
        public String getName() {
            switch (this) {
                case GLOBAL: return "GLOBAL";
                case TICKETS_ISSUANCE: return "TICKETS_ISSUANCE";
                case COURSE_CALCULATIONS: return "COURSE_CALCULATIONS";
                case STATISTICS_CHECKS: return "STATISTICS_CHECKS";
            }
            return "Unknown";
        }
        
        /**
         * Method is reponsible to return the message that is to be outputed in the log file
         * @return the message to be outputed to log file
         */
        public String getMessage() {
            switch (this) {
                case TICKETS_ISSUANCE: return "a ticket has been issued";
                case COURSE_CALCULATIONS: return "a course has been calculated";
                case STATISTICS_CHECKS: return "statistics data has been checked";
            }
            return null;
        }
    };
    
    private static LoggerManager instance = new LoggerManager();
    
    private Logger logger;
    
    private LoggerManager() { }
    
    /**
     * Method to initialize all the components of the logger features
     */
    public void init() {
        try {
            ConfigManager config = ConfigManager.getInstance();
            
            Level level = Level.parse(config.getProperties().getProperty("logger.level"));
            String logFile = config.getProperties().getProperty("logger.file");
            
            Formatter customLoggerFormatter = new CustomLoggerFormatter();
            
            // Create the path folder if it does not exists
            File file = new File(logFile);
            file.getParentFile().mkdirs();
            
            logger = Logger.getGlobal();
            logger.setUseParentHandlers(false);
            logger.setLevel(level);
            
            // Add console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(customLoggerFormatter);
            logger.addHandler(consoleHandler);
            
            // Add file handler
            FileHandler loggerFileHandler = new FileHandler(logFile);
            loggerFileHandler.setFormatter(customLoggerFormatter);
            logger.addHandler(loggerFileHandler);
        } catch (IOException | SecurityException ex) {
            Logger.getGlobal().log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to get the singleton instance of class LoggerManager
     * @return instance of singleton class LoggerManager
     */
    public static LoggerManager getInstance() {
        return instance;
    }
    
    /**
     * Method to log a message
     * @param message message to add to the outputed log file
     * @param level level of error or notification of the log
     * @param component component type of the log to be outputed
     * @param thrown parameter that contains the thrown error
     */
    public void log(String message, Level level, Component component, Throwable thrown) {
        ConfigManager config = ConfigManager.getInstance();
        
        switch (component) {
            case TICKETS_ISSUANCE:
                if (Boolean.parseBoolean(config.getProperties().getProperty("logger.enable.tickets_issuance")) == false) return;
                break;
            case COURSE_CALCULATIONS:
                if (Boolean.parseBoolean(config.getProperties().getProperty("logger.enable.course_calculations")) == false) return;
                break;
            case STATISTICS_CHECKS:
                if (Boolean.parseBoolean(config.getProperties().getProperty("logger.enable.statistics_checks")) == false) return;
                break;
        }
        
        if (component.getMessage() != null) {
            if (message != null && !message.equals("")) {
                message = component.getMessage() + " - " + message;
            } else {
                message = component.getMessage();
            }
        }
        
        message = "[" + component.getName() + "] " + message;
        
        this.logger.log(level, message, thrown);
    }
    
    /**
     * Method to log a message
     * @param component component type of the log to be outputed
     */
    public void log(Component component) {
         this.log(null, Level.INFO, component, null);
    }
    
    /**
     * Method to log a message
     * @param message message to add to the outputed log file
     * @param level level of error or notification of the log
     * @param component component type of the log to be outputed
     */
    public void log(Component component, String message, Level level) {
         this.log(message, level, component, null);
    }
    
    /**
     * Method to log a message
     * @param message message to add to the outputed log file
     * @param component component type of the log to be outputed
     */
    public void log(Component component, String message) {
        this.log(message, Level.INFO, component, null);
    }
    
    /**
     * Method to log a message
     * @param message message to add to the outputed log file
     * @param level level of error or notification of the log
     */
    public void log(String message, Level level) {
        this.log(message, level, Component.GLOBAL, null);
    }
    
    /**
     * Method to log a message
     * @param message message to add to the outputed log file
     */
    public void log(String message) {
        this.log(message, Level.INFO, Component.GLOBAL, null);
    }
    
    /**
     * Method to log a message
     * @param thrown parameter that contains the thrown error
     */
    public void log(Throwable thrown) {
        this.log(null, Level.SEVERE, Component.GLOBAL, thrown);
    }
    
    /**
     * Method to log a message
     * @param level level of error or notification of the log
     * @param thrown parameter that contains the thrown error
     */
    public void log(Throwable thrown, Level level) {
        this.log(null, level, Component.GLOBAL, thrown);
    }
    
    /**
     * Method to log a message
     * @param message message to add to the outputed log file
     * @param thrown parameter that contains the thrown error
     */
    public void log(String message, Throwable thrown) {
        this.log(message, Level.SEVERE, Component.GLOBAL, thrown);
    }
    
}
