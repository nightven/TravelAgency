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

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateVacationLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateVacationLogic.class);

    /** The Constant VACATION_ID_FOR_INSERT. */
    private final static int VACATION_ID_FOR_INSERT = 0;

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
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkCreateVacation(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCityId,
                                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateHotel(enterHotel)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Vacation vacation = new Vacation();
            vacation.setId(VACATION_ID_FOR_INSERT);
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
                Long lastId = null;
                lastId = vacationDAO.findLastVacationId() + 1L;
                img.write(savePath + File.separator + lastId + ".jpg");

                vacation.setPathImage("/images/vacations/"+ lastId + ".jpg");

                if (vacationDAO.create(vacation)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create vacation.", e);
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Vacation).", e);
            } catch (IOException e) {
                throw new BusinessLogicException("Failed to write to file (Vacation).", e);
            }
        }
        return flag;
        }
    }
