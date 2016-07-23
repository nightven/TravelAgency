package by.bsu.travelagency.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Михаил on 2/16/2016.
 */
public enum  MessageManager {
    INSTANCE;
    private ResourceBundle messageManager;
    private final String RESOURCE_NAME = "resources.messages";
    // класс извлекает информацию из файла messages.properties
    private MessageManager() {
        messageManager = ResourceBundle.getBundle(RESOURCE_NAME, Locale.getDefault());
    }
    public void changeResource(Locale locale) {
        messageManager = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }
    public String getProperty(String key) {
        return messageManager.getString(key);
    }
}
