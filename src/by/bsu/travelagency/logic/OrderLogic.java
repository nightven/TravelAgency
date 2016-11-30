package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Order;
import by.bsu.travelagency.entity.TourType;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Михаил on 2/16/2016.
 */
public class OrderLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(OrderLogic.class);

    /** The Constant ORDER_ID_FOR_INSERT. */
    final static Long ORDER_ID_FOR_INSERT = 0L;

    /**
     * Check order.
     *
     * @param userId the user id
     * @param tourId the tour id
     * @param departureDate the departure date
     * @param arrivalDate the arrival date
     * @param price the price
     * @param enterQuantity the enter quantity
     * @param discount the discount
     * @param tourType the tour type
     * @param totalPrice the total price
     * @param userBalance the user balance
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkOrder(Long userId, Long tourId, String departureDate, String arrivalDate, int price, int enterQuantity,
                                     double discount, String tourType, int totalPrice, int userBalance) throws BusinessLogicException {
        boolean flag = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDateFormatted = null;
        try {
            departureDateFormatted = format.parse(departureDate);
        } catch (ParseException e) {
            throw new BusinessLogicException("Failed to parse date (Order).", e);
        }
        if (Validator.validateQuantity(enterQuantity) && Validator.validateOrderDate(departureDateFormatted)){
            Order order = new Order();
            order.setOrderId(ORDER_ID_FOR_INSERT);
            order.setUserId(userId);
            order.setTourId(tourId);
            order.setOrderDate(new java.util.Date());
            LOG.debug("Order date: " + new java.util.Date());
            try {
                order.setDepartureDate(format.parse(departureDate));
                order.setArrivalDate(format.parse(arrivalDate));
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Order).", e);
            }
            order.setPrice(price);
            order.setQuantity(enterQuantity);
            order.setDiscount(discount);
            order.setTourType(TourType.valueOf(tourType.toUpperCase()));
            order.setTotalPrice(totalPrice);

            UserDAO userDAO = new UserDAO();
            int newBalance = userBalance-totalPrice;

            OrderDAO orderDAO = new OrderDAO();

            try {
                if (orderDAO.create(order) && userDAO.updateUserBalance(userId, newBalance)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create order or to update user balance (Order).", e);
            }
        }
            return flag;
        }

    /**
     * Check order delete.
     *
     * @param orderId the order id
     * @param userId the user id
     * @param departureDate the departure date
     * @param totalPrice the total price
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkOrderDelete(Long orderId, Long userId, Date departureDate, int totalPrice) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateOrderDate(departureDate)){
            OrderDAO orderDAO = new OrderDAO();
            UserDAO userDAO = new UserDAO();

            try {
                if (orderDAO.delete(orderId) && userDAO.updateUserBalanceAddition(userId, totalPrice)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to delete order or to update user balance (Order).", e);
            }
        }
        return flag;
    }
    }
