package by.bsu.travelagency.pool;

/**
 * Created by Михаил on 2/24/2016.
 */

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;

/**
 * The Class ConnectionPool.
 */
public class ConnectionPool {

    /**
     * The Constant LOG.
     */
    private final static Logger LOG = Logger.getLogger(ConnectionPool.class);

    /**
     * The connection queue.
     */
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;

    /**
     * Instantiates a new connection pool.
     *
     * @param POOL_SIZE the pool size
     * @throws ConnectionPoolException the connection pool exception
     */
    public ConnectionPool(final int POOL_SIZE) throws ConnectionPoolException {
        try {
           makeConnection(POOL_SIZE);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Failed to register driver.", e);
        } catch (IOException e) {
            throw new ConnectionPoolException("Failed to read database properties.", e);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Failed to get connection.", e);
        }
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ConnectionPoolException the connection pool exception
     */
    public Connection getConnection() throws ConnectionPoolException {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Failed to take connection from pool.", e);
        }
    }

    /**
     * Gets the pool size.
     *
     * @return the pool size
     */
    public int getPoolSize() {
        return connectionQueue.size();
    }

    /**
     * Close all connections.
     *
     * @throws ConnectionPoolException the connection pool exception
     */
    public void closeAllConnections() throws ConnectionPoolException {
        int count = 0;
        for (ProxyConnection connection : connectionQueue) {
            try {
                connection.closeConnection();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Failed to close connection.", e);
            }
            count++;
        }
        LOG.info("Connections in the amount of " + count + " pieces successfully closed.");
    }

    /**
     * Make connections.
     *
     * @param POOL_SIZE the pool size
     * @throws IOException the IO exception
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException the SQL exception
     */
    private void makeConnection(final int POOL_SIZE) throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("/resources/database.properties"));
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        Class.forName("com.mysql.jdbc.Driver");
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(properties.getProperty("connection-string"), properties));
            connectionQueue.offer(connection);
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    private void closeConnection(ProxyConnection connection) {
        LOG.info("Queue before close: " + connectionQueue.size());
        connectionQueue.offer(connection);
        LOG.info("Queue after close: " + connectionQueue.size());
    }

    /**
     * The Inner Class ProxyConnection.
     */
    private class ProxyConnection implements Connection{

        /** The connection. */
        private Connection connection;

        /**
         * Instantiates a new proxy connection.
         *
         * @param connection the connection
         */
        ProxyConnection(Connection connection) {
            this.connection = connection;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createStatement()
         */
        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        /**
         * Close connection.
         *
         * @throws SQLException the SQL exception
         */
        public void closeConnection() throws SQLException {
            connection.close();
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#close()
         */
        @Override
        public void close() throws SQLException {
            TravelController.connectionPool.closeConnection(this);
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#commit()
         */
        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#isClosed()
         */
        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getMetaData()
         */
        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setReadOnly(boolean)
         */
        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#isReadOnly()
         */
        @Override
        public boolean isReadOnly() throws SQLException {
            return false;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setCatalog(java.lang.String)
         */
        @Override
        public void setCatalog(String catalog) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getCatalog()
         */
        @Override
        public String getCatalog() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setTransactionIsolation(int)
         */
        @Override
        public void setTransactionIsolation(int level) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getTransactionIsolation()
         */
        @Override
        public int getTransactionIsolation() throws SQLException {
            return 0;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getWarnings()
         */
        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#clearWarnings()
         */
        @Override
        public void clearWarnings() throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createStatement(int, int)
         */
        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
         */
        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
         */
        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getTypeMap()
         */
        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setTypeMap(java.util.Map)
         */
        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setHoldability(int)
         */
        @Override
        public void setHoldability(int holdability) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getHoldability()
         */
        @Override
        public int getHoldability() throws SQLException {
            return 0;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setSavepoint()
         */
        @Override
        public Savepoint setSavepoint() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setSavepoint(java.lang.String)
         */
        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#rollback(java.sql.Savepoint)
         */
        @Override
        public void rollback(Savepoint savepoint) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
         */
        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createStatement(int, int, int)
         */
        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
         */
        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
         */
        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareStatement(java.lang.String, int)
         */
        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
         */
        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
         */
        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createClob()
         */
        @Override
        public Clob createClob() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createBlob()
         */
        @Override
        public Blob createBlob() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createNClob()
         */
        @Override
        public NClob createNClob() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createSQLXML()
         */
        @Override
        public SQLXML createSQLXML() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#isValid(int)
         */
        @Override
        public boolean isValid(int timeout) throws SQLException {
            return false;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
         */
        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setClientInfo(java.util.Properties)
         */
        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getClientInfo(java.lang.String)
         */
        @Override
        public String getClientInfo(String name) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getClientInfo()
         */
        @Override
        public Properties getClientInfo() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
         */
        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
         */
        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setSchema(java.lang.String)
         */
        @Override
        public void setSchema(String schema) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getSchema()
         */
        @Override
        public String getSchema() throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#abort(java.util.concurrent.Executor)
         */
        @Override
        public void abort(Executor executor) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setNetworkTimeout(java.util.concurrent.Executor, int)
         */
        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getNetworkTimeout()
         */
        @Override
        public int getNetworkTimeout() throws SQLException {
            return 0;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareStatement(java.lang.String)
         */
        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#prepareCall(java.lang.String)
         */
        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#nativeSQL(java.lang.String)
         */
        @Override
        public String nativeSQL(String sql) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#rollback()
         */
        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#setAutoCommit(boolean)
         */
        @Override
        public void setAutoCommit(boolean flag) throws SQLException {
            connection.setAutoCommit(flag);
        }

        /* (non-Javadoc)
         * @see java.sql.Connection#getAutoCommit()
         */
        @Override
        public boolean getAutoCommit() throws SQLException {
            return false;
        }

        /* (non-Javadoc)
         * @see java.sql.Wrapper#unwrap(java.lang.Class)
         */
        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        /* (non-Javadoc)
         * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
         */
        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
}
