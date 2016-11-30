package test.by.bsu.travelagency.logic;

import by.bsu.travelagency.pool.ConnectionPool;
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Михаил on 9/10/2016.
 */
public class ConnectionPoolTest {

    /**
     * Connection pool test.
     */
    @Test
    public void connectionPoolTest(){
        ConnectionPool connectionPool = null;
        try {
            connectionPool = new ConnectionPool(20);
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        int expected = 20;
        int actual = connectionPool.getPoolSize();
        Assert.assertEquals(expected, actual);
    }

}
