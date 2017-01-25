package test.by.bsu.travelagency.logic;

import by.bsu.travelagency.pool.ConnectionPool;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.junit.Assert;
import org.junit.Test;


public class ConnectionPoolTest {

    /**
     * Connection pool test.
     */
    @Test
    public void connectionPoolTest(){
        // TODO: 1/25/2017 doesn't work, properties error
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
