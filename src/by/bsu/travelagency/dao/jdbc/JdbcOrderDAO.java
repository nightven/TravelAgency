package by.bsu.travelagency.dao.jdbc;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Order;
import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.entity.TourType;
import by.bsu.travelagency.pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 2/24/2016.
 */
public class JdbcOrderDAO implements OrderDAO {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(JdbcOrderDAO.class);

    /** The Constant SQL_INSERT_ORDER. */
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(id_user,id_tour,quantity,total_price,order_date,departure_date,arrival_date) VALUES(?,?,?,?,?,?,?)";

    /** The Constant SQL_SELECT_ALL_ORDERS_BY_USER_ID. */
    private static final String SQL_SELECT_ALL_ORDERS_BY_USER_ID = "SELECT id_order,orders.id_user,orders.id_tour,quantity,total_price,order_date,orders.departure_date,orders.arrival_date,tour_type.tour_type as tour_type,tours.name,tours.path_image FROM orders JOIN tours USING (id_tour) JOIN tour_type USING (id_type)  WHERE orders.id_user=? AND tours.id_type IN (1,2,3) ORDER BY orders.id_order DESC";

    /** The Constant SQL_SELECT_ALL_ORDERS_BY_USER_ID_AFTER_NOW. */
    private static final String SQL_SELECT_ALL_ORDERS_BY_USER_ID_AFTER_NOW = "SELECT id_order,orders.id_user,orders.id_tour,quantity,total_price,order_date,orders.departure_date,orders.arrival_date,tour_type.tour_type as tour_type,tours.name,tours.path_image FROM orders JOIN tours USING (id_tour) JOIN tour_type USING (id_type)  WHERE orders.id_user=? AND orders.departure_date>? AND tours.id_type IN (1,2,3) ORDER BY orders.id_order DESC";

    /** The Constant SQL_SELECT_ALL_ORDERS_BY_USER_ID_BEFORE_NOW. */
    private static final String SQL_SELECT_ALL_ORDERS_BY_USER_ID_BEFORE_NOW = "SELECT id_order,orders.id_user,orders.id_tour,quantity,total_price,order_date,orders.departure_date,orders.arrival_date,tour_type.tour_type as tour_type,tours.name,tours.path_image FROM orders JOIN tours USING (id_tour) JOIN tour_type USING (id_type)  WHERE orders.id_user=? AND orders.departure_date<=? AND tours.id_type IN (1,2,3) ORDER BY orders.id_order DESC";

    /** The Constant SQL_DELETE_ORDER. */
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id_order=?";
    
    /** The Constant SQL_SELECT_DEPARTURE_DATE_BY_ID. */
    private static final String SQL_SELECT_DEPARTURE_DATE_BY_ID = "SELECT departure_date FROM orders WHERE id_order=?";
    
    /** The Constant SQL_SELECT_TOTAL_PRICE_BY_ID. */
    private static final String SQL_SELECT_TOTAL_PRICE_BY_ID = "SELECT total_price FROM orders WHERE id_order=?";

    /** The Constant SQL_SELECT_USER_ID_BY_ORDER_ID. */
    private static final String SQL_SELECT_USER_ID_BY_ORDER_ID = "SELECT id_user FROM orders WHERE id_order=?";


    /**
     * Find all user orders by user id.
     *
     * @param userId the user id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<OrderTourInfo> findAllUserOrdersByUserId(Long userId) throws DAOException {
        List<OrderTourInfo> orderTourInfos = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_USER_ID)) {
            ps.setLong(1,userId);
            ResultSet resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return orderTourInfos;
    }

    /**
     * Find all user orders by user id after now.
     *
     * @param userId the user id
     * @param nowDate the today's date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<OrderTourInfo> findAllUserOrdersByUserIdAfterNow(Long userId, Date nowDate) throws DAOException {
        return generalFindAllUserOrdersByDate(userId, nowDate, SQL_SELECT_ALL_ORDERS_BY_USER_ID_AFTER_NOW);
    }

    /**
     * Find all user orders by user id before now.
     *
     * @param userId the user id
     * @param nowDate the today's date
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<OrderTourInfo> findAllUserOrdersByUserIdBeforeNow(Long userId, Date nowDate) throws DAOException {
        return generalFindAllUserOrdersByDate(userId, nowDate, SQL_SELECT_ALL_ORDERS_BY_USER_ID_BEFORE_NOW);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_DEPARTURE_DATE_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                departureDate = resultSet.getDate("departure_date");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
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
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TOTAL_PRICE_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                totalPrice = resultSet.getInt("total_price");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return totalPrice;
    }

    /**
     * Find userId by OrderId.
     *
     * @param id the id
     * @return the int
     * @throws DAOException the DAO exception
     */
    public long findUserIdByOrderId(Long id) throws DAOException {
        long userId = 0;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_ID_BY_ORDER_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("id_user");
                LOG.debug("userId from DAO.findUserIdByOrderId = " + userId);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return userId;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean create(Order order) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_INSERT_ORDER)) {
            ps.setLong(1,order.getUserId());
            ps.setLong(2,order.getTourId());
            ps.setInt(3,order.getQuantity());
            ps.setInt(4,order.getTotalPrice());
            ps.setDate(5,new java.sql.Date(order.getOrderDate().getTime()));
            ps.setDate(6,new java.sql.Date(order.getDepartureDate().getTime()));
            ps.setDate(7,new java.sql.Date(order.getArrivalDate().getTime()));
            if (ps.executeUpdate() != 0){
                flag = true;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    public Order findEntityById(Long id) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_DELETE_ORDER)) {
            ps.setLong(1,id);
            if (ps.executeUpdate() != 0){
                flag = true;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        LOG.debug("deleteOrder = " + flag);
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean delete(Order entity) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    public boolean update(Order entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates the order tour infos.
     *
     * @param resultSet the result set
     * @param orderTourInfos the order tour infos
     * @throws SQLException the SQL exception
     */
    private void createOrderTourInfos (ResultSet resultSet, List<OrderTourInfo> orderTourInfos) throws SQLException {
        while (resultSet.next()) {
            OrderTourInfo orderTourInfo = new OrderTourInfo();
            orderTourInfo.setOrderId(resultSet.getLong("id_order"));
            orderTourInfo.setUserId(resultSet.getLong("id_user"));
            orderTourInfo.setTourId(resultSet.getLong("id_tour"));
            orderTourInfo.setQuantity(resultSet.getInt("quantity"));
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

    /**
     * General find all user orders by user id and date.
     *
     * @param userId the user id
     * @param nowDate the today's date
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<OrderTourInfo> generalFindAllUserOrdersByDate(Long userId, Date nowDate, String query) throws DAOException {
        List<OrderTourInfo> orderTourInfos = new ArrayList<>();
        try (Connection cn = TravelController.connectionPool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setLong(1,userId);
            ps.setDate(2,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return orderTourInfos;
    }
}
