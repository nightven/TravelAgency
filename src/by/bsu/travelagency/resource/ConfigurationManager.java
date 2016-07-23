package by.bsu.travelagency.resource;

import java.util.ResourceBundle;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.config");
    // класс извлекает информацию из файла config.properties
    private ConfigurationManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
