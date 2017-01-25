package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcVacationDAO implements VacationDAO {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(JdbcVacationDAO.class);
    
    /** The Constant SQL_SELECT_ALL_VACATIONS. */
    private static final String SQL_SELECT_ALL_VACATIONS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE id_type=1";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1";

    /** The Constant SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND tours.name LIKE ? AND id_type=1";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND departure_date=? AND id_type=1";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND arrival_date=? AND id_type=1";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND price=? AND id_type=1";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_transport = (SELECT id_transport FROM transport_type WHERE transport_type=?) AND id_type=1";
    
    /** The Constant SQL_SELECT_VACATION_BY_ID. */
    private static final String SQL_SELECT_VACATION_BY_ID = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE id_tour=? AND id_type=1";

    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY name";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY departure_date";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY arrival_date";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY price";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY transport";


    /** The Constant SQL_SELECT_LAST_VACATIONS. */
    private static final String SQL_SELECT_LAST_VACATIONS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=1 ORDER BY id_tour DESC LIMIT 6";
    
    /** The Constant SQL_SELECT_LAST_VACATION_ID. */
    private static final String SQL_SELECT_LAST_VACATION_ID = "SELECT id_tour FROM tours WHERE id_type=1 ORDER BY id_tour DESC LIMIT 1";
    
    /** The Constant SQL_SELECT_PATH_IMAGE_VACATION_BY_ID. */
    private static final String SQL_SELECT_PATH_IMAGE_VACATION_BY_ID = "SELECT path_image FROM tours WHERE id_tour=? AND id_type=1";
    
    /** The Constant SQL_INSERT_VACATION. */
    private static final String SQL_INSERT_VACATION = "INSERT INTO tours(name,summary,description,departure_date,arrival_date,price,hot_tour,hotel,id_transport,services,path_image,id_type) VALUES(?,?,?,?,?,?,?,?,(SELECT id_transport FROM transport_type WHERE transport_type=?),?,?,1)";

    /** The Constant SQL_INSERT_VACATION_CITY. */
    private static final String SQL_INSERT_VACATION_CITY = "INSERT INTO tours_cities(id_tour,id_city,`order`) VALUES(?,?,1)";

    /** The Constant SQL_UPDATE_VACATION_CITY. */
    private static final String SQL_UPDATE_VACATION_CITY = "UPDATE tours_cities SET id_city=? WHERE id_tour=?";
    
    /** The Constant SQL_UPDATE_VACATION. */
    private static final String SQL_UPDATE_VACATION = "UPDATE tours SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,hot_tour=?,hotel=?,id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?),services=?,path_image=? WHERE id_tour=? AND id_type=1";
    
    /** The Constant SQL_DELETE_VACATION. */
    private static final String SQL_DELETE_VACATION = "DELETE FROM tours WHERE id_tour=? AND id_type=1";

    /** The Constant SQL_DELETE_VACATION_CITY. */
    private static final String SQL_DELETE_VACATION_CITY = "DELETE FROM tours_cities WHERE id_tour=?";

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
     * Find all vacations.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findAllVacations() throws DAOException {
        List<Vacation> vacations = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_VACATIONS);
            generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
    }

    /**
     * Find all vacations after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findAllVacationsAfterNow(Date nowDate) throws DAOException {
        return generalSelectVacationsAfterNow(nowDate, SQL_SELECT_ALL_VACATIONS_AFTER_NOW);
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
        List<Vacation> vacations = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME);
        map.put("departure_date", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
        map.put("arrival_date", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
        map.put("price", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE);
        map.put("transport", SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT);
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(map.get(criterion))) {
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        if (!order){
            Collections.reverse(vacations);
        }
        return vacations;
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
        List<Vacation> vacations = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
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
        return generalFindVacationByDate(nowDate, departureDate, SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW);
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
        return generalFindVacationByDate(nowDate, arrivalDate, SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW);
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
        List<Vacation> vacations = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setDouble(2,price);
            ResultSet resultSet =
                    ps.executeQuery();
           generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
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
        List<Vacation> vacations = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
    }

    /**
     * Find last vacation id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    public Long findLastVacationId() throws DAOException {
        Long id = 0L;
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_VACATION_ID);
            while (resultSet.next()) {
                id = resultSet.getLong("id_tour");
                LOG.debug("Last vacation id: " + resultSet.getLong("id_tour"));
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_VACATION_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                pathImage = resultSet.getString("path_image");
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
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_VACATION); PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_VACATION_CITY); Statement st = cn.createStatement()) {
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
            ps.executeUpdate();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_VACATION_ID);
            Long vacationId = null;
            while (resultSet.next()){
                vacationId = resultSet.getLong("id_tour");
            }
            ps2.setLong(1,vacationId);
            ps2.setLong(2,vacation.getCities().get(0).getIdCity());
            ps2.executeUpdate();
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
    public boolean update(Vacation vacation) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_VACATION); PreparedStatement ps2 = cn.prepareStatement(SQL_UPDATE_VACATION_CITY)) {
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
            ps.setLong(12,vacation.getId());
            ps.executeUpdate();
            ps2.setLong(1,vacation.getCities().get(0).getIdCity());
            ps2.setLong(2,vacation.getId());
            ps2.executeUpdate();
            flag = true;
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
        return generalSelectVacationsAfterNow(nowDate, SQL_SELECT_LAST_VACATIONS);
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Vacation findEntityById(Long id) throws DAOException {
        Vacation vacation = new Vacation();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VACATION_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                vacation.setId(resultSet.getLong("id_tour"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getDouble("price"));
                vacation.setLastMinute(resultSet.getBoolean("hot_tour"));
                vacation.setHotel(resultSet.getString("hotel"));
                ArrayList<City> cities = new ArrayList<>();
                City city = new City();
                city.setIdCity(resultSet.getLong("id_city"));
                city.setNameCity(resultSet.getString("destination_city"));
                Country country = new Country();
                country.setIdCountry(resultSet.getLong("id_country"));
                country.setNameCountry(resultSet.getString("destination_country"));
                city.setCountry(country);
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
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
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_VACATION); PreparedStatement ps2 = cn.prepareStatement(SQL_DELETE_VACATION_CITY)) {
            ps2.setLong(1,id);
            ps2.execute();
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
    public boolean delete(Vacation entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * General get of vacation list.
     *
     * @param resultSet the ResultSet
     * @param vacations the list of vacations
     * @throws DAOException the DAO exception
     */
    private void generalGetListVacations(ResultSet resultSet, List<Vacation> vacations) throws DAOException {
        try {
            Map<Long, Vacation> vacationMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (!vacationMap.containsKey(resultSet.getLong("id_tour"))) {
                    Vacation vacation = new Vacation();
                    vacation.setId(resultSet.getLong("id_tour"));
                    vacation.setName(resultSet.getString("name"));
                    vacation.setSummary(resultSet.getString("summary"));
                    vacation.setDescription(resultSet.getString("description"));
                    vacation.setDepartureDate(resultSet.getDate("departure_date"));
                    vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                    vacation.setPrice(resultSet.getDouble("price"));
                    vacation.setLastMinute(resultSet.getBoolean("hot_tour"));
                    vacation.setHotel(resultSet.getString("hotel"));
                    ArrayList<City> cities = new ArrayList<>();
                    City city = new City();
                    city.setIdCity(resultSet.getLong("id_city"));
                    city.setNameCity(resultSet.getString("destination_city"));
                    Country country = new Country();
                    country.setIdCountry(resultSet.getLong("id_country"));
                    country.setNameCountry(resultSet.getString("destination_country"));
                    city.setCountry(country);
                    cities.add(city);
                    vacation.setCities(cities);
                    vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                    vacation.setServices(resultSet.getString("services"));
                    vacation.setPathImage(resultSet.getString("path_image"));
                    vacationMap.put(vacation.getId(), vacation);
                }
            }
            vacations.addAll(vacationMap.values());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * General select vacations after now.
     *
     * @param nowDate today's date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Vacation> generalSelectVacationsAfterNow(Date nowDate, String query) throws DAOException{
        List<Vacation> vacations = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1, nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
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
    private List<Vacation> generalFindVacationByDate(Date nowDate, Date generalDate, String query) throws DAOException {
        List<Vacation> vacations = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ps.setDate(2,generalDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListVacations(resultSet, vacations);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return vacations;
    }
}
