package by.bsu.travelagency.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Михаил on 2/16/2016.
 */
public enum  MessageManager {
    
    /** The instance. */
    INSTANCE;
    
    /** The message manager. */
    private ResourceBundle messageManager;
    
    /** The resource name. */
    private final String RESOURCE_NAME = "resources.messages";
    
    /**
     * Instantiates a new message manager.
     */
    private MessageManager() {
        messageManager = ResourceBundle.getBundle(RESOURCE_NAME, Locale.getDefault());
    }
    
    /**
     * Change resource.
     *
     * @param locale the locale
     */
    public void changeResource(Locale locale) {
        messageManager = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }
    
    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return messageManager.getString(key);
    }
}
