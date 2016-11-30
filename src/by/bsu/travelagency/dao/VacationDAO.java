package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class VacationDAO extends AbstractDAO<Long, Vacation> {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(VacationDAO.class);
    
    /** The Constant SQL_SELECT_ALL_VACATIONS. */
    private static final String SQL_SELECT_ALL_VACATIONS = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>?";

    /** The Constant SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? AND name LIKE ?";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? AND departure_date=?";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? AND arrival_date=?";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? AND price=?";
    
    /** The Constant SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW. */
    private static final String SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? AND transport=?";
    
    /** The Constant SQL_SELECT_VACATION_BY_ID. */
    private static final String SQL_SELECT_VACATION_BY_ID = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE idvacation=?";

    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? ORDER BY name";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? ORDER BY departure_date";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? ORDER BY arrival_date";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? ORDER BY price";
    
    /** The Constant SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT. */
    private static final String SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? ORDER BY transport";


    /** The Constant SQL_SELECT_LAST_VACATIONS. */
    private static final String SQL_SELECT_LAST_VACATIONS = "SELECT idvacation,name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image FROM vacations WHERE departure_date>? ORDER BY idvacation DESC LIMIT 6";
    
    /** The Constant SQL_SELECT_LAST_VACATION_ID. */
    private static final String SQL_SELECT_LAST_VACATION_ID = "SELECT idvacation FROM vacations ORDER BY idvacation DESC LIMIT 1";
    
    /** The Constant SQL_SELECT_PATH_IMAGE_VACATION_BY_ID. */
    private static final String SQL_SELECT_PATH_IMAGE_VACATION_BY_ID = "SELECT path_image FROM vacations WHERE idvacation=?";
    
    /** The Constant SQL_INSERT_VACATION. */
    private static final String SQL_INSERT_VACATION = "INSERT INTO vacations(name,summary,description,departure_date,arrival_date,price,last_minute,hotel,destination_city,destination_country,transport,services,path_image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    /** The Constant SQL_UPDATE_VACATION. */
    private static final String SQL_UPDATE_VACATION = "UPDATE vacations SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,last_minute=?,hotel=?,destination_city=?,destination_country=?,transport=?,services=?,path_image=? WHERE idvacation=?";
    
    /** The Constant SQL_DELETE_VACATION. */
    private static final String SQL_DELETE_VACATION = "DELETE FROM vacations WHERE idvacation=?";

    /**
     * Find all vacations.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findAllVacations() throws DAOException {
        List<Vacation> vacations = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_VACATIONS);
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(st);
            closeConnection(cn);
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
        List<Vacation> vacations = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_ALL_VACATIONS_AFTER_NOW);
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return vacations;
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            switch(criterion){
                case "name":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_NAME);
                    break;
                case "departure_date":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
                    break;
                case "arrival_date":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
                    break;
                case "price":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_PRICE);
                    break;
                case "transport":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_VACATIONS_AFTER_NOW_SORT_BY_TRANSPORT);
                    break;
            }
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_NAME_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        List<Vacation> vacations = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_DEPARTURE_DATE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setDate(2,departureDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return vacations;
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
        List<Vacation> vacations = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_ARRIVAL_DATE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setDate(2,arrivalDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return vacations;
    }

    /**
     * Find vacations by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> findVacationsByPriceAfterNow(Date nowDate, int price) throws DAOException {
        List<Vacation> vacations = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_PRICE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setInt(2,price);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_VACATIONS_BY_TRANSPORT_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
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
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_VACATION_ID);
            while (resultSet.next()) {
                id = resultSet.getLong("idvacation");
                LOG.debug("Last vacation id: " + resultSet.getLong("idvacation"));
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
     * Find path image vacation by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findPathImageVacationById(Long id) throws DAOException {
        String pathImage = null;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_VACATION_BY_ID);
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
    public boolean create(Vacation vacation) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_INSERT_VACATION);
            ps.setString(1,vacation.getName());
            ps.setString(2,vacation.getSummary());
            ps.setString(3,vacation.getDescription());
            ps.setDate(4,new java.sql.Date(vacation.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(vacation.getArrivalDate().getTime()));
            ps.setLong(6,vacation.getPrice());
            ps.setInt(7,(vacation.getLastMinute()) ? 1 : 0);
            ps.setString(8,vacation.getHotel());
            ps.setString(9,vacation.getDestinationCity());
            ps.setString(10,vacation.getDestinationCountry());
            ps.setString(11,vacation.getTransport().toString());
            ps.setString(12,vacation.getServices());
            ps.setString(13,vacation.getPathImage());
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
    public boolean update(Vacation vacation) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_VACATION);
            ps.setString(1,vacation.getName());
            ps.setString(2,vacation.getSummary());
            ps.setString(3,vacation.getDescription());
            ps.setDate(4,new java.sql.Date(vacation.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(vacation.getArrivalDate().getTime()));
            ps.setLong(6,vacation.getPrice());
            ps.setInt(7,(vacation.getLastMinute()) ? 1 : 0);
            ps.setString(8,vacation.getHotel());
            ps.setString(9,vacation.getDestinationCity());
            ps.setString(10,vacation.getDestinationCountry());
            ps.setString(11,vacation.getTransport().toString());
            ps.setString(12,vacation.getServices());
            ps.setString(13,vacation.getPathImage());
            ps.setLong(14,vacation.getId());
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
     * Select last vacations.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Vacation> selectLastVacations(Date nowDate) throws DAOException {
        List<Vacation> vacations = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_LAST_VACATIONS);
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
                vacations.add(vacation);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return vacations;
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
    public Vacation findEntityById(Long id) throws DAOException {
        Vacation vacation = new Vacation();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_VACATION_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                vacation.setId(resultSet.getLong("idvacation"));
                vacation.setName(resultSet.getString("name"));
                vacation.setSummary(resultSet.getString("summary"));
                vacation.setDescription(resultSet.getString("description"));
                vacation.setDepartureDate(resultSet.getDate("departure_date"));
                vacation.setArrivalDate(resultSet.getDate("arrival_date"));
                vacation.setPrice(resultSet.getInt("price"));
                vacation.setLastMinute(resultSet.getBoolean("last_minute"));
                vacation.setHotel(resultSet.getString("hotel"));
                vacation.setDestinationCity(resultSet.getString("destination_city"));
                vacation.setDestinationCountry(resultSet.getString("destination_country"));
                vacation.setTransport(Transport.valueOf(resultSet.getString("transport")));
                vacation.setServices(resultSet.getString("services"));
                vacation.setPathImage(resultSet.getString("path_image"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return vacation;
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
            ps = cn.prepareStatement(SQL_DELETE_VACATION);
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
    public boolean delete(Vacation entity) {
        throw new UnsupportedOperationException();
    }
}
