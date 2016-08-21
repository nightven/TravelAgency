package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class UserDAO extends AbstractDAO<Long, User> {

    private final static Logger LOG = Logger.getLogger(UserDAO.class);
    private static final String SQL_SELECT_ALL_USERS = "SELECT iduser,login,password,role,email,name,surname,discount,money FROM users";
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT iduser,login,password,role,email,name,surname,discount,money FROM users WHERE login=?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT iduser,login,password,role,email,name,surname,discount,money FROM users WHERE iduser=?";
    private static final String SQL_SELECT_USER_PASSWORD_BY_ID = "SELECT password FROM users WHERE iduser=?";
    private static final String SQL_UPDATE_USER_PASSWORD_BY_ID = "UPDATE users SET password=? WHERE iduser=?";
    private static final String SQL_SELECT_MONEY_BY_USER_ID = "SELECT money FROM users WHERE iduser=?";
    private static final String SQL_INSERT_USER = "INSERT INTO users(login,password,role,email,name,surname,discount,money) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER_BALANCE = "UPDATE users SET money=? WHERE iduser=?";
    private static final String SQL_UPDATE_USER_BALANCE_ADDITION = "UPDATE users SET money=money + ? WHERE iduser=?";


    public List<User> findAllUsers() {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(st);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return users;
    }

    public User findUserByName(String name) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return user;
    }

    public int findMoneyByUserId(Long id) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return money;
    }

    public String findPasswordByUserId(Long id) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return password;
    }

    public boolean updatePasswordByUserId(Long id, String password) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    public boolean insertUser(User user) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    public boolean updateUserBalance(Long id, int money) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    public boolean updateUserBalanceAddition(Long id, int moneyToAdd) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    public void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(Connection cn){
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User findEntityById(Long id) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException();
    }
}
