package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class ShoppingDAO extends AbstractDAO<Long, Shopping> {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ShoppingDAO.class);
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS. */
    private static final String SQL_SELECT_ALL_SHOPPINGS = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>?";

    /** The Constant SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? AND name LIKE ?";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? AND departure_date=?";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? AND arrival_date=?";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? AND price=?";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? AND transport=?";
    
    /** The Constant SQL_SELECT_SHOPPING_BY_ID. */
    private static final String SQL_SELECT_SHOPPING_BY_ID = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE idshopping=?";

    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY name";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY departure_date";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY arrival_date";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY price";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY transport";

    /** The Constant SQL_SELECT_LAST_SHOPPINGS. */
    private static final String SQL_SELECT_LAST_SHOPPINGS = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY idshopping DESC LIMIT 6";
    
    /** The Constant SQL_SELECT_LAST_SHOPPING_ID. */
    private static final String SQL_SELECT_LAST_SHOPPING_ID = "SELECT idshopping FROM shoppings ORDER BY idshopping DESC LIMIT 1";
    
    /** The Constant SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID. */
    private static final String SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID = "SELECT path_image FROM shoppings WHERE idshopping=?";
    
    /** The Constant SQL_INSERT_SHOPPING. */
    private static final String SQL_INSERT_SHOPPING = "INSERT INTO shoppings(name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    /** The Constant SQL_UPDATE_SHOPPING. */
    private static final String SQL_UPDATE_SHOPPING = "UPDATE shoppings SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,last_minute=?,shops=?,destination_city=?,destination_country=?,transport=?,services=?,path_image=? WHERE idshopping=?";
    
    /** The Constant SQL_DELETE_SHOPPING. */
    private static final String SQL_DELETE_SHOPPING = "DELETE FROM shoppings WHERE idshopping=?";

    /**
     * Find all shoppings.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findAllShoppings() throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_SHOPPINGS);
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(st);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find all shoppings after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW);
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find all sort shoppings after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            switch(criterion){
                case "name":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME);
                    break;
                case "departure_date":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
                    break;
                case "arrival_date":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
                    break;
                case "price":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE);
                    break;
                case "transport":
                    ps = cn.prepareStatement(SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT);
                    break;
            }
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
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
            Collections.reverse(shoppings);
        }
        return shoppings;
    }

    /**
     * Find shoppings by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find shoppings by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setDate(2,departureDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find shoppings by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setDate(2,arrivalDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find shoppings by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, int price) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setInt(2,price);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find shoppings by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW);
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
    }

    /**
     * Find last shopping id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    public Long findLastShoppingId() throws DAOException {
        Long id = 0L;
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_SHOPPING_ID);
            while (resultSet.next()) {
                id = resultSet.getLong("idshopping");
                LOG.debug("Last shopping id: " + resultSet.getLong("idshopping"));
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
     * Find path image shopping by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findPathImageShoppingById(Long id) throws DAOException {
        String pathImage = null;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID);
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
    public boolean create(Shopping shopping) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_INSERT_SHOPPING);
            ps.setString(1,shopping.getName());
            ps.setString(2,shopping.getSummary());
            ps.setString(3,shopping.getDescription());
            ps.setDate(4,new java.sql.Date(shopping.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(shopping.getArrivalDate().getTime()));
            ps.setLong(6,shopping.getPrice());
            ps.setInt(7,(shopping.getLastMinute()) ? 1 : 0);
            ps.setString(8,shopping.getShops());
            ps.setString(9,shopping.getDestinationCity());
            ps.setString(10,shopping.getDestinationCountry());
            ps.setString(11,shopping.getTransport().toString());
            ps.setString(12,shopping.getServices());
            ps.setString(13,shopping.getPathImage());
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
    public boolean update(Shopping shopping) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_UPDATE_SHOPPING);
            ps.setString(1,shopping.getName());
            ps.setString(2,shopping.getSummary());
            ps.setString(3,shopping.getDescription());
            ps.setDate(4,new java.sql.Date(shopping.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(shopping.getArrivalDate().getTime()));
            ps.setLong(6,shopping.getPrice());
            ps.setInt(7,(shopping.getLastMinute()) ? 1 : 0);
            ps.setString(8,shopping.getShops());
            ps.setString(9,shopping.getDestinationCity());
            ps.setString(10,shopping.getDestinationCountry());
            ps.setString(11,shopping.getTransport().toString());
            ps.setString(12,shopping.getServices());
            ps.setString(13,shopping.getPathImage());
            ps.setLong(14,shopping.getId());
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
     * Select last shoppings.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> selectLastShoppings(Date nowDate) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_LAST_SHOPPINGS);
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            while (resultSet.next()) {
                Shopping shopping = new Shopping();
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
                shoppings.add(shopping);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shoppings;
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
    public Shopping findEntityById(Long id) throws DAOException {
        Shopping shopping = new Shopping();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_SHOPPING_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                shopping.setId(resultSet.getLong("idshopping"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getInt("price"));
                shopping.setLastMinute(resultSet.getBoolean("last_minute"));
                shopping.setShops(resultSet.getString("shops"));
                shopping.setDestinationCity(resultSet.getString("destination_city"));
                shopping.setDestinationCountry(resultSet.getString("destination_country"));
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return shopping;
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
            ps = cn.prepareStatement(SQL_DELETE_SHOPPING);
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
    public boolean delete(Shopping entity) {
        throw new UnsupportedOperationException();
    }
}
