package by.bsu.travelagency.resource;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Enum ResourceManager.
 */
public enum ResourceManager {
    
    /** The instance. */
    INSTANCE;
    
    /** The resource bundle. */
    private ResourceBundle resourceBundle;
    
    /** The resource name. */
    private final String RESOURCE_NAME = "resources.text";
    
    /**
     * Instantiates a new resource manager.
     */
    private ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, Locale.getDefault());
    }
    
    /**
     * Change resource.
     *
     * @param locale the locale
     */
    public void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }
    
    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}