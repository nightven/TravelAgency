package by.bsu.travelagency.pool;

/**
 * Created by Михаил on 2/24/2016.
 */
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * The Class ConnectionPool.
 */
public class ConnectionPool {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ConnectionPool.class);
    
    /** The connection queue. */
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    
    /** The connection string. */
    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/epamtravel";

    /**
     * Instantiates a new connection pool.
     *
     * @param POOL_SIZE the pool size
     * @throws ConnectionPoolException the connection pool exception
     */
    public ConnectionPool(final int POOL_SIZE) throws ConnectionPoolException {
        Properties properties=new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","root");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(POOL_SIZE);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            throw new ConnectionPoolException("Failed to register driver.", e);
        }
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection = null;
            try {
                connection = new ProxyConnection(DriverManager.getConnection(CONNECTION_STRING, properties));
            } catch (SQLException e) {
                throw new ConnectionPoolException("Failed to get connection.", e);
            }
            connectionQueue.offer(connection);
        }
    }
    
    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ConnectionPoolException the connection pool exception
     */
    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Failed to take connection from pool.", e);
        }
        return connection;
    }

    /**
     * Gets the pool size.
     *
     * @return the pool size
     */
    public int getPoolSize(){
        return connectionQueue.size();
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    public void closeConnection(ProxyConnection connection) {
        LOG.info("Queue before close: " + connectionQueue.size());
        connectionQueue.offer(connection);
        LOG.info("Queue after close: " + connectionQueue.size());
    }

    /**
     * Close all connections.
     *
     * @throws ConnectionPoolException the connection pool exception
     */
    public void closeAllConnections() throws ConnectionPoolException {
        int count = 0;
        for (ProxyConnection connection :
                connectionQueue) {
            try {
                connection.closeConnection();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Failed to close connection.", e);
            }
            count++;
        }
        LOG.info("Connections in the amount of " + count + " pieces successfully closed.");
    }
}
