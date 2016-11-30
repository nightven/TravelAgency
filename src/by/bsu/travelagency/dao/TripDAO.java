package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class TripDAO extends AbstractDAO<Long, Trip> {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(TripDAO.class);
    
    /** The Constant SQL_SELECT_ALL_TRIPS. */
    private static final String SQL_SELECT_ALL_TRIPS = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>?";

    /** The Constant SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? AND name LIKE ?";
    
    /** The Constant SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? AND departure_date=?";
    
    /** The Constant SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? AND arrival_date=?";
    
    /** The Constant SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? AND price=?";
    
    /** The Constant SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW. */
    private static final String SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? AND transport=?";
    
    /** The Constant SQL_SELECT_TRIP_BY_ID. */
    private static final String SQL_SELECT_TRIP_BY_ID = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE idtrip=?";

    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? ORDER BY name";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? ORDER BY departure_date";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? ORDER BY arrival_date";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? ORDER BY price";
    
    /** The Constant SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT. */
    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? ORDER BY transport";

    /** The Constant SQL_SELECT_LAST_TRIPS. */
    private static final String SQL_SELECT_LAST_TRIPS = "SELECT idtrip,name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image FROM trips WHERE departure_date>? ORDER BY idtrip DESC LIMIT 6";
    
    /** The Constant SQL_SELECT_LAST_TRIP_ID. */
    private static final String SQL_SELECT_LAST_TRIP_ID = "SELECT idtrip FROM trips ORDER BY idtrip DESC LIMIT 1";
    
    /** The Constant SQL_SELECT_PATH_IMAGE_TRIP_BY_ID. */
    private static final String SQL_SELECT_PATH_IMAGE_TRIP_BY_ID = "SELECT path_image FROM trips WHERE idtrip=?";
    
    /** The Constant SQL_INSERT_TRIP. */
    private static final String SQL_INSERT_TRIP = "INSERT INTO trips(name,summary,description,departure_date,arrival_date,price,last_minute,cities,attractions,transport,services,path_image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    
    /** The Constant SQL_UPDATE_TRIP. */
    private static final String SQL_UPDATE_TRIP = "UPDATE trips SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,last_minute=?,cities=?,attractions=?,transport=?,services=?,path_image=? WHERE idtrip=?";
    
    /** The Constant SQL_DELETE_TRIP. */
    private static final String SQL_DELETE_TRIP = "DELETE FROM trips WHERE idtrip=?";

    /**
     * Find all trips.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Trip> findAllTrips() throws DAOException {
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_TRIPS);
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(st);
            closeConnection(cn);
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
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_ALL_TRIPS_AFTER_NOW);
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return trips;
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            switch(criterion){
                case "name":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME);
                    break;
                case "departure_date":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
                    break;
                case "arrival_date":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
                    break;
                case "price":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE);
                    break;
                case "transport":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT);
                    break;
            }
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setDate(2,departureDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return trips;
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
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setDate(2,arrivalDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return trips;
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setInt(2,price);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_TRIP_ID);
            while (resultSet.next()) {
                id = resultSet.getLong("idtrip");
                LOG.debug("Last trip id: " + resultSet.getLong("idtrip"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(st);
            closeConnection(cn);
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_TRIP_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                pathImage = resultSet.getString("path_image");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return pathImage;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(Trip trip) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_INSERT_TRIP);
            ps.setString(1,trip.getName());
            ps.setString(2,trip.getSummary());
            ps.setString(3,trip.getDescription());
            ps.setDate(4,new java.sql.Date(trip.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(trip.getArrivalDate().getTime()));
            ps.setLong(6,trip.getPrice());
            ps.setInt(7,(trip.getLastMinute()) ? 1 : 0);
            ps.setString(8,trip.getCities());
            ps.setString(9,trip.getAttractions());
            ps.setString(10,trip.getTransport().toString());
            ps.setString(11,trip.getServices());
            ps.setString(12,trip.getPathImage());
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(Trip trip) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_TRIP);
            ps.setString(1,trip.getName());
            ps.setString(2,trip.getSummary());
            ps.setString(3,trip.getDescription());
            ps.setDate(4,new java.sql.Date(trip.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(trip.getArrivalDate().getTime()));
            ps.setLong(6,trip.getPrice());
            ps.setInt(7,(trip.getLastMinute()) ? 1 : 0);
            ps.setString(8,trip.getCities());
            ps.setString(9,trip.getAttractions());
            ps.setString(10,trip.getTransport().toString());
            ps.setString(11,trip.getServices());
            ps.setString(12,trip.getPathImage());
            ps.setLong(13,trip.getId());
            ps.executeUpdate();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        List<Trip> trips = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_LAST_TRIPS);
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
                trips.add(trip);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return trips;
    }

    /**
     * Close statement.
     *
     * @param st the st
     * @throws DAOException the DAO exception
     */
    public void closeStatement(Statement st) throws DAOException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DAOException("Failed to close statement.", e);
            }
        }
    }

    /**
     * Close connection.
     *
     * @param cn the cn
     * @throws DAOException the DAO exception
     */
    public void closeConnection(Connection cn) throws DAOException {
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                throw new DAOException("Failed to close statement.", e);
            }
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Trip findEntityById(Long id) throws DAOException {
        Trip trip = new Trip();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TRIP_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                trip.setId(resultSet.getLong("idtrip"));
                trip.setName(resultSet.getString("name"));
                trip.setSummary(resultSet.getString("summary"));
                trip.setDescription(resultSet.getString("description"));
                trip.setDepartureDate(resultSet.getDate("departure_date"));
                trip.setArrivalDate(resultSet.getDate("arrival_date"));
                trip.setPrice(resultSet.getInt("price"));
                trip.setLastMinute(resultSet.getBoolean("last_minute"));
                trip.setCities(resultSet.getString("cities"));
                trip.setAttractions(resultSet.getString("attractions"));
                trip.setTransport(Transport.valueOf(resultSet.getString("transport")));
                trip.setServices(resultSet.getString("services"));
                trip.setPathImage(resultSet.getString("path_image"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return trip;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_DELETE_TRIP);
            ps.setLong(1,id);
            ps.execute();
            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean delete(Trip entity) {
        throw new UnsupportedOperationException();
    }
}
