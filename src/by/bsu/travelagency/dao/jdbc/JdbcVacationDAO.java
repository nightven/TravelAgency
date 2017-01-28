package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.pool.ConnectionPool;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcVacationDAO implements VacationDAO {

    private final static Logger LOG = Logger.getLogger(JdbcVacationDAO.class);
    
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1";

    private static final String SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND tours.name LIKE ? AND id_type=1";
    
    private static final String SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND departure_date=? AND id_type=1";
    
    private static final String SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND arrival_date=? AND id_type=1";
    
    private static final String SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country," +
            " countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours " +
            "JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) " +
            "JOIN countries USING (id_country) WHERE departure_date>? AND price=? AND id_type=1";
    
    private static final String SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? " +
            "AND id_transport = (SELECT id_transport FROM transport_type WHERE transport_type=?) AND id_type=1";
    
    private static final String SQL_SELECT_VACATION_BY_ID = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date," +
            "price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, " +
            "countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours " +
            "JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) " +
            "JOIN countries USING (id_country) WHERE id_tour=? AND id_type=1";

    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY name";
    
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT id_tour,tours.name,summary," +
            "description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY departure_date";
    
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT id_tour,tours.name,summary," +
            "description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY arrival_date";
    
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY price";
    
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY transport";


    private static final String SQL_SELECT_LAST_VACATIONS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date," +
            "price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, " +
            "countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours " +
            "JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) " +
            "JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY id_tour DESC LIMIT 6";
    
    private static final String SQL_SELECT_LAST_VACATION_ID = "SELECT id_tour FROM tours WHERE id_type=1 ORDER BY id_tour DESC LIMIT 1";
    
    private static final String SQL_SELECT_PATH_IMAGE_VACATION_BY_ID = "SELECT path_image FROM tours WHERE id_tour=? AND id_type=1";
    
    private static final String SQL_INSERT_VACATION = "INSERT INTO tours(name,summary,description,departure_date,arrival_date,price," +
            "hot_tour,hotel,id_transport,services,path_image,id_type) VALUES(?,?,?,?,?,?,?,?,(SELECT id_transport FROM transport_type " +
            "WHERE transport_type=?),?,?,1)";

    private static final String SQL_INSERT_VACATION_CITY = "INSERT INTO tours_cities(id_tour,id_city,`order`) VALUES(?,?,1)";

    private static final String SQL_UPDATE_VACATION_CITY = "UPDATE tours_cities SET id_city=? WHERE id_tour=?";
    
    private static final String SQL_UPDATE_VACATION = "UPDATE tours SET name=?,summary=?,description=?,departure_date=?,arrival_date=?," +
            "price=?,hot_tour=?,hotel=?,id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?),services=?," +
            "path_image=? WHERE id_tour=? AND id_type=1";
    
    private static final String SQL_DELETE_VACATION = "DELETE FROM tours WHERE id_tour=? AND id_type=1";

    private static final String SQL_DELETE_VACATION_CITY = "DELETE FROM tours_cities WHERE id_tour=?";

    private static final String PARAM_ID_TOUR = "id_tour";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SUMMARY = "summary";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_DEPARTURE_DATE = "departure_date";
    private static final String PARAM_ARRIVAL_DATE = "arrival_date";
    private static final String PARAM_PRICE = "price";
    private static final String PARAM_HOT_TOUR = "hot_tour";
    private static final String PARAM_ID_CITY = "id_city";
    private static final String PARAM_DESTINATION_CITY = "destination_city";
    private static final String PARAM_ID_COUNTRY = "id_country";
    private static final String PARAM_DESTINATION_COUNTRY = "destination_country";
    private static final String PARAM_TRANSPORT = "transport";
    private static final String PARAM_SERVICES = "services";
    private static final String PARAM_PATH_IMAGE = "path_image";
    private static final String PARAM_HOTEL = "hotel";

    private static final HashMap<String, String> mapForSortCriterion = new HashMap<>();

    static {
        mapForSortCriterion.put("name", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME);
        mapForSortCriterion.put("departure_date", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
        mapForSortCriterion.put("arrival_date", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
        mapForSortCriterion.put("price", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE);
        mapForSortCriterion.put("transport", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT);
    }

    /**
     * Instantiates a new JdbcVacationDAO.
     */
    private JdbcVacationDAO() {
    }

    /** Nested class JdbcVacationDAOHolder. */
    private static class JdbcVacationDAOHolder {
        private static final JdbcVacationDAO HOLDER_INSTANCE = new JdbcVacationDAO();
    }


    /**
     * Gets the instance.
     *
     * @return the JdbcVacationDAOHolder instance
     */
    public static JdbcVacationDAO getInstance() {
        return JdbcVacationDAOHolder.HOLDER_INSTANCE;
    }

    /**
     * Find all vacations after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findAllVacationsAfterNow(Date nowDate) throws DAOException {
        return selectVacationsAfterNowWithQuery(nowDate, SQL_SELECT_ALL_VACATIONS_AFTER_NOW);
    }

    /**
     * Find all sort vacations after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findAllSortVacationsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(mapForSortCriterion.getOrDefault(criterion, SQL_SELECT_ALL_VACATIONS_AFTER_NOW))) {
            ps.setDate(1,nowDate);
            ResultSet resultSet = ps.executeQuery();
            List<Vacation> vacations = getListVacations(resultSet);
            if (!order){
                Collections.reverse(vacations);
            }
            return vacations;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Find vacations by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findVacationsByNameAfterNow(Date nowDate, String name) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet = ps.executeQuery();
            return getListVacations(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Find vacations by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findVacationsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException {
        return findVacationByDateWithQuery(nowDate, departureDate, SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW);
    }

    /**
     * Find vacations by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findVacationsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException {
        return findVacationByDateWithQuery(nowDate, arrivalDate, SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW);
    }

    /**
     * Find vacations by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findVacationsByPriceAfterNow(Date nowDate, double price) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setDouble(2,price);
            ResultSet resultSet = ps.executeQuery();
            return getListVacations(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Find vacations by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findVacationsByTransportAfterNow(Date nowDate, String transport) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet = ps.executeQuery();
            return getListVacations(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Find last vacation id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    public Long findLastVacationId() throws DAOException {
        Long id = 0L;
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_VACATION_ID);
            if (resultSet.next()) {
                id = resultSet.getLong(PARAM_ID_TOUR);
                LOG.debug("Last vacation id: " + resultSet.getLong(PARAM_ID_TOUR));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return id;
    }

    /**
     * Find path image vacation by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findPathImageVacationById(Long id) throws DAOException {
        String pathImage = null;
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_VACATION_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                pathImage = resultSet.getString(PARAM_PATH_IMAGE);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return pathImage;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(Vacation vacation) throws DAOException {
        boolean flag = true;
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_VACATION);
             PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_VACATION_CITY);
             Statement st = cn.createStatement()) {
            fillVacationPreparedStatement(vacation, ps);
            if (ps.executeUpdate() == 0){
                flag = false;
            }
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_VACATION_ID);
            Long vacationId = null;
            if (resultSet.next()){
                vacationId = resultSet.getLong(PARAM_ID_TOUR);
            }
            ps2.setLong(1,vacationId);
            ps2.setLong(2,vacation.getCities().get(0).getId());
            if (ps2.executeUpdate() == 0){
                flag = false;
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
    public boolean update(Vacation vacation) throws DAOException {
        boolean flag = true;
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_VACATION);
             PreparedStatement ps2 = cn.prepareStatement(SQL_UPDATE_VACATION_CITY)) {
            fillVacationPreparedStatement(vacation,ps);
            ps.setLong(12,vacation.getId());
            if (ps.executeUpdate() == 0){
                flag = false;
            }
            ps2.setLong(1,vacation.getCities().get(0).getId());
            ps2.setLong(2,vacation.getId());
            if (ps2.executeUpdate() == 0){
                flag = false;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /**
     * Select last vacations.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> selectLastVacations(Date nowDate) throws DAOException {
        return selectVacationsAfterNowWithQuery(nowDate, SQL_SELECT_LAST_VACATIONS);
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Vacation findEntityById(Long id) throws DAOException {
        Vacation vacation = new Vacation();
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATION_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                vacation.setId(resultSet.getLong(PARAM_ID_TOUR));
                vacation.setName(resultSet.getString(PARAM_NAME));
                vacation.setSummary(resultSet.getString(PARAM_SUMMARY));
                vacation.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                vacation.setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
                vacation.setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
                vacation.setPrice(resultSet.getDouble(PARAM_PRICE));
                vacation.setLastMinute(resultSet.getBoolean(PARAM_HOT_TOUR));
                vacation.setHotel(resultSet.getString(PARAM_HOTEL));
                ArrayList<City> cities = new ArrayList<>();
                City city = new City();
                city.setId(resultSet.getLong(PARAM_ID_CITY));
                city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                Country country = new Country();
                country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                city.setCountry(country);
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(resultSet.getString(PARAM_TRANSPORT)));
                vacation.setServices(resultSet.getString(PARAM_SERVICES));
                vacation.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacation;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = true;
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_VACATION);
             PreparedStatement ps2 = cn.prepareStatement(SQL_DELETE_VACATION_CITY)) {
            ps2.setLong(1,id);
            if (ps2.executeUpdate() == 0){
                flag = false;
            }
            ps.setLong(1,id);
            if (ps.executeUpdate() == 0){
                flag = false;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /**
     * General get of vacation list.
     *
     * @param resultSet the ResultSet
     * @return the list of vacations
     * @throws DAOException the DAO exception
     */
    private List<Vacation> getListVacations(ResultSet resultSet) throws DAOException {
        List<Vacation> vacations = new ArrayList<>();
        try {
            Map<Long, Vacation> vacationMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (!vacationMap.containsKey(resultSet.getLong(PARAM_ID_TOUR))) {
                    Vacation vacation = new Vacation();
                    vacation.setId(resultSet.getLong(PARAM_ID_TOUR));
                    vacation.setName(resultSet.getString(PARAM_NAME));
                    vacation.setSummary(resultSet.getString(PARAM_SUMMARY));
                    vacation.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                    vacation.setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
                    vacation.setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
                    vacation.setPrice(resultSet.getDouble(PARAM_PRICE));
                    vacation.setLastMinute(resultSet.getBoolean(PARAM_HOT_TOUR));
                    vacation.setHotel(resultSet.getString(PARAM_HOTEL));
                    ArrayList<City> cities = new ArrayList<>();
                    City city = new City();
                    city.setId(resultSet.getLong(PARAM_ID_CITY));
                    city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                    Country country = new Country();
                    country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                    country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                    city.setCountry(country);
                    cities.add(city);
                    vacation.setCities(cities);
                    vacation.setTransport(Transport.valueOf(resultSet.getString(PARAM_TRANSPORT)));
                    vacation.setServices(resultSet.getString(PARAM_SERVICES));
                    vacation.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
                    vacationMap.put(vacation.getId(), vacation);
                }
            }
            vacations.addAll(vacationMap.values());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
    }

    /**
     * General select vacations after now.
     *
     * @param nowDate today's date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Vacation> selectVacationsAfterNowWithQuery(Date nowDate, String query) throws DAOException{
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1, nowDate);
            ResultSet resultSet = ps.executeQuery();
            return getListVacations(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * General find vacations by date after now.
     *
     * @param nowDate today's date
     * @param generalDate arrival or departure date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Vacation> findVacationByDateWithQuery(Date nowDate, Date generalDate, String query) throws DAOException {
        try (Connection cn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ps.setDate(2,generalDate);
            ResultSet resultSet = ps.executeQuery();
            return getListVacations(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * Fill the vacation prepared statement.
     *
     * @param vacation the vacation
     * @param ps the prepared statement
     * @throws DAOException the DAO exception
     */
    private void fillVacationPreparedStatement(Vacation vacation, PreparedStatement ps) throws DAOException {
        try {
            ps.setString(1,vacation.getName());
            ps.setString(2,vacation.getSummary());
            ps.setString(3,vacation.getDescription());
            ps.setDate(4,new java.sql.Date(vacation.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(vacation.getArrivalDate().getTime()));
            ps.setDouble(6,vacation.getPrice());
            ps.setInt(7,(vacation.getLastMinute()) ? 1 : 0);
            ps.setString(8,vacation.getHotel());
            ps.setString(9,vacation.getTransport().toString());
            ps.setString(10,vacation.getServices());
            ps.setString(11,vacation.getPathImage());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }
}
