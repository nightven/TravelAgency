package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.sql.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface ShoppingService.
 */
public interface ShoppingService {

    /**
     * Check create shopping.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterShops the enter shops
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @param img the img
     * @param savePath the save path
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkCreateShopping(String enterName, String enterSummary, String enterDepartureDate,
                                String enterArrivalDate, String enterDestinationCityId,
                                String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;

    /**
     * Find all cities.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<City> findAllCities() throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * Check edit shopping.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterShops the enter shops
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @param img the img
     * @param savePath the save path
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;

    /**
     * Check edit shopping.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterShops the enter shops
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription) throws ServiceException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the shopping
     * @throws ServiceException the service exception
     */
    Shopping findEntityById(Long id) throws ServiceException;

    /**
     * Find user by id.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    User findUserById(Long id) throws ServiceException;

    /**
     * Find money by user id.
     *
     * @param id the id
     * @return the double
     * @throws ServiceException the service exception
     */
    double findMoneyByUserId(Long id) throws ServiceException;

    /**
     * Find all shoppings after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws ServiceException;

    /**
     * Find all sort shoppings after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException;

    /**
     * Select last shoppings.
     *
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> selectLastShoppings(Date nowDate) throws ServiceException;

    /**
     * Find shoppings by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws ServiceException;

    /**
     * Find shoppings by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException;

    /**
     * Find shoppings by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException;

    /**
     * Find shoppings by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws ServiceException;

    /**
     * Find shoppings by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws ServiceException;
}
