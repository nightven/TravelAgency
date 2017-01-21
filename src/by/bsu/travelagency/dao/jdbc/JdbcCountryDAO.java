package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.CountryDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class JdbcCountryDAO implements CountryDAO {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(JdbcCountryDAO.class);

    /** The Constant SQL_SELECT_ALL_COUNTRIES. */
    private static final String SQL_SELECT_ALL_COUNTRIES = "SELECT id_country,countries.name AS name_country FROM countries";

    /** The Constant SQL_SELECT_COUNTRY_BY_ID. */
    private static final String SQL_SELECT_COUNTRY_BY_ID = "SELECT id_country,countries.name AS name_country FROM countries WHERE id_country=?";

    /** The Constant SQL_INSERT_COUNTRY. */
    private static final String SQL_INSERT_COUNTRY = "INSERT INTO countries(name) VALUES(?)";

    /** The Constant SQL_UPDATE_COUNTRY. */
    private static final String SQL_UPDATE_COUNTRY = "UPDATE countries SET name=? WHERE id_country=?";

    /** The Constant SQL_SELECT_ALL_CITIES_BY_COUNTRY. */
    private static final String SQL_SELECT_ALL_CITIES_BY_COUNTRY = "SELECT id_city FROM cities WHERE id_country = ?";

    /** The Constant SQL_DELETE_CITY. */
    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city=?";

    /** The Constant SQL_DELETE_COUNTRY. */
    private static final String SQL_DELETE_COUNTRY = "DELETE FROM countries WHERE id_country=?";


    /**
     * Find all countries.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Country> findAllCountries() throws DAOException {
        List<Country> countries = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_COUNTRIES);
            createCountries(resultSet, countries);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return countries;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(Country country) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_COUNTRY)) {
            ps.setString(1,country.getNameCountry());
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
    public Country findEntityById(Long id) throws DAOException {
        Country country = new Country();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_COUNTRY_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                country.setIdCountry(resultSet.getLong("id_country"));
                country.setNameCountry(resultSet.getString("name_country"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return country;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_COUNTRY); PreparedStatement ps2 = cn.prepareStatement(SQL_SELECT_ALL_CITIES_BY_COUNTRY); PreparedStatement ps3 = cn.prepareStatement(SQL_DELETE_CITY)) {
            ps2.setLong(1,id);
            ResultSet resultSet = ps2.executeQuery();
            while (resultSet.next()){
                ps3.setLong(1,resultSet.getLong("id_city"));
                ps3.executeUpdate();
            }
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
    public boolean delete(Country entity) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(Country country) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_COUNTRY)) {
            ps.setString(1,country.getNameCountry());
            ps.setLong(2,country.getIdCountry());
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
     * Creates the country.
     *
     * @param resultSet the result set
     * @param countries the countries
     * @throws SQLException the SQL exception
     */
    private void createCountries (ResultSet resultSet, List<Country> countries) throws SQLException {
        while (resultSet.next()) {
            Country country = new Country();
            country.setIdCountry(resultSet.getLong("id_country"));
            country.setNameCountry(resultSet.getString("name_country"));
            countries.add(country);
        }
    }
}
