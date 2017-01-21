package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.CityDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class JdbcCityDAO implements CityDAO {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(JdbcCityDAO.class);

    /** The Constant SQL_SELECT_ALL_CITIES. */
    private static final String SQL_SELECT_ALL_CITIES = "SELECT id_city,cities.name AS name_city,id_country,countries.name AS name_country FROM cities JOIN countries USING (id_country) ORDER BY cities.name";

    /** The Constant SQL_SELECT_CITY_BY_ID. */
    private static final String SQL_SELECT_CITY_BY_ID = "SELECT id_city,cities.name AS name_city,id_country,countries.name AS name_country FROM cities JOIN countries USING (id_country) WHERE id_city=?";

    /** The Constant SQL_INSERT_CITY. */
    private static final String SQL_INSERT_CITY = "INSERT INTO cities(name,id_country) VALUES(?,?)";

    /** The Constant SQL_UPDATE_CITY. */
    private static final String SQL_UPDATE_CITY = "UPDATE cities SET name=?,id_country=? WHERE id_city=?";

    /** The Constant SQL_DELETE_CITY. */
    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city=?";

    /**
     * Find all cities.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<City> findAllCities() throws DAOException {
        List<City> cities = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CITIES);
            createCites(resultSet, cities);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return cities;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(City city) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_CITY)) {
            ps.setString(1,city.getNameCity());
            ps.setLong(2,city.getCountry().getIdCountry());
            if (ps.executeUpdate() != 0){
                flag = true;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public City findEntityById(Long id) throws DAOException {
        City city = new City();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_CITY_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                city.setIdCity(resultSet.getLong("id_city"));
                city.setNameCity(resultSet.getString("name_city"));
                Country country = new Country();
                country.setIdCountry(resultSet.getLong("id_country"));
                country.setNameCountry(resultSet.getString("name_country"));
                city.setCountry(country);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return city;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_CITY)) {
            ps.setLong(1,id);
            if (ps.executeUpdate() != 0){
                flag = true;
            }
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
    public boolean delete(City entity) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(City city) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_CITY)) {
            ps.setString(1,city.getNameCity());
            ps.setLong(2,city.getCountry().getIdCountry());
            ps.setLong(3,city.getIdCity());
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
     * Creates the city.
     *
     * @param resultSet the result set
     * @param cities the cities
     * @throws SQLException the SQL exception
     */
    private void createCites (ResultSet resultSet, List<City> cities) throws SQLException {
        while (resultSet.next()) {
            City city = new City();
            city.setIdCity(resultSet.getLong("id_city"));
            city.setNameCity(resultSet.getString("name_city"));
            Country country = new Country();
            country.setIdCountry(resultSet.getLong("id_country"));
            country.setNameCountry(resultSet.getString("name_country"));
            city.setCountry(country);
            cities.add(city);
        }
    }
// TODO: 1/16/2017 delete this method if not used
/*    *//**
     * General find all user orders by user id and date.
     *
     * @param userId the user id
     * @param nowDate the today's date
     * @return the list
     * @throws DAOException the DAO exception
     *//*
    private List<OrderTourInfo> generalFindAllUserOrdersByDate(Long userId, Date nowDate, String query) throws DAOException {
        List<OrderTourInfo> orderTourInfos = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setLong(1,userId);
            ps.setDate(2,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return orderTourInfos;
    }*/
}
