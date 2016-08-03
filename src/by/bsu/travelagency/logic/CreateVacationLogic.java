package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
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
    private final static Logger LOG = Logger.getLogger(CreateVacationLogic.class);

    final static int VACATION_ID_FOR_INSERT = 0;

    public static boolean checkCreateVacation(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) {
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                vacation.setArrivalDate(format.parse(enterArrivalDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vacation.setPrice(Integer.parseInt(enterPrice));
            vacation.setLastMinute(("on".equals(enterLastMinute)));
            vacation.setHotel(enterHotel);
            vacation.setDestinationCity(enterDestinationCity);
            vacation.setDestinationCountry(enterDestinationCountry);
            vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
            vacation.setServices(enterServices);

            VacationDAO vacationDAO = new VacationDAO();
            Long lastId = vacationDAO.findLastVacationId() + 1L;
            try {
                img.write(savePath + File.separator + lastId + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            vacation.setPathImage("/images/vacations/"+ lastId + ".jpg"); // картинка!!!


            if (vacationDAO.insertVacation(vacation)){
                flag = true;
            }
        }
            return flag;
        }
    }
