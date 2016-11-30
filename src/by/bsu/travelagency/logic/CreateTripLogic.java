package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateTripLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateTripLogic.class);

    /** The Constant TRIP_ID_FOR_INSERT. */
    final static int TRIP_ID_FOR_INSERT = 0;

    /**
     * Check create trip.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterCities the enter cities
     * @param enterAttractions the enter attractions
     * @param enterLastMinute the enter last minute
     * @param enterPrice the enter price
     * @param enterTransport the enter transport
     * @param enterServices the enter services
     * @param enterDescription the enter description
     * @param img the img
     * @param savePath the save path
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkCreateTrip(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterCities, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Trip trip = new Trip();
            trip.setId(TRIP_ID_FOR_INSERT);
            trip.setName(enterName);
            trip.setSummary(enterSummary);
            trip.setDescription(enterDescription);
            try {
                trip.setDepartureDate(format.parse(enterDepartureDate));
                trip.setArrivalDate(format.parse(enterArrivalDate));
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Trip).", e);
            }
            trip.setPrice(Integer.parseInt(enterPrice));
            trip.setLastMinute(("on".equals(enterLastMinute)));
            trip.setCities(enterCities);
            trip.setAttractions(enterAttractions);
            trip.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
            trip.setServices(enterServices);

            TripDAO tripDAO = new TripDAO();
            Long lastId = null;
            try {
                lastId = tripDAO.findLastTripId() + 1L;
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to find last trip id.", e);
            }
            try {
                img.write(savePath + File.separator + lastId + ".jpg");
            } catch (IOException e) {
                throw new BusinessLogicException("Failed to write to file (Trip).", e);
            }
            trip.setPathImage("/images/trips/"+ lastId + ".jpg");


            try {
                if (tripDAO.create(trip)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create trip.", e);
            }
        }
            return flag;
        }
    }
