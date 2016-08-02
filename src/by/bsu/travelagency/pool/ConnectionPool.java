package by.bsu.travelagency.pool;

/**
 * Created by Михаил on 2/24/2016.
 */
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
public class ConnectionPool {
    private final static Logger LOG = Logger.getLogger(ConnectionPool.class);
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/epamtravel";

    public ConnectionPool(final int POOL_SIZE) throws SQLException {
        Properties properties=new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","root");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(POOL_SIZE);
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(CONNECTION_STRING, properties));
            connectionQueue.offer(connection);
        }
    }
    public ProxyConnection getConnection() throws InterruptedException {
        ProxyConnection connection = null;
        connection = connectionQueue.take();
        return connection;
    }
    public void closeConnection(ProxyConnection connection) {
        LOG.info("Queue before close: " + connectionQueue.size());
        connectionQueue.offer(connection);
        LOG.info("Queue after close: " + connectionQueue.size());
    }
}
