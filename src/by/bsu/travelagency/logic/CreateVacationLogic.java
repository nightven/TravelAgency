package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
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
public class CreateVacationLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateVacationLogic.class);

    /** The Constant VACATION_ID_FOR_INSERT. */
    final static int VACATION_ID_FOR_INSERT = 0;

    /**
     * Check create vacation.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCountry the enter destination country
     * @param enterDestinationCity the enter destination city
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
                                              String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateHotel(enterHotel) && Validator.validateDestinationCountry(enterDestinationCountry) && Validator.validateDestinationCity(enterDestinationCity)){
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
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Vacation).", e);
            }
            vacation.setPrice(Integer.parseInt(enterPrice));
            vacation.setLastMinute(("on".equals(enterLastMinute)));
            vacation.setHotel(enterHotel);
            vacation.setDestinationCity(enterDestinationCity);
            vacation.setDestinationCountry(enterDestinationCountry);
            vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
            vacation.setServices(enterServices);

            VacationDAO vacationDAO = new VacationDAO();
            Long lastId = null;
            try {
                lastId = vacationDAO.findLastVacationId() + 1L;
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to find last vacation id.", e);
            }
            try {
                img.write(savePath + File.separator + lastId + ".jpg");
            } catch (IOException e) {
                throw new BusinessLogicException("Failed to write to file (Vacation).", e);
            }
            vacation.setPathImage("/images/vacations/"+ lastId + ".jpg");


            try {
                if (vacationDAO.create(vacation)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create vacation.", e);
            }
        }
            return flag;
        }
    }
