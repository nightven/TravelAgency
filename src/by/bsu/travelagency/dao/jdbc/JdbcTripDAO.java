package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Михаил on 2/24/2016.
 */
public class JdbcTripDAO implements TripDAO {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(JdbcTripDAO.class);
    
    /** The Constant SQL_SELECT_ALL_TRIPS. */
    private static final String SQL_SELECT_ALL_TRIPS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE id_type=2 ORDER BY tours_cities.`order`";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 ORDER BY tours_cities.`order`";

    /** The Constant SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND tours.name LIKE ? AND id_type=2 ORDER BY tours_cities.`order`";
    
    /** The Constant SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND departure_date=? AND id_type=2 ORDER BY tours_cities.`order`";
    
    /** The Constant SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND arrival_date=? AND id_type=2 ORDER BY tours_cities.`order`";
    
    /** The Constant SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND price=? AND id_type=2 ORDER BY tours_cities.`order`";
    
    /** The Constant SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?) AND id_type=2 ORDER BY tours_cities.`order`";
    
    /** The Constant SQL_SELECT_TRIP_BY_ID. */
    private static final String SQL_SELECT_TRIP_BY_ID = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE id_tour=? AND id_type=2 ORDER BY tours_cities.`order`";

    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 ORDER BY tours.name,tours_cities.`order`";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 ORDER BY departure_date,tours_cities.`order`";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 ORDER BY arrival_date,tours_cities.`order`";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 ORDER BY price,tours_cities.`order`";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 ORDER BY transport,tours_cities.`order`";

    /** The Constant SQL_SELECT_LAST_TRIPS. */
    private static final String SQL_SELECT_LAST_TRIPS = "SELECT t.id_tour,t.name,t.summary,t.description,t.departure_date,t.arrival_date,t.price,t.hot_tour,t.attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,t.transport,t.services,t.path_image FROM (SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) WHERE departure_date>? AND id_type=2 ORDER BY id_tour DESC LIMIT 6) t JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country)";
    
    /** The Constant SQL_SELECT_LAST_TRIP_ID. */
    private static final String SQL_SELECT_LAST_TRIP_ID = "SELECT id_tour FROM tours WHERE id_type=2 ORDER BY id_tour DESC LIMIT 1";
    
    /** The Constant SQL_SELECT_PATH_IMAGE_TRIP_BY_ID. */
    private static final String SQL_SELECT_PATH_IMAGE_TRIP_BY_ID = "SELECT path_image FROM tours WHERE id_tour=? AND id_type=2";
    
    /** The Constant SQL_INSERT_TRIP. */
    private static final String SQL_INSERT_TRIP = "INSERT INTO tours(name,summary,description,departure_date,arrival_date,price,hot_tour,attractions,id_transport,services,path_image,id_type) VALUES(?,?,?,?,?,?,?,?,(SELECT id_transport FROM transport_type WHERE transport_type=?),?,?,2)";

    /** The Constant SQL_INSERT_TRIP_CITY. */
    private static final String SQL_INSERT_TRIP_CITY = "INSERT INTO tours_cities(id_tour,id_city,`order`) VALUES(?,?,?)";
    
    /** The Constant SQL_UPDATE_TRIP. */
    private static final String SQL_UPDATE_TRIP = "UPDATE tours SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,hot_tour=?,attractions=?,id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?),services=?,path_image=? WHERE id_tour=? AND id_type=2";

    /** The Constant SQL_UPDATE_TRIP_CITY. */
    private static final String SQL_UPDATE_TRIP_CITY = "UPDATE tours_cities SET id_city=? WHERE id_tour=?";
    
    /** The Constant SQL_DELETE_TRIP. */
    private static final String SQL_DELETE_TRIP = "DELETE FROM tours WHERE id_tour=? AND id_type=2";

    /** The Constant SQL_DELETE_TRIP_CITY. */
    private static final String SQL_DELETE_TRIP_CITY = "DELETE FROM tours_cities WHERE id_tour=?";

    /**
     * Find all trips.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findAllTrips() throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_TRIPS);
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trips;
    }

    /**
     * Find all trips after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findAllTripsAfterNow(Date nowDate) throws DAOException {
        return generalSelectTripsAfterNow(nowDate, SQL_SELECT_ALL_TRIPS_AFTER_NOW);
    }

    /**
     * Find all sort trips after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findAllSortTripsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME);
        map.put("departure_date", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
        map.put("arrival_date", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
        map.put("price", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE);
        map.put("transport", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT);
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(map.get(criterion))) {
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        if (!order){
            Collections.reverse(trips);
        }
        return trips;
    }

    /**
     * Find trips by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findTripsByNameAfterNow(Date nowDate, String name) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trips;
    }

    /**
     * Find trips by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findTripsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException {
        return generalFindTripByDate(nowDate, departureDate, SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW);
    }

    /**
     * Find trips by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findTripsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException {
        return generalFindTripByDate(nowDate, arrivalDate, SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW);
    }

    /**
     * Find trips by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findTripsByPriceAfterNow(Date nowDate, int price) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setInt(2,price);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trips;
    }

    /**
     * Find trips by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findTripsByTransportAfterNow(Date nowDate, String transport) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trips;
    }

    /**
     * Find last trip id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    public Long findLastTripId() throws DAOException {
        Long id = 0L;
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_TRIP_ID);
            while (resultSet.next()) {
                id = resultSet.getLong("id_tour");
                LOG.debug("Last trip id: " + resultSet.getLong("id_tour"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return id;
    }

    /**
     * Find path image trip by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findPathImageTripById(Long id) throws DAOException {
        String pathImage = null;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_TRIP_BY_ID)) {
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
    public boolean create(Trip trip) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_TRIP); PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_TRIP_CITY); Statement st = cn.createStatement()) {
            ps.setString(1,trip.getName());
            ps.setString(2,trip.getSummary());
            ps.setString(3,trip.getDescription());
            ps.setDate(4,new java.sql.Date(trip.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(trip.getArrivalDate().getTime()));
            ps.setLong(6,trip.getPrice());
            ps.setInt(7,(trip.getLastMinute()) ? 1 : 0);
            ps.setString(8,trip.getAttractions());
            ps.setString(9,trip.getTransport().toString());
            ps.setString(10,trip.getServices());
            ps.setString(11,trip.getPathImage());
            ps.executeUpdate();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_TRIP_ID);
            Long tripId = null;
            while (resultSet.next()){
                tripId = resultSet.getLong("id_tour");
            }
            for (int i = 0; i < trip.getCities().size(); i++) {
                ps2.setLong(1,tripId);
                ps2.setLong(2,trip.getCities().get(i).getIdCity());
                ps2.setInt(3,i+1);
                ps2.executeUpdate();
            }
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
    public boolean update(Trip trip) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_TRIP)) {
            ps.setString(1,trip.getName());
            ps.setString(2,trip.getSummary());
            ps.setString(3,trip.getDescription());
            ps.setDate(4,new java.sql.Date(trip.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(trip.getArrivalDate().getTime()));
            ps.setLong(6,trip.getPrice());
            ps.setInt(7,(trip.getLastMinute()) ? 1 : 0);
            ps.setString(8,trip.getAttractions());
//            ps.setString(9,trip.getDestinationCity());
//            ps.setString(10,trip.getDestinationCountry());
            ps.setString(9,trip.getTransport().toString());
            ps.setString(10,trip.getServices());
            ps.setString(11,trip.getPathImage());
            ps.setLong(12,trip.getId());
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
     * Select last trips.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> selectLastTrips(Date nowDate) throws DAOException {
        return generalSelectTripsAfterNow(nowDate, SQL_SELECT_LAST_TRIPS);
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Trip findEntityById(Long id) throws DAOException {
        Trip trip = new Trip();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIP_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (trip.getId() == 0L){
                    trip.setId(resultSet.getLong("id_tour"));
                    trip.setName(resultSet.getString("name"));
                    trip.setSummary(resultSet.getString("summary"));
                    trip.setDescription(resultSet.getString("description"));
                    trip.setDepartureDate(resultSet.getDate("departure_date"));
                    trip.setArrivalDate(resultSet.getDate("arrival_date"));
                    trip.setPrice(resultSet.getInt("price"));
                    trip.setLastMinute(resultSet.getBoolean("hot_tour"));
                    trip.setAttractions(resultSet.getString("attractions"));
                    trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                    trip.setServices(resultSet.getString("services"));
                    trip.setPathImage(resultSet.getString("path_image"));
                    ArrayList<City> cities = new ArrayList<City>();
                    City city = new City();
                    city.setIdCity(resultSet.getLong("id_city"));
                    city.setNameCity(resultSet.getString("destination_city"));
                    Country country = new Country();
                    country.setIdCountry(resultSet.getLong("id_country"));
                    country.setNameCountry(resultSet.getString("destination_country"));
                    city.setCountry(country);
                    cities.add(city);
                    trip.setCities(cities);
                }
                else {
                    City city = new City();
                    city.setIdCity(resultSet.getLong("id_city"));
                    city.setNameCity(resultSet.getString("destination_city"));
                    Country country = new Country();
                    country.setIdCountry(resultSet.getLong("id_country"));
                    country.setNameCountry(resultSet.getString("destination_country"));
                    city.setCountry(country);
                    trip.getCities().add(city);
                }
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trip;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_TRIP)) {
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
    public boolean delete(Trip entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * General get of trip list.
     *
     * @param resultSet the ResultSet
     * @param trips the list of trips
     * @throws DAOException the DAO exception
     */
    private void generalGetListTrips(ResultSet resultSet, List<Trip> trips) throws DAOException {
        try {
            Map<Long, Trip> tripMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (!tripMap.containsKey(resultSet.getLong("id_tour"))) {
                    Trip trip = new Trip();
                    trip.setId(resultSet.getLong("id_tour"));
                    trip.setName(resultSet.getString("name"));
                    trip.setSummary(resultSet.getString("summary"));
                    trip.setDescription(resultSet.getString("description"));
                    trip.setDepartureDate(resultSet.getDate("departure_date"));
                    trip.setArrivalDate(resultSet.getDate("arrival_date"));
                    trip.setPrice(resultSet.getInt("price"));
                    trip.setLastMinute(resultSet.getBoolean("hot_tour"));
                    trip.setAttractions(resultSet.getString("attractions"));
                    trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                    trip.setServices(resultSet.getString("services"));
                    trip.setPathImage(resultSet.getString("path_image"));
                    ArrayList<City> cities = new ArrayList<City>();
                    City city = new City();
                    city.setIdCity(resultSet.getLong("id_city"));
                    city.setNameCity(resultSet.getString("destination_city"));
                    Country country = new Country();
                    country.setIdCountry(resultSet.getLong("id_country"));
                    country.setNameCountry(resultSet.getString("destination_country"));
                    city.setCountry(country);
                    cities.add(city);
                    trip.setCities(cities);
                    tripMap.put(trip.getId(),trip);
                }
                else {
                    City city = new City();
                    city.setIdCity(resultSet.getLong("id_city"));
                    city.setNameCity(resultSet.getString("destination_city"));
                    Country country = new Country();
                    country.setIdCountry(resultSet.getLong("id_country"));
                    country.setNameCountry(resultSet.getString("destination_country"));
                    city.setCountry(country);
                    tripMap.get(resultSet.getLong("id_tour")).getCities().add(city);
                }
            }
            trips.addAll(tripMap.values());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * General select trips after now.
     *
     * @param nowDate today's date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Trip> generalSelectTripsAfterNow(Date nowDate, String query) throws DAOException{
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trips;
    }

    /**
     * General find trips by date after now.
     *
     * @param nowDate today's date
     * @param generalDate arrival or departure date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Trip> generalFindTripByDate(Date nowDate, Date generalDate, String query) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ps.setDate(2,generalDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListTrips(resultSet, trips);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return trips;
    }
}
