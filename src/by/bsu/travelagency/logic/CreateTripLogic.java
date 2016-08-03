package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Trip;
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
    private final static Logger LOG = Logger.getLogger(CreateTripLogic.class);

    final static int TRIP_ID_FOR_INSERT = 0;

    public static boolean checkCreateTrip(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterCities, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) {
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                trip.setArrivalDate(format.parse(enterArrivalDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            trip.setPrice(Integer.parseInt(enterPrice));
            trip.setLastMinute(("on".equals(enterLastMinute)));
            trip.setCities(enterCities);
            trip.setAttractions(enterAttractions);
            trip.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
            trip.setServices(enterServices);

            TripDAO tripDAO = new TripDAO();
            Long lastId = tripDAO.findLastTripId() + 1L;
            try {
                img.write(savePath + File.separator + lastId + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            trip.setPathImage("/images/trips/"+ lastId + ".jpg"); // картинка!!!


            if (tripDAO.insertTrip(trip)){
                flag = true;
            }
        }
            return flag;
        }
    }
