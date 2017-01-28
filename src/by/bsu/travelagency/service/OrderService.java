package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.service.exception.ServiceException;

import java.sql.Date;
import java.util.List;

/**
 * The Interface OrderService.
 */
public interface OrderService {

    /**
     * Find all user orders by user id after now.
     *
     * @param userId the user id
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<OrderTourInfo> findAllUserOrdersByUserIdAfterNow(Long userId, Date nowDate) throws ServiceException;

    /**
     * Check order.
     *
     * @param userId the user id
     * @param tourId the tour id
     * @param departureDate the departure date
     * @param arrivalDate the arrival date
     * @param enterQuantity the enter quantity
     * @param tourType the tour type
     * @param totalPrice the total price
     * @param userBalance the user balance
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkOrder(Long userId, Long tourId, String departureDate, String arrivalDate, int enterQuantity, String tourType, double totalPrice, double userBalance) throws ServiceException;

    /**
     * Check order delete.
     *
     * @param orderId the order id
     * @param userId the user id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkOrderDelete(Long orderId, Long userId) throws ServiceException;

    /**
     * Find all user orders by user id before now.
     *
     * @param userId the user id
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<OrderTourInfo> findAllUserOrdersByUserIdBeforeNow(Long userId, Date nowDate) throws ServiceException;
}
