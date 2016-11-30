package by.bsu.travelagency.dao;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Order;
import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.entity.TourType;
import by.bsu.travelagency.pool.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class OrderDAO extends AbstractDAO<Long, Order> {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(OrderDAO.class);

    /** The Constant SQL_INSERT_ORDER. */
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(iduser,idtour,price,quantity,discount,total_price,order_date,departure_date,arrival_date,tour_type) VALUES(?,?,?,?,?,?,?,?,?,?)";
    
    /** The Constant SQL_SELECT_ORDER_VACATION. */
    private static final String SQL_SELECT_ORDER_VACATION = "SELECT orders.idorder,orders.iduser,orders.idtour,orders.price,orders.quantity,orders.discount,orders.total_price,orders.order_date,orders.departure_date,orders.arrival_date,orders.tour_type,vacations.name,vacations.path_image FROM orders INNER JOIN vacations ON orders.idtour=vacations.idvacation  WHERE orders.iduser=? AND orders.tour_type='VACATIONS' ORDER BY orders.idorder DESC;";
    
    /** The Constant SQL_SELECT_ORDER_TRIP. */
    private static final String SQL_SELECT_ORDER_TRIP = "SELECT orders.idorder,orders.iduser,orders.idtour,orders.price,orders.quantity,orders.discount,orders.total_price,orders.order_date,orders.departure_date,orders.arrival_date,orders.tour_type,trips.name,trips.path_image FROM orders INNER JOIN trips ON orders.idtour=trips.idtrip  WHERE orders.iduser=? AND orders.tour_type='TRIPS' ORDER BY orders.idorder DESC;";
    
    /** The Constant SQL_SELECT_ORDER_SHOPPING. */
    private static final String SQL_SELECT_ORDER_SHOPPING = "SELECT orders.idorder,orders.iduser,orders.idtour,orders.price,orders.quantity,orders.discount,orders.total_price,orders.order_date,orders.departure_date,orders.arrival_date,orders.tour_type,shoppings.name,shoppings.path_image FROM orders INNER JOIN shoppings ON orders.idtour=shoppings.idshopping  WHERE orders.iduser=? AND orders.tour_type='SHOPPINGS' ORDER BY orders.idorder DESC;";
    
    /** The Constant SQL_DELETE_ORDER. */
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE idorder=?";
    
    /** The Constant SQL_SELECT_DEPARTURE_DATE_BY_ID. */
    private static final String SQL_SELECT_DEPARTURE_DATE_BY_ID = "SELECT departure_date FROM orders WHERE idorder=?";
    
    /** The Constant SQL_SELECT_TOTAL_PRICE_BY_ID. */
    private static final String SQL_SELECT_TOTAL_PRICE_BY_ID = "SELECT total_price FROM orders WHERE idorder=?";


    /**
     * Find all user orders by user id.
     *
     * @param userId the user id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<OrderTourInfo> findAllUserOrdersByUserId(Long userId) throws DAOException {
        List<OrderTourInfo> orderTourInfos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_ORDER_VACATION);
            ps.setLong(1,userId);
            ResultSet resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);

            ps = cn.prepareStatement(SQL_SELECT_ORDER_TRIP);
            ps.setLong(1,userId);
            resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);

            ps = cn.prepareStatement(SQL_SELECT_ORDER_SHOPPING);
            ps.setLong(1,userId);
            resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return orderTourInfos;
    }

    /**
     * Find departure date by id.
     *
     * @param id the id
     * @return the date
     * @throws DAOException the DAO exception
     */
    public Date findDepartureDateById(Long id) throws DAOException {
        Date departureDate = null;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_DEPARTURE_DATE_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                departureDate = resultSet.getDate("departure_date");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return departureDate;
    }

    /**
     * Find total price by id.
     *
     * @param id the id
     * @return the int
     * @throws DAOException the DAO exception
     */
    public int findTotalPriceById(Long id) throws DAOException {
        int totalPrice = 0;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_SELECT_TOTAL_PRICE_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                totalPrice = resultSet.getInt("total_price");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        } finally {
            closeStatement(ps);
            closeConnection(cn);
        }
        return totalPrice;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(Order order) throws DAOException {
        boolean flag = false;
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = TravelController.connectionPool.getConnection();
            ps = cn.prepareStatement(SQL_INSERT_ORDER);
            ps.setLong(1,order.getUserId());
            ps.setLong(2,order.getTourId());
            ps.setInt(3,order.getPrice());
            ps.setInt(4,order.getQuantity());
            ps.setDouble(5,order.getDiscount());
            ps.setInt(6,order.getTotalPrice());
            ps.setDate(7,new java.sql.Date(order.getOrderDate().getTime()));
            ps.setDate(8,new java.sql.Date(order.getDepartureDate().getTime()));
            ps.setDate(9,new java.sql.Date(order.getArrivalDate().getTime()));
            ps.setString(10,order.getTourType().toString());
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

    /**
     * Creates the order tour infos.
     *
     * @param resultSet the result set
     * @param orderTourInfos the order tour infos
     * @throws SQLException the SQL exception
     */
    public void createOrderTourInfos (ResultSet resultSet, List<OrderTourInfo> orderTourInfos) throws SQLException {
        while (resultSet.next()) {
            OrderTourInfo orderTourInfo = new OrderTourInfo();
            orderTourInfo.setOrderId(resultSet.getLong("idorder"));
            orderTourInfo.setUserId(resultSet.getLong("iduser"));
            orderTourInfo.setTourId(resultSet.getLong("idtour"));
            orderTourInfo.setPrice(resultSet.getInt("price"));
            orderTourInfo.setQuantity(resultSet.getInt("quantity"));
            orderTourInfo.setDiscount(resultSet.getDouble("discount"));
            orderTourInfo.setTotalPrice(resultSet.getInt("total_price"));
            orderTourInfo.setOrderDate(resultSet.getDate("order_date"));
            orderTourInfo.setDepartureDate(resultSet.getDate("departure_date"));
            orderTourInfo.setArrivalDate(resultSet.getDate("arrival_date"));
            orderTourInfo.setTourType(TourType.valueOf(resultSet.getString("tour_type")));
            orderTourInfo.setName(resultSet.getString("name"));
            orderTourInfo.setPathImage(resultSet.getString("path_image"));
            orderTourInfos.add(orderTourInfo);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Order findEntityById(Long id) {
        throw new UnsupportedOperationException();
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
            ps = cn.prepareStatement(SQL_DELETE_ORDER);
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
    public boolean delete(Order entity) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.AbstractDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(Order entity) {
        throw new UnsupportedOperationException();
    }
}
