package by.bsu.travelagency.service.impl;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.dao.jdbc.JdbcVacationDAO;
import by.bsu.travelagency.entity.*;
import by.bsu.travelagency.util.Validator;
import by.bsu.travelagency.service.VacationService;
import by.bsu.travelagency.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class VacationServiceImpl.
 */
public class VacationServiceImpl implements VacationService {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(VacationServiceImpl.class);

    /** The Constant VACATION_ID_FOR_INSERT. */
    private final static int VACATION_ID_FOR_INSERT = 0;

    /** The Constant REGEX_FILE_NAME. */
    private final static String REGEX_FILE_NAME = "([0-9])*";


    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#checkCreateVacation(String, String, String, String, String, String, String, String, String, String, String, Part, String)
     */
    public boolean checkCreateVacation(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCityId,
                                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException {
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

                vacation.setPrice(Double.parseDouble(enterPrice));
                vacation.setLastMinute(("on".equals(enterLastMinute)));
                vacation.setHotel(enterHotel);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                vacation.setServices(enterServices);

                JdbcVacationDAO vacationDAO = JdbcVacationDAO.getInstance();
                Long lastId = null;
                lastId = vacationDAO.findLastVacationId() + 1L;
                img.write(savePath + File.separator + lastId + ".jpg");

                vacation.setPathImage("/images/vacations/"+ lastId + ".jpg");

                if (vacationDAO.create(vacation)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create vacation.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Vacation).", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Vacation).", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findAllCities()
     */
    @Override
    public List<City> findAllCities() throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().findAllCities();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#delete(java.lang.Long)
     */
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#checkEditVacation(String, String, String, String, String, String, String, String, String, String, String, String, Part, String)
     */
    public boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription, Part img, String savePath) throws ServiceException {
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

                vacation.setPrice(Double.parseDouble(enterPrice));
                vacation.setLastMinute(("on".equals(enterLastMinute)));
                vacation.setHotel(enterHotel);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                vacation.setServices(enterServices);

                JdbcVacationDAO vacationDAO = JdbcVacationDAO.getInstance();
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
                throw new ServiceException("Failed to update vacation.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Vacation).", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Vacation).", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#checkEditVacation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription) throws ServiceException {
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

                vacation.setPrice(Double.parseDouble(enterPrice));
                vacation.setLastMinute(("on".equals(enterLastMinute)));
                vacation.setHotel(enterHotel);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                vacation.setCities(cities);
                vacation.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                vacation.setServices(enterServices);

                JdbcVacationDAO vacationDAO = JdbcVacationDAO.getInstance();
                String pathImage = null;
                pathImage = vacationDAO.findPathImageVacationById(vacation.getId());

                LOG.debug("Logic: pathImage: " + pathImage);
                vacation.setPathImage(pathImage);

                if (vacationDAO.update(vacation)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update vacation.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Vacation).", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findEntityById(java.lang.Long)
     */
    @Override
    public Vacation findEntityById(Long id) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findUserById(java.lang.Long)
     */
    @Override
    public User findUserById(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findMoneyByUserId(java.lang.Long)
     */
    @Override
    public double findMoneyByUserId(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findMoneyByUserId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findAllVacationsAfterNow(java.sql.Date)
     */
    @Override
    public List<Vacation> findAllVacationsAfterNow(Date nowDate) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findAllVacationsAfterNow(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findAllSortVacationsAfterNow(java.sql.Date, java.lang.String, boolean)
     */
    @Override
    public List<Vacation> findAllSortVacationsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findAllSortVacationsAfterNow(nowDate, criterion, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#selectLastVacations(java.sql.Date)
     */
    @Override
    public List<Vacation> selectLastVacations(Date nowDate) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().selectLastVacations(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findVacationsByNameAfterNow(java.sql.Date, java.lang.String)
     */
    @Override
    public List<Vacation> findVacationsByNameAfterNow(Date nowDate, String name) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findVacationsByNameAfterNow(nowDate, name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findVacationsByDepartureDateAfterNow(java.sql.Date, java.sql.Date)
     */
    @Override
    public List<Vacation> findVacationsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findVacationsByDepartureDateAfterNow(nowDate, departureDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findVacationsByArrivalDateAfterNow(java.sql.Date, java.sql.Date)
     */
    @Override
    public List<Vacation> findVacationsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findVacationsByArrivalDateAfterNow(nowDate, arrivalDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findVacationsByPriceAfterNow(java.sql.Date, double)
     */
    @Override
    public List<Vacation> findVacationsByPriceAfterNow(Date nowDate, double price) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findVacationsByPriceAfterNow(nowDate, price);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.VacationService#findVacationsByTransportAfterNow(java.sql.Date, java.lang.String)
     */
    @Override
    public List<Vacation> findVacationsByTransportAfterNow(Date nowDate, String transport) throws ServiceException {
        try {
            return JdbcVacationDAO.getInstance().findVacationsByTransportAfterNow(nowDate, transport);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
