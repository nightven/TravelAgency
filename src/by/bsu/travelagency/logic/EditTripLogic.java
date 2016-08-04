package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditTripLogic {
    private final static Logger LOG = Logger.getLogger(EditTripLogic.class);

    final static String REGEX_FILE_NAME = "([0-9])*";


    public static boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterCities, String enterAttractions, String enterLastMinute,
                                            String enterPrice, String enterTransport, String enterServices, String enterDescription,
                                            Part img, String savePath) {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(enterId));
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
            String pathImage = tripDAO.findPathImageTripById(trip.getId());
            Pattern patternFileName = Pattern.compile(REGEX_FILE_NAME);
            Matcher matcherFileName = patternFileName.matcher(pathImage);
            String fileName = null;

            while (matcherFileName.find()){
                if (!matcherFileName.group().isEmpty()){
                    fileName = matcherFileName.group();
                    LOG.info("Logic: fileName: " + fileName);
                }
            }

            LOG.debug("Logic: pathImage: " + "/images/trips/"+ fileName + ".jpg");
            try {
                img.write(savePath + File.separator + fileName + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            trip.setPathImage("/images/trips/"+ fileName + ".jpg"); // картинка!!!


            if (tripDAO.updateTrip(trip)){
                flag = true;
            }
        }
            return flag;
        }

    public static boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterCities, String enterAttractions, String enterLastMinute,
                                            String enterPrice, String enterTransport, String enterServices, String enterDescription) {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(enterId));
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
            String pathImage = tripDAO.findPathImageTripById(trip.getId());

            LOG.debug("Logic: pathImage: " + pathImage);

            trip.setPathImage(pathImage); // картинка!!!


            if (tripDAO.updateTrip(trip)){
                flag = true;
            }
        }
        return flag;
    }
    }
