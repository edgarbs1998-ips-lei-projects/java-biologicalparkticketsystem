package biologicalparkticketsystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Config class to get the configurations of the config file
 */
public class ConfigManager {
    
    private static ConfigManager instance = new ConfigManager(); 
    
    private static final String CONFIG_FILE_PATH = "./config.properties";
    
    private Properties properties;
    
    private ConfigManager() { }
    
    /**
     * Method to initialize all the properties in the config file
     */
    public void init() {
        this.properties = new Properties();
        InputStream inputStream = null;
                
        try {
            inputStream = new FileInputStream(CONFIG_FILE_PATH);
            
            properties.load(inputStream);
        } catch (IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                Logger.getGlobal().log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Method to get the instance of the singleton class ConfigManager
     * @return instance of class ConfigManager
     */
    public static ConfigManager getInstance() {
        return instance;
    } 
    
    /**
     * Method to get the config properties
     * @return config properties
     */
    public Properties getProperties() {
        return this.properties;
    }
    
}
