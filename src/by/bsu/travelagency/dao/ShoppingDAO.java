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
    private static final String SQL_SELECT_SHOPPING_BY_ID = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings WHERE idshopping=?";
    private static final String SQL_SELECT_LAST_SHOPPINGS = "SELECT idshopping,name,summary,description,departure_date,arrival_date,price,last_minute,shops,destination_city,destination_country,transport,services,path_image FROM shoppings ORDER BY idshopping DESC LIMIT 6";

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

    public List<Shopping> selectLastShoppings() {
        List<Shopping> shoppings = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            st = cn.createStatement();
            ResultSet resultSet =
                    st.executeQuery(SQL_SELECT_LAST_SHOPPINGS);
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
        throw new UnsupportedOperationException();
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
