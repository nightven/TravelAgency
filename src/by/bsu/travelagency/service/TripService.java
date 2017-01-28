package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Interface TripService.
 */
public interface TripService {

    /**
     * Check create trip.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterAttractions the enter attractions
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @param img the img
     * @param savePath the save path
     * @param cities the cities
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkCreateTrip(String enterName, String enterSummary, String enterDepartureDate,
                            String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                            String enterServices, String enterDescription, Part img, String savePath, ArrayList<City> cities) throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * Check edit trip.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterAttractions the enter attractions
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @param img the img
     * @param savePath the save path
     * @param cities the cities
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                  String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                                  String enterServices, String enterDescription, Part img, String savePath, ArrayList<City> cities) throws ServiceException;

    /**
     * Check edit trip.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterAttractions the enter attractions
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @param cities the cities
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                          String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                          String enterServices, String enterDescription, ArrayList<City> cities) throws ServiceException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the trip
     * @throws ServiceException the service exception
     */
    Trip findEntityById(Long id) throws ServiceException;

    /**
     * Find all trips after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findAllTripsAfterNow(Date nowDate) throws ServiceException;

    /**
     * Find all sort trips after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findAllSortTripsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException;

    /**
     * Select last trips.
     *
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> selectLastTrips(Date nowDate) throws ServiceException;

    /**
     * Find trips by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findTripsByNameAfterNow(Date nowDate, String name) throws ServiceException;

    /**
     * Find trips by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findTripsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException;

    /**
     * Find trips by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findTripsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException;

    /**
     * Find trips by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findTripsByPriceAfterNow(Date nowDate, double price) throws ServiceException;

    /**
     * Find trips by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Trip> findTripsByTransportAfterNow(Date nowDate, String transport) throws ServiceException;
}
