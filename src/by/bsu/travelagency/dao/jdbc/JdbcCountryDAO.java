package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.dao.CountryDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.pool.ConnectionPool;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCountryDAO implements CountryDAO {

    private static final Logger LOG = Logger.getLogger(JdbcCountryDAO.class);

    private static final String SQL_SELECT_ALL_COUNTRIES = "SELECT id_country,countries.name AS name_country FROM countries";

    private static final String SQL_SELECT_COUNTRY_BY_ID = "SELECT id_country,countries.name AS name_country FROM countries" +
            " WHERE id_country=?";

    private static final String SQL_INSERT_COUNTRY = "INSERT INTO countries(name) VALUES(?)";

    private static final String SQL_UPDATE_COUNTRY = "UPDATE countries SET name=? WHERE id_country=?";

    private static final String SQL_SELECT_ALL_CITIES_BY_COUNTRY = "SELECT id_city FROM cities WHERE id_country = ?";

    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city=?";

    private static final String SQL_DELETE_COUNTRY = "DELETE FROM countries WHERE id_country=?";

    private static final String PARAM_ID_COUNTRY = "id_country";

    private static final String PARAM_NAME_COUNTRY = "name_country";

    private static final String PARAM_ID_CITY = "id_city";

    /**
     * Instantiates a new JdbcCountryDAO.
     */
    private JdbcCountryDAO() {
    }

    /** Nested class JdbcCountryDAOHolder. */
    private static class JdbcCountryDAOHolder {
        private static final JdbcCountryDAO HOLDER_INSTANCE = new JdbcCountryDAO();
    }


    /**
     * Gets the instance.
     *
     * @return the JdbcCountryDAOHolder instance
     */
    public static JdbcCountryDAO getInstance() {
        return JdbcCountryDAOHolder.HOLDER_INSTANCE;
    }


    /**
     * Find all countries.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Country> findAllCountries() throws DAOException {
        List<Country> countries = new ArrayList<>();
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             Statement st = cn.createStatement()) {
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_COUNTRY)) {
            ps.setString(1,country.getNameCountry());
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Country findEntityById(Long id) throws DAOException {
        Country country = new Country();
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_COUNTRY_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                country.setNameCountry(resultSet.getString(PARAM_NAME_COUNTRY));
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
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_COUNTRY);
             PreparedStatement ps2 = cn.prepareStatement(SQL_SELECT_ALL_CITIES_BY_COUNTRY);
             PreparedStatement ps3 = cn.prepareStatement(SQL_DELETE_CITY)) {
            ps2.setLong(1,id);
            ResultSet resultSet = ps2.executeQuery();
            while (resultSet.next()){
                ps3.setLong(1,resultSet.getLong(PARAM_ID_CITY));
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
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(Country country) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_COUNTRY)) {
            ps.setString(1,country.getNameCountry());
            ps.setLong(2,country.getId());
            return (ps.executeUpdate() != 0);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
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
            country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
            country.setNameCountry(resultSet.getString(PARAM_NAME_COUNTRY));
            countries.add(country);
        }
    }
}
