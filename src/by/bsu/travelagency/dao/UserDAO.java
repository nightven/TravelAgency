package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class UserDAO extends AbstractDAO<Long, User> {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(UserDAO.class);
    
    /** The Constant SQL_SELECT_ALL_USERS. */
    private static final String SQL_SELECT_ALL_USERS = "SELECT iduser,login,password,role,email,name,surname,discount,money FROM users";
    
    /** The Constant SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT. */
    private static final String SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT = "SELECT users.iduser,users.login,users.password,users.role,users.email,users.name,users.surname,users.discount,users.money,COUNT(DISTINCT orders.idorder) AS orders FROM users LEFT JOIN orders ON users.iduser=orders.iduser GROUP BY users.iduser;";
    
    /** The Constant SQL_SELECT_USER_BY_NAME. */
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT iduser,login,password,role,email,name,surname,discount,money FROM users WHERE login=?";
    
    /** The Constant SQL_SELECT_USER_BY_ID. */
    private static final String SQL_SELECT_USER_BY_ID = "SELECT iduser,login,password,role,email,name,surname,discount,money FROM users WHERE iduser=?";
    
    /** The Constant SQL_SELECT_USER_PASSWORD_BY_ID. */
    private static final String SQL_SELECT_USER_PASSWORD_BY_ID = "SELECT password FROM users WHERE iduser=?";
    
    /** The Constant SQL_UPDATE_USER_PASSWORD_BY_ID. */
    private static final String SQL_UPDATE_USER_PASSWORD_BY_ID = "UPDATE users SET password=? WHERE iduser=?";
    
    /** The Constant SQL_SELECT_MONEY_BY_USER_ID. */
    private static final String SQL_SELECT_MONEY_BY_USER_ID = "SELECT money FROM users WHERE iduser=?";
    
    /** The Constant SQL_INSERT_USER. */
    private static final String SQL_INSERT_USER = "INSERT INTO users(login,password,role,email,name,surname,discount,money) VALUES(?,?,?,?,?,?,?,?)";
    
    /** The Constant SQL_UPDATE_USER. */
    private static final String SQL_UPDATE_USER = "UPDATE users SET login=?,password=?,role=?,email=?,name=?,surname=?,discount=?,money=? WHERE iduser=?";
    
    /** The Constant SQL_UPDATE_USER_BALANCE. */
    private static final String SQL_UPDATE_USER_BALANCE = "UPDATE users SET money=? WHERE iduser=?";
    
    /** The Constant SQL_UPDATE_USER_BALANCE_ADDITION. */
    private static final String SQL_UPDATE_USER_BALANCE_ADDITION = "UPDATE users SET money=money + ? WHERE iduser=?";
    
    /** The Constant SQL_DELETE_USER. */
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE iduser=?";


    /**
     * Find all users.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<User> findAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("iduser"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setMoney(resultSet.getInt("money"));
                users.add(user);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(st);
            closeConnection(cn);
        }
        return users;
    }

    /**
     * Find all users with order count.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<UserOrderNumber> findAllUsersWithOrderCount() throws DAOException {
        List<UserOrderNumber> users = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT);
            while (resultSet.next()) {
                UserOrderNumber userOrderNumber = new UserOrderNumber();
                userOrderNumber.setId(resultSet.getLong("iduser"));
                userOrderNumber.setLogin(resultSet.getString("login"));
                userOrderNumber.setPassword(resultSet.getString("password"));
                userOrderNumber.setRole(resultSet.getInt("role"));
                userOrderNumber.setEmail(resultSet.getString("email"));
                userOrderNumber.setName(resultSet.getString("name"));
                userOrderNumber.setSurname(resultSet.getString("surname"));
                userOrderNumber.setDiscount(resultSet.getDouble("discount"));
                userOrderNumber.setMoney(resultSet.getInt("money"));
                userOrderNumber.setOrderNumber(resultSet.getInt("orders"));
                users.add(userOrderNumber);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(st);
            closeConnection(cn);
        }
        return users;
    }

    /**
     * Find user by name.
     *
     * @param name the name
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserByName(String name) throws DAOException {
        User user = new User();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_USER_BY_NAME);
            ps.setString(1,name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                LOG.debug(resultSet.getString("login"));
                LOG.debug(resultSet.getInt("role"));
                user.setId(resultSet.getLong("iduser"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setMoney(resultSet.getInt("money"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return user;
    }

    /**
     * Find money by user id.
     *
     * @param id the id
     * @return the int
     * @throws DAOException the DAO exception
     */
    public int findMoneyByUserId(Long id) throws DAOException {
        int money = 0;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_MONEY_BY_USER_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                money = resultSet.getInt("money");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return money;
    }

    /**
     * Find password by user id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findPasswordByUserId(Long id) throws DAOException {
        String password = null;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_USER_PASSWORD_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return password;
    }

    /**
     * Update password by user id.
     *
     * @param id the id
     * @param password the password
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updatePasswordByUserId(Long id, String password) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_USER_PASSWORD_BY_ID);
            ps.setString(1,password);
            ps.setLong(2,id);
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(User user) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_INSERT_USER);
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getRole());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getName());
            ps.setString(6,user.getSurname());
            ps.setDouble(7,user.getDiscount());
            ps.setInt(8,user.getMoney());
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(User user) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_USER);
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getRole());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getName());
            ps.setString(6,user.getSurname());
            ps.setDouble(7,user.getDiscount());
            ps.setInt(8,user.getMoney());
            ps.setLong(9,user.getId());
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /**
     * Update user balance.
     *
     * @param id the id
     * @param money the money
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updateUserBalance(Long id, int money) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_USER_BALANCE);
            ps.setInt(1,money);
            ps.setLong(2,id);
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /**
     * Update user balance addition.
     *
     * @param id the id
     * @param moneyToAdd the money to add
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updateUserBalanceAddition(Long id, int moneyToAdd) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_USER_BALANCE_ADDITION);
            ps.setInt(1,moneyToAdd);
            ps.setLong(2,id);
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /**
     * Close statement.
     *
     * @param st the st
     * @throws DAOException the DAO exception
     */
    public void closeStatement(Statement st) throws DAOException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DAOException("Failed to close statement.", e);
            }
        }
    }

    /**
     * Close connection.
     *
     * @param cn the cn
     * @throws DAOException the DAO exception
     */
    public void closeConnection(Connection cn) throws DAOException {
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                throw new DAOException("Failed to close statement.", e);
            }
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#findEntityById(java.lang.Object)
     */
    @Override
    public User findEntityById(Long id) throws DAOException {
        User user = new User();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_USER_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("iduser"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setMoney(resultSet.getInt("money"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return user;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_DELETE_USER);
            ps.setLong(1,id);
            ps.execute();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }
}
