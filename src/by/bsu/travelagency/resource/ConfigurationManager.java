package by.bsu.travelagency.resource;

import java.util.ResourceBundle;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ConfigurationManager {
    
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.config");
    
    /**
     * Instantiates a new configuration manager.
     */
    private ConfigurationManager() { }
    
    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
