package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcVacationDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditVacationLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditVacationLogic.class);

    /** The Constant REGEX_FILE_NAME. */
    private final static String REGEX_FILE_NAME = "([0-9])*";


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
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCityId,
                                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateHotel(enterHotel)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Vacation vacation = new Vacation();
            vacation.setId(Integer.parseInt(enterId));
            vacation.setName(enterName);
            vacation.setSummary(enterSummary);
            vacation.setDescription(enterDescription);
            LOG.debug("Vacation: enterDepartureDate = " + enterDepartureDate);
            try {
                vacation.setDepartureDate(format.parse(enterDepartureDate));
                vacation.setArrivalDate(format.parse(enterArrivalDate));

                vacation.setPrice(Integer.parseInt(enterPrice));
                vacation.setLastMinute(("on".equals(enterLastMinute)));
                vacation.setHotel(enterHotel);
                JdbcCityDAO cityDAO = new JdbcCityDAO();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                vacation.setServices(enterServices);

                JdbcVacationDAO vacationDAO = new JdbcVacationDAO();
                String pathImage = null;
                pathImage = vacationDAO.findPathImageVacationById(vacation.getId());
                Pattern patternFileName = Pattern.compile(REGEX_FILE_NAME);
                Matcher matcherFileName = patternFileName.matcher(pathImage);
                String fileName = null;

                while (matcherFileName.find()){
                    if (!matcherFileName.group().isEmpty()){
                        fileName = matcherFileName.group();
                        LOG.info("Logic: fileName: " + fileName);
                    }
                }

                LOG.debug("Logic: pathImage: " + "/images/vacations/"+ fileName + ".jpg");

                img.write(savePath + File.separator + fileName + ".jpg");
                vacation.setPathImage("/images/vacations/"+ fileName + ".jpg");

                if (vacationDAO.update(vacation)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to update vacation.", e);
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Vacation).", e);
            } catch (IOException e) {
                throw new BusinessLogicException("Failed to write to file (Vacation).", e);
            }
        }
            return flag;
        }

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
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateHotel(enterHotel)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Vacation vacation = new Vacation();
            vacation.setId(Integer.parseInt(enterId));
            vacation.setName(enterName);
            vacation.setSummary(enterSummary);
            vacation.setDescription(enterDescription);
            LOG.debug("Vacation: enterDepartureDate = " + enterDepartureDate);
            try {
                vacation.setDepartureDate(format.parse(enterDepartureDate));
                vacation.setArrivalDate(format.parse(enterArrivalDate));

                vacation.setPrice(Integer.parseInt(enterPrice));
                vacation.setLastMinute(("on".equals(enterLastMinute)));
                vacation.setHotel(enterHotel);
                JdbcCityDAO cityDAO = new JdbcCityDAO();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                vacation.setServices(enterServices);

                JdbcVacationDAO vacationDAO = new JdbcVacationDAO();
                String pathImage = null;
                pathImage = vacationDAO.findPathImageVacationById(vacation.getId());

                LOG.debug("Logic: pathImage: " + pathImage);
                vacation.setPathImage(pathImage);

                if (vacationDAO.update(vacation)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to update vacation.", e);
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Vacation).", e);
            }
        }
        return flag;
    }
}
