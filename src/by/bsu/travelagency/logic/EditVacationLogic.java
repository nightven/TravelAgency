package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.Vacation;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.StringMatchFilter;

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
public class EditVacationLogic {
    private final static Logger LOG = Logger.getLogger(EditVacationLogic.class);

    final static String REGEX_FILE_NAME = "([0-9])*";


    public static boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateHotel(enterHotel) && Validator.validateDestinationCountry(enterDestinationCountry) && Validator.validateDestinationCity(enterDestinationCity)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Vacation vacation = new Vacation();
            vacation.setId(Integer.parseInt(enterId));
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
            String pathImage = vacationDAO.findPathImageVacationById(vacation.getId());
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
            try {
                img.write(savePath + File.separator + fileName + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            vacation.setPathImage("/images/vacations/"+ fileName + ".jpg"); // картинка!!!


            if (vacationDAO.updateVacation(vacation)){
                flag = true;
            }
        }
            return flag;
        }

    public static boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                            String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription) {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateHotel(enterHotel) && Validator.validateDestinationCountry(enterDestinationCountry) && Validator.validateDestinationCity(enterDestinationCity)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Vacation vacation = new Vacation();
            vacation.setId(Integer.parseInt(enterId));
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
            String pathImage = vacationDAO.findPathImageVacationById(vacation.getId());

            LOG.debug("Logic: pathImage: " + pathImage);

            vacation.setPathImage(pathImage); // картинка!!!


            if (vacationDAO.updateVacation(vacation)){
                flag = true;
            }
        }
        return flag;
    }
    }
