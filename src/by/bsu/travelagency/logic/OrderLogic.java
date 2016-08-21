package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.Order;
import by.bsu.travelagency.entity.TourType;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Михаил on 2/16/2016.
 */
public class OrderLogic {
    private final static Logger LOG = Logger.getLogger(OrderLogic.class);

    final static Long ORDER_ID_FOR_INSERT = 0L;

    public static boolean checkOrder(Long userId, Long tourId, String departureDate, String arrivalDate, int price, int enterQuantity,
                                     double discount, String tourType, int totalPrice, int userBalance) {
        boolean flag = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDateFormatted = null;
        try {
            departureDateFormatted = format.parse(departureDate);
        } catch (ParseException e) {
            e.printStackTrace();
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                order.setArrivalDate(format.parse(arrivalDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            order.setPrice(price);
            order.setQuantity(enterQuantity);
            order.setDiscount(discount);
            order.setTourType(TourType.valueOf(tourType.toUpperCase()));
            order.setTotalPrice(totalPrice);

            UserDAO userDAO = new UserDAO();
            int newBalance = userBalance-totalPrice;

            OrderDAO orderDAO = new OrderDAO();

            if (orderDAO.insertOrder(order) && userDAO.updateUserBalance(userId, newBalance)){
                flag = true;
            }
        }
            return flag;
        }

    public static boolean checkOrderDelete(Long orderId, Long userId, Date departureDate, int totalPrice) {
        boolean flag = false;
        if (Validator.validateOrderDate(departureDate)){
            OrderDAO orderDAO = new OrderDAO();
            UserDAO userDAO = new UserDAO();

            if (orderDAO.delete(orderId) && userDAO.updateUserBalanceAddition(userId, totalPrice)){
                flag = true;
            }
        }
        return flag;
    }
    }
