package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.pool.ConnectionPool;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDAO implements UserDAO {

    private final static Logger LOG = Logger.getLogger(JdbcUserDAO.class);
    
    private static final String SQL_SELECT_ALL_USERS = "SELECT id_user,login,password,role,email,name,surname,discount,balance " +
            "FROM users";
    
    private static final String SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT = "SELECT users.id_user,users.login,users.password,users.role," +
            "users.email,users.name,users.surname,users.discount,users.balance,COUNT(DISTINCT orders.id_order) AS orders FROM users " +
            "LEFT JOIN orders USING (id_user) GROUP BY users.id_user;";
    
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT id_user,login,password,role,email,name,surname,discount,balance " +
            "FROM users WHERE login=?";
    
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user,login,password,role,email,name,surname,discount,balance " +
            "FROM users WHERE id_user=?";
    
    private static final String SQL_SELECT_USER_PASSWORD_BY_ID = "SELECT password FROM users WHERE id_user=?";
    
    private static final String SQL_UPDATE_USER_PASSWORD_BY_ID = "UPDATE users SET password=? WHERE id_user=?";
    
    private static final String SQL_SELECT_MONEY_BY_USER_ID = "SELECT balance FROM users WHERE id_user=?";
    
    private static final String SQL_INSERT_USER = "INSERT INTO users(login,password,role,email,name,surname,discount,balance) " +
            "VALUES(?,?,?,?,?,?,?,?)";
    
    private static final String SQL_UPDATE_USER = "UPDATE users SET login=?,password=?,role=?,email=?,name=?,surname=?,discount=?," +
            "balance=? WHERE id_user=?";
    
    private static final String SQL_UPDATE_USER_BALANCE = "UPDATE users SET balance=? WHERE id_user=?";
    
    private static final String SQL_UPDATE_USER_BALANCE_ADDITION = "UPDATE users SET balance=balance + ? WHERE id_user=?";
    
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id_user=?";

    private static final String PARAM_ID_USER = "id_user";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_ROLE = "role";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_DISCOUNT = "discount";
    private static final String PARAM_BALANCE = "balance";
    private static final String PARAM_ORDERS = "orders";

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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                users.add(createUser(resultSet));
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT);
            while (resultSet.next()) {
                UserOrderNumber userOrderNumber = new UserOrderNumber();
                userOrderNumber.setId(resultSet.getLong(PARAM_ID_USER));
                userOrderNumber.setLogin(resultSet.getString(PARAM_LOGIN));
                userOrderNumber.setPassword(resultSet.getString(PARAM_PASSWORD));
                userOrderNumber.setRole(resultSet.getInt(PARAM_ROLE));
                userOrderNumber.setEmail(resultSet.getString(PARAM_EMAIL));
                userOrderNumber.setName(resultSet.getString(PARAM_NAME));
                userOrderNumber.setSurname(resultSet.getString(PARAM_SURNAME));
                userOrderNumber.setDiscount(resultSet.getDouble(PARAM_DISCOUNT));
                userOrderNumber.setMoney(resultSet.getInt(PARAM_BALANCE));
                userOrderNumber.setOrderNumber(resultSet.getInt(PARAM_ORDERS));
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_BY_NAME)) {
            ps.setString(1,name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                LOG.debug(resultSet.getString(PARAM_LOGIN));
                LOG.debug(resultSet.getInt(PARAM_ROLE));
                user = createUser(resultSet);
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_MONEY_BY_USER_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                money = resultSet.getDouble(PARAM_BALANCE);
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_PASSWORD_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString(PARAM_PASSWORD);
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_USER_PASSWORD_BY_ID)) {
            ps.setString(1,password);
            ps.setLong(2,id);
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(User user) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_USER)) {
            setUserPreparedStatement(user, ps);
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(User user) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_USER)) {
            setUserPreparedStatement(user, ps);
            ps.setLong(9,user.getId());
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
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
        return updateUserBalanceWithQuery(id, money, SQL_UPDATE_USER_BALANCE);
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
        return updateUserBalanceWithQuery(id, moneyToAdd, SQL_UPDATE_USER_BALANCE_ADDITION);
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public User findEntityById(Long id) throws DAOException {
        User user = new User();
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_USER)) {
            ps.setLong(1,id);
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
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
    private boolean updateUserBalanceWithQuery(Long id, double money, String query) throws DAOException{
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDouble(1,money);
            ps.setLong(2,id);
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Sets the user prepared statement.
     *
     * @param user the user
     * @param ps the prepared statement
     * @throws DAOException the DAO exception
     */
    private void setUserPreparedStatement(User user, PreparedStatement ps) throws DAOException {
        try {
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getRole());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getName());
            ps.setString(6,user.getSurname());
            ps.setDouble(7,user.getDiscount());
            ps.setDouble(8,user.getMoney());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Creates user from result set.
     *
     * @param resultSet the resultSet
     * @throws DAOException the DAO exception
     */
    private User createUser(ResultSet resultSet) throws DAOException {
        try {
            User user = new User();
            user.setId(resultSet.getLong(PARAM_ID_USER));
            user.setLogin(resultSet.getString(PARAM_LOGIN));
            user.setPassword(resultSet.getString(PARAM_PASSWORD));
            user.setRole(resultSet.getInt(PARAM_ROLE));
            user.setEmail(resultSet.getString(PARAM_EMAIL));
            user.setName(resultSet.getString(PARAM_NAME));
            user.setSurname(resultSet.getString(PARAM_SURNAME));
            user.setDiscount(resultSet.getDouble(PARAM_DISCOUNT));
            user.setMoney(resultSet.getInt(PARAM_BALANCE));
            return user;
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }
}
