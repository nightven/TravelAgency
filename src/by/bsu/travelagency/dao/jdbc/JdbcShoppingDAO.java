package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcShoppingDAO implements ShoppingDAO {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(JdbcShoppingDAO.class);
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS. */
    private static final String SQL_SELECT_ALL_SHOPPINGS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE id_type=3";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3";

    /** The Constant SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND tours.name LIKE ? AND id_type=3";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND departure_date=? AND id_type=3";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND arrival_date=? AND id_type=3";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND price=? AND id_type=3";
    
    /** The Constant SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW. */
    private static final String SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?) AND id_type=3";
    
    /** The Constant SQL_SELECT_SHOPPING_BY_ID. */
    private static final String SQL_SELECT_SHOPPING_BY_ID = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE id_tour=? AND id_type=3";

    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY tours.name";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY departure_date";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY arrival_date";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY price";
    
    /** The Constant SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT. */
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY transport";

    /** The Constant SQL_SELECT_LAST_SHOPPINGS. */
    private static final String SQL_SELECT_LAST_SHOPPINGS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY id_tour DESC LIMIT 6";
    
    /** The Constant SQL_SELECT_LAST_SHOPPING_ID. */
    private static final String SQL_SELECT_LAST_SHOPPING_ID = "SELECT id_tour FROM tours WHERE id_type=3 ORDER BY id_tour DESC LIMIT 1";
    
    /** The Constant SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID. */
    private static final String SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID = "SELECT path_image FROM tours WHERE id_tour=? AND id_type=3";
    
    /** The Constant SQL_INSERT_SHOPPING. */
    private static final String SQL_INSERT_SHOPPING = "INSERT INTO tours(name,summary,description,departure_date,arrival_date,price,hot_tour,shops,id_transport,services,path_image,id_type) VALUES(?,?,?,?,?,?,?,?,(SELECT id_transport FROM transport_type WHERE transport_type=?),?,?,3)";

    /** The Constant SQL_INSERT_SHOPPING_CITY. */
    private static final String SQL_INSERT_SHOPPING_CITY = "INSERT INTO tours_cities(id_tour,id_city,`order`) VALUES(?,?,1)";

    /** The Constant SQL_UPDATE_SHOPPING_CITY. */
    private static final String SQL_UPDATE_SHOPPING_CITY = "UPDATE tours_cities SET id_city=? WHERE id_tour=?";
    
    /** The Constant SQL_UPDATE_SHOPPING. */
    private static final String SQL_UPDATE_SHOPPING = "UPDATE tours SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,hot_tour=?,shops=?,id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?),services=?,path_image=? WHERE id_tour=? AND id_type=3";
    
    /** The Constant SQL_DELETE_SHOPPING. */
    private static final String SQL_DELETE_SHOPPING = "DELETE FROM tours WHERE id_tour=? AND id_type=3";

    /** The Constant SQL_DELETE_SHOPPING_CITY. */
    private static final String SQL_DELETE_SHOPPING_CITY = "DELETE FROM tours_cities WHERE id_tour=?";

    /**
     * Instantiates a new JdbcShoppingDAO.
     */
    private JdbcShoppingDAO() {
    }

    /** Nested class JdbcShoppingDAOHolder. */
    private static class JdbcShoppingDAOHolder {
        private static final JdbcShoppingDAO HOLDER_INSTANCE = new JdbcShoppingDAO();
    }


    /**
     * Gets the instance.
     *
     * @return the JdbcShoppingDAOHolder instance
     */
    public static JdbcShoppingDAO getInstance() {
        return JdbcShoppingDAOHolder.HOLDER_INSTANCE;
    }

    /**
     * Find all shoppings.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findAllShoppings() throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_ALL_SHOPPINGS);
            generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        return generalSelectShoppingsAfterNow(nowDate, SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW);
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
        HashMap<String, String> map = new HashMap<>();
        map.put("name", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME);
        map.put("departure_date", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
        map.put("arrival_date", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
        map.put("price", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE);
        map.put("transport", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT);
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(map.get(criterion))) {
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
           generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        return generalFindShoppingByDate(nowDate, departureDate, SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW);
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
        return generalFindShoppingByDate(nowDate, arrivalDate, SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW);
    }

    /**
     * Find shoppings by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setDouble(2,price);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); Statement st = cn.createStatement()) {
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_SHOPPING_ID);
            while (resultSet.next()) {
                id = resultSet.getLong("id_tour");
                LOG.debug("Last shopping id: " + resultSet.getLong("id_tour"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID)) {
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
    public boolean create(Shopping shopping) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_SHOPPING); PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_SHOPPING_CITY); Statement st = cn.createStatement()) {
            ps.setString(1,shopping.getName());
            ps.setString(2,shopping.getSummary());
            ps.setString(3,shopping.getDescription());
            ps.setDate(4,new java.sql.Date(shopping.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(shopping.getArrivalDate().getTime()));
            ps.setDouble(6,shopping.getPrice());
            ps.setInt(7,(shopping.getLastMinute()) ? 1 : 0);
            ps.setString(8,shopping.getShops());
            ps.setString(9,shopping.getTransport().toString());
            ps.setString(10,shopping.getServices());
            ps.setString(11,shopping.getPathImage());
            ps.executeUpdate();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_SHOPPING_ID);
            Long shoppingId = null;
            while (resultSet.next()){
                shoppingId = resultSet.getLong("id_tour");
            }
            ps2.setLong(1,shoppingId);
            ps2.setLong(2,shopping.getCities().get(0).getIdCity());
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
    public boolean update(Shopping shopping) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_SHOPPING); PreparedStatement ps2 = cn.prepareStatement(SQL_UPDATE_SHOPPING_CITY)) {
            ps.setString(1,shopping.getName());
            ps.setString(2,shopping.getSummary());
            ps.setString(3,shopping.getDescription());
            ps.setDate(4,new java.sql.Date(shopping.getDepartureDate().getTime()));
            ps.setDate(5,new java.sql.Date(shopping.getArrivalDate().getTime()));
            ps.setDouble(6,shopping.getPrice());
            ps.setInt(7,(shopping.getLastMinute()) ? 1 : 0);
            ps.setString(8,shopping.getShops());
            ps.setString(9,shopping.getTransport().toString());
            ps.setString(10,shopping.getServices());
            ps.setString(11,shopping.getPathImage());
            ps.setLong(12,shopping.getId());
            ps.executeUpdate();
            ps2.setLong(1,shopping.getCities().get(0).getIdCity());
            ps2.setLong(2,shopping.getId());
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
     * Select last shoppings.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Shopping> selectLastShoppings(Date nowDate) throws DAOException {
        return generalSelectShoppingsAfterNow(nowDate, SQL_SELECT_LAST_SHOPPINGS);
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Shopping findEntityById(Long id) throws DAOException {
        Shopping shopping = new Shopping();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPING_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                shopping.setId(resultSet.getLong("id_tour"));
                shopping.setName(resultSet.getString("name"));
                shopping.setSummary(resultSet.getString("summary"));
                shopping.setDescription(resultSet.getString("description"));
                shopping.setDepartureDate(resultSet.getDate("departure_date"));
                shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                shopping.setPrice(resultSet.getDouble("price"));
                shopping.setLastMinute(resultSet.getBoolean("hot_tour"));
                shopping.setShops(resultSet.getString("shops"));
                ArrayList<City> cities = new ArrayList<City>();
                City city = new City();
                city.setIdCity(resultSet.getLong("id_city"));
                city.setNameCity(resultSet.getString("destination_city"));
                Country country = new Country();
                country.setIdCountry(resultSet.getLong("id_country"));
                country.setNameCountry(resultSet.getString("destination_country"));
                city.setCountry(country);
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                shopping.setServices(resultSet.getString("services"));
                shopping.setPathImage(resultSet.getString("path_image"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return shopping;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_SHOPPING); PreparedStatement ps2 = cn.prepareStatement(SQL_DELETE_SHOPPING_CITY)) {
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
    public boolean delete(Shopping entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * General get of shopping list.
     *
     * @param resultSet the ResultSet
     * @param shoppings the list of shoppings
     * @throws DAOException the DAO exception
     */
    private void generalGetListShoppings(ResultSet resultSet, List<Shopping> shoppings) throws DAOException {
        try {
            Map<Long, Shopping> shoppingMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (!shoppingMap.containsKey(resultSet.getLong("id_tour"))) {
                    Shopping shopping = new Shopping();
                    shopping.setId(resultSet.getLong("id_tour"));
                    shopping.setName(resultSet.getString("name"));
                    shopping.setSummary(resultSet.getString("summary"));
                    shopping.setDescription(resultSet.getString("description"));
                    shopping.setDepartureDate(resultSet.getDate("departure_date"));
                    shopping.setArrivalDate(resultSet.getDate("arrival_date"));
                    shopping.setPrice(resultSet.getDouble("price"));
                    shopping.setLastMinute(resultSet.getBoolean("hot_tour"));
                    shopping.setShops(resultSet.getString("shops"));
                    ArrayList<City> cities = new ArrayList<City>();
                    City city = new City();
                    city.setIdCity(resultSet.getLong("id_city"));
                    city.setNameCity(resultSet.getString("destination_city"));
                    Country country = new Country();
                    country.setIdCountry(resultSet.getLong("id_country"));
                    country.setNameCountry(resultSet.getString("destination_country"));
                    city.setCountry(country);
                    cities.add(city);
                    shopping.setCities(cities);
                    shopping.setTransport(Transport.valueOf(resultSet.getString("transport")));
                    shopping.setServices(resultSet.getString("services"));
                    shopping.setPathImage(resultSet.getString("path_image"));
                    shoppings.add(shopping);
                }
            }
            shoppings.addAll(shoppingMap.values());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    /**
     * General select shoppings after now.
     *
     * @param nowDate today's date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Shopping> generalSelectShoppingsAfterNow(Date nowDate, String query) throws DAOException{
        List<Shopping> shoppings = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return shoppings;
    }

    /**
     * General find shoppings by date after now.
     *
     * @param nowDate today's date
     * @param generalDate arrival or departure date
     * @param query the query to use
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Shopping> generalFindShoppingByDate(Date nowDate, Date generalDate, String query) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ps.setDate(2,generalDate);
            ResultSet resultSet =
                    ps.executeQuery();
            generalGetListShoppings(resultSet, shoppings);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return shoppings;
    }
}
