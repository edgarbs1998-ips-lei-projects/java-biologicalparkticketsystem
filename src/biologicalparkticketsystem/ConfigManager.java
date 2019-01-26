package biologicalparkticketsystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
    
    private static ConfigManager instance = new ConfigManager(); 
    
    private static final String CONFIG_FILE_PATH = "./config.properties";
    
    private Properties properties;
    
    private ConfigManager() { }
    
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
    
    public static ConfigManager getInstance() {
        return instance;
    } 
    
    public Properties getProperties() {
        return this.properties;
    }
    
}
