package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.sql.Date;
import java.util.List;

/**
 * The Interface VacationService.
 */
public interface VacationService {

    /**
     * Check create vacation.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterHotel the enter hotel
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
    boolean checkCreateVacation(String enterName, String enterSummary, String enterDepartureDate,
                                String enterArrivalDate, String enterDestinationCityId,
                                String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * Check edit vacation.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterHotel the enter hotel
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
    boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;

    /**
     * Check edit vacation.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterHotel the enter hotel
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription) throws ServiceException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the vacation
     * @throws ServiceException the service exception
     */
    Vacation findEntityById(Long id) throws ServiceException;

    /**
     * Find all vacations after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findAllVacationsAfterNow(Date nowDate) throws ServiceException;

    /**
     * Find all sort vacations after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findAllSortVacationsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException;

    /**
     * Select last vacations.
     *
     * @param nowDate the now date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> selectLastVacations(Date nowDate) throws ServiceException;

    /**
     * Find vacations by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findVacationsByNameAfterNow(Date nowDate, String name) throws ServiceException;

    /**
     * Find vacations by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findVacationsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException;

    /**
     * Find vacations by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findVacationsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException;

    /**
     * Find vacations by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findVacationsByPriceAfterNow(Date nowDate, double price) throws ServiceException;

    /**
     * Find vacations by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Vacation> findVacationsByTransportAfterNow(Date nowDate, String transport) throws ServiceException;
}
