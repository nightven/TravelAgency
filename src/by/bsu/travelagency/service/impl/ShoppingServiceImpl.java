package by.bsu.travelagency.service.impl;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcShoppingDAO;
import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.util.Validator;
import by.bsu.travelagency.service.ShoppingService;
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
 * The Class ShoppingServiceImpl.
 */
public class ShoppingServiceImpl implements ShoppingService {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ShoppingServiceImpl.class);

    /** The Constant SHOPPING_ID_FOR_INSERT. */
    private final static int SHOPPING_ID_FOR_INSERT = 0;

    /** The Constant REGEX_FILE_NAME. */
    private final static String REGEX_FILE_NAME = "([0-9])*";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#checkCreateShopping(String, String, String, String, String, String, String, String, String, String, String, Part, String)
     */
    @Override
    public boolean checkCreateShopping(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCityId,
                                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(SHOPPING_ID_FOR_INSERT);
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));

                shopping.setPrice(Double.parseDouble(enterPrice));
                shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = JdbcShoppingDAO.getInstance();
                Long lastId = null;
                lastId = shoppingDAO.findLastShoppingId() + 1L;

                img.write(savePath + File.separator + lastId + ".jpg");

                shopping.setPathImage("/images/shoppings/"+ lastId + ".jpg");

                if (shoppingDAO.create(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create shopping.", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Shopping).", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findAllCities()
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
     * @see by.bsu.travelagency.service.ShoppingService#delete(java.lang.Long)
     */
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#checkEditShopping(String, String, String, String, String, String, String, String, String, String, String, String, Part, String)
     */
    public boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription, Part img, String savePath) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(Integer.parseInt(enterId));
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));
                shopping.setPrice(Double.parseDouble(enterPrice));
                shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = JdbcShoppingDAO.getInstance();
                String pathImage = null;
                pathImage = shoppingDAO.findPathImageShoppingById(shopping.getId());
                Pattern patternFileName = Pattern.compile(REGEX_FILE_NAME);
                Matcher matcherFileName = patternFileName.matcher(pathImage);
                String fileName = null;

                while (matcherFileName.find()){
                    if (!matcherFileName.group().isEmpty()){
                        fileName = matcherFileName.group();
                        LOG.info("Logic: fileName: " + fileName);
                    }
                }

                LOG.debug("Logic: pathImage: " + "/images/shoppings/"+ fileName + ".jpg");
                img.write(savePath + File.separator + fileName + ".jpg");

                shopping.setPathImage("/images/shoppings/"+ fileName + ".jpg");

                if (shoppingDAO.update(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update shopping.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Shopping).", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#checkEditShopping(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(Integer.parseInt(enterId));
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));

                shopping.setPrice(Double.parseDouble(enterPrice));
                shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = JdbcShoppingDAO.getInstance();
                String pathImage = null;

                pathImage = shoppingDAO.findPathImageShoppingById(shopping.getId());

                LOG.debug("Logic: pathImage: " + pathImage);

                shopping.setPathImage(pathImage);

                if (shoppingDAO.update(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update shopping.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findEntityById(java.lang.Long)
     */
    @Override
    public Shopping findEntityById(Long id) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findUserById(java.lang.Long)
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
     * @see by.bsu.travelagency.service.ShoppingService#findMoneyByUserId(java.lang.Long)
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
     * @see by.bsu.travelagency.service.ShoppingService#findAllShoppingsAfterNow(java.sql.Date)
     */
    @Override
    public List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findAllShoppingsAfterNow(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findAllSortShoppingsAfterNow(java.sql.Date, java.lang.String, boolean)
     */
    @Override
    public List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findAllSortShoppingsAfterNow(nowDate, criterion, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#selectLastShoppings(java.sql.Date)
     */
    @Override
    public List<Shopping> selectLastShoppings(Date nowDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().selectLastShoppings(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findShoppingsByNameAfterNow(java.sql.Date, java.lang.String)
     */
    @Override
    public List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByNameAfterNow(nowDate, name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findShoppingsByDepartureDateAfterNow(java.sql.Date, java.sql.Date)
     */
    @Override
    public List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByDepartureDateAfterNow(nowDate, departureDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findShoppingsByArrivalDateAfterNow(java.sql.Date, java.sql.Date)
     */
    @Override
    public List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByArrivalDateAfterNow(nowDate, arrivalDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findShoppingsByPriceAfterNow(java.sql.Date, double)
     */
    @Override
    public List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByPriceAfterNow(nowDate, price);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.ShoppingService#findShoppingsByTransportAfterNow(java.sql.Date, java.lang.String)
     */
    @Override
    public List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByTransportAfterNow(nowDate, transport);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
