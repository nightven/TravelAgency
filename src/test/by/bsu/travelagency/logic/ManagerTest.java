package test.by.bsu.travelagency.logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.ResourceBundle;
/**
 * Created by Михаил on 9/10/2016.
 */
public class ManagerTest {

    /**
     * Configuration manager test.
     */
    @Test
    public void configurationManagerTest(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.config");
        Assert.assertNotNull(resourceBundle);
    }

    /**
     * Message manager test.
     */
    @Test
    public void messageManagerTest(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.messages");
        Assert.assertNotNull(resourceBundle);
    }

    /**
     * Resource manager test.
     */
    @Test
    public void resourceManagerTest(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.text");
        Assert.assertNotNull(resourceBundle);
    }

}
