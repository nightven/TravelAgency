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
    private static final String SQL_SELECT_ALL_USERS = "SELECT iduser,login,password,role,email,name,surname FROM users";
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT iduser,login,password,role,email,name,surname FROM users WHERE login=?";
    private static final String SQL_INSERT_USER = "INSERT INTO users(login,password,role,email,name,surname) VALUES(?,?,?,?,?,?)";

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
        throw new UnsupportedOperationException();
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
