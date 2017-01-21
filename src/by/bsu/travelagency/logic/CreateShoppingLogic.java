package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcShoppingDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Transport;
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
public class CreateShoppingLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateShoppingLogic.class);

    /** The Constant SHOPPING_ID_FOR_INSERT. */
    private final static int SHOPPING_ID_FOR_INSERT = 0;

    /**
     * Check create shopping.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCityId the enter destination city id
     * @param enterShops the enter shops
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
    public static boolean checkCreateShopping(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCityId,
                                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws BusinessLogicException {
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

                shopping.setPrice(Integer.parseInt(enterPrice));
                shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = new JdbcCityDAO();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = new JdbcShoppingDAO();
                Long lastId = null;
                lastId = shoppingDAO.findLastShoppingId() + 1L;

                img.write(savePath + File.separator + lastId + ".jpg");

                shopping.setPathImage("/images/shoppings/"+ lastId + ".jpg");

                if (shoppingDAO.create(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create shopping.", e);
            } catch (IOException e) {
                throw new BusinessLogicException("Failed to write to file (Shopping).", e);
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Shopping).", e);
            }
        }
            return flag;
        }
    }
