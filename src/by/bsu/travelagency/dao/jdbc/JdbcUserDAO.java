package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDAO implements UserDAO {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(JdbcUserDAO.class);
    
    /** The Constant SQL_SELECT_ALL_USERS. */
    private static final String SQL_SELECT_ALL_USERS = "SELECT id_user,login,password,role,email,name,surname,discount,balance FROM users";
    
    /** The Constant SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT. */
    private static final String SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT = "SELECT users.id_user,users.login,users.password,users.role,users.email,users.name,users.surname,users.discount,users.balance,COUNT(DISTINCT orders.id_order) AS orders FROM users LEFT JOIN orders USING (id_user) GROUP BY users.id_user;";
    
    /** The Constant SQL_SELECT_USER_BY_NAME. */
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT id_user,login,password,role,email,name,surname,discount,balance FROM users WHERE login=?";
    
    /** The Constant SQL_SELECT_USER_BY_ID. */
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user,login,password,role,email,name,surname,discount,balance FROM users WHERE id_user=?";
    
    /** The Constant SQL_SELECT_USER_PASSWORD_BY_ID. */
    private static final String SQL_SELECT_USER_PASSWORD_BY_ID = "SELECT password FROM users WHERE id_user=?";
    
    /** The Constant SQL_UPDATE_USER_PASSWORD_BY_ID. */
    private static final String SQL_UPDATE_USER_PASSWORD_BY_ID = "UPDATE users SET password=? WHERE id_user=?";
    
    /** The Constant SQL_SELECT_MONEY_BY_USER_ID. */
    private static final String SQL_SELECT_MONEY_BY_USER_ID = "SELECT balance FROM users WHERE id_user=?";
    
    /** The Constant SQL_INSERT_USER. */
    private static final String SQL_INSERT_USER = "INSERT INTO users(login,password,role,email,name,surname,discount,balance) VALUES(?,?,?,?,?,?,?,?)";
    
    /** The Constant SQL_UPDATE_USER. */
    private static final String SQL_UPDATE_USER = "UPDATE users SET login=?,password=?,role=?,email=?,name=?,surname=?,discount=?,balance=? WHERE id_user=?";
    
    /** The Constant SQL_UPDATE_USER_BALANCE. */
    private static final String SQL_UPDATE_USER_BALANCE = "UPDATE users SET balance=? WHERE id_user=?";
    
    /** The Constant SQL_UPDATE_USER_BALANCE_ADDITION. */
    private static final String SQL_UPDATE_USER_BALANCE_ADDITION = "UPDATE users SET balance=balance + ? WHERE id_user=?";
    
    /** The Constant SQL_DELETE_USER. */
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id_user=?";

    /**
     * Instantiates a new JdbcUserDAO.
     */
    private JdbcUserDAO() {
    }

    /** Nested class JdbcUserDAOHolder. */
    private static class JdbcUserDAOHolder {
        private static final JdbcUserDAO HOLDER_INSTANCE = new JdbcUserDAO();
    }


    /**
     * Gets the instance.
     *
     * @return the JdbcUserDAOHolder instance
     */
    public static JdbcUserDAO getInstance() {
        return JdbcUserDAOHolder.HOLDER_INSTANCE;
    }

    /**
     * Find all users.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<User> findAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id_user"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setMoney(resultSet.getInt("balance"));
                users.add(user);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT);
            while (resultSet.next()) {
                UserOrderNumber userOrderNumber = new UserOrderNumber();
                userOrderNumber.setId(resultSet.getLong("id_user"));
                userOrderNumber.setLogin(resultSet.getString("login"));
                userOrderNumber.setPassword(resultSet.getString("password"));
                userOrderNumber.setRole(resultSet.getInt("role"));
                userOrderNumber.setEmail(resultSet.getString("email"));
                userOrderNumber.setName(resultSet.getString("name"));
                userOrderNumber.setSurname(resultSet.getString("surname"));
                userOrderNumber.setDiscount(resultSet.getDouble("discount"));
                userOrderNumber.setMoney(resultSet.getInt("balance"));
                userOrderNumber.setOrderNumber(resultSet.getInt("orders"));
                users.add(userOrderNumber);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_BY_NAME)) {
            ps.setString(1,name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                LOG.debug(resultSet.getString("login"));
                LOG.debug(resultSet.getInt("role"));
                user.setId(resultSet.getLong("id_user"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setMoney(resultSet.getInt("balance"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return user;
    }

    /**
     * Find money by user id.
     *
     * @param id the id
     * @return the money
     * @throws DAOException the DAO exception
     */
    public double findMoneyByUserId(Long id) throws DAOException {
        double money = 0;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_MONEY_BY_USER_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                money = resultSet.getDouble("balance");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_PASSWORD_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_USER_PASSWORD_BY_ID)) {
            ps.setString(1,password);
            ps.setLong(2,id);
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(User user) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_USER)) {
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getRole());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getName());
            ps.setString(6,user.getSurname());
            ps.setDouble(7,user.getDiscount());
            ps.setDouble(8,user.getMoney());
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(User user) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_USER)) {
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getRole());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getName());
            ps.setString(6,user.getSurname());
            ps.setDouble(7,user.getDiscount());
            ps.setDouble(8,user.getMoney());
            ps.setLong(9,user.getId());
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
    public boolean updateUserBalance(Long id, double money) throws DAOException {
        return updateUserBalanceGeneral(id, money, SQL_UPDATE_USER_BALANCE);
    }

    /**
     * Update user balance addition.
     *
     * @param id the id
     * @param moneyToAdd the money to add
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updateUserBalanceAddition(Long id, double moneyToAdd) throws DAOException {
        return updateUserBalanceGeneral(id, moneyToAdd, SQL_UPDATE_USER_BALANCE_ADDITION);
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public User findEntityById(Long id) throws DAOException {
        User user = new User();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id_user"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setMoney(resultSet.getInt("balance"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return user;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_USER)) {
            ps.setLong(1,id);
            ps.execute();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Update user balance general method.
     *
     * @param id the id
     * @param money the money to add
     * @param query the query to use
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    private boolean updateUserBalanceGeneral(Long id, double money, String query) throws DAOException{
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDouble(1,money);
            ps.setLong(2,id);
            if (ps.executeUpdate() != 0) {
                flag = true;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        LOG.debug("updateUserBalanceGeneral = " + flag);
        return flag;
    }
}
