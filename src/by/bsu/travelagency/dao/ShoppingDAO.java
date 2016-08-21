package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class ShoppingDAO extends AbstractDAO<Long, Shopping> {

    private final static Logger LOG = Logger.getLogger(ShoppingDAO.class);
    private static final String SQL_SELECT_ALL_SHOPPINGS = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings";
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>?";
    private static final String SQL_SELECT_SHOPPING_BY_ID = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE idshopping=?";
    private static final String SQL_SELECT_LAST_SHOPPINGS = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE departure_date>? ORDER BY idshopping DESC LIMIT 6";
    private static final String SQL_SELECT_LAST_SHOPPING_ID = "SELECT idshopping FROM shoppings ORDER BY idshopping DESC LIMIT 1";
    private static final String SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID = "SELECT path_image FROM shoppings WHERE idshopping=?";
    private static final String SQL_INSERT_SHOPPING = "INSERT INTO shoppings(name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_SHOPPING = "UPDATE shoppings SET name=?,summary=?,description=?,departure_date=?,arrival_date=?,price=?,last_minute=?,shops=?,destination_city=?,destination_country=?,transport=?,services=?,path_image=? WHERE idshopping=?";
    private static final String SQL_DELETE_SHOPPING = "DELETE FROM shoppings WHERE idshopping=?";

    public List<Shopping> findAllShoppings() {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(st);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return shoppings;
    }

    public List<Shopping> findAllShoppingsAfterNow(Date nowDate) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return shoppings;
    }

    public Long findLastShoppingId() {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(st);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return id;
    }

    public String findPathImageShoppingById(Long id) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return pathImage;
    }

    public boolean insertShopping(Shopping shopping) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    public boolean updateShopping(Shopping shopping) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    public List<Shopping> selectLastShoppings(Date nowDate) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return shoppings;
    }

    public void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(Connection cn){
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Shopping findEntityById(Long id) {
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
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return shopping;
    }

    @Override
    public boolean delete(Long id) {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_DELETE_SHOPPING);
            ps.setLong(1,id);
            ps.execute();
            flag = true;
        } catch (SQLException e) {
            LOG.error("SQL exception (request or table failed): " + e);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        } finally {
            closeStatement(ps);
            closeConnection(cn);
// код возвращения экземпляра Connection в пул
        }
        return flag;
    }

    @Override
    public boolean delete(Shopping entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Shopping entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Shopping update(Shopping entity) {
        throw new UnsupportedOperationException();
    }
}
