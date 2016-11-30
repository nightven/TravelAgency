package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Transport;
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
public class CreateShoppingLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateShoppingLogic.class);

    /** The Constant SHOPPING_ID_FOR_INSERT. */
    final static int SHOPPING_ID_FOR_INSERT = 0;

    /**
     * Check create shopping.
     *
     * @param enterName the enter name
     * @param enterSummary the enter summary
     * @param enterDepartureDate the enter departure date
     * @param enterArrivalDate the enter arrival date
     * @param enterDestinationCountry the enter destination country
     * @param enterDestinationCity the enter destination city
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
                                              String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateDestinationCountry(enterDestinationCountry) && Validator.validateDestinationCity(enterDestinationCity)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(SHOPPING_ID_FOR_INSERT);
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));
            } catch (ParseException e) {
                throw new BusinessLogicException("Failed to parse date (Shopping).", e);
            }
            shopping.setPrice(Integer.parseInt(enterPrice));
            shopping.setLastMinute(("on".equals(enterLastMinute)));
            shopping.setShops(enterShops);
            shopping.setDestinationCity(enterDestinationCity);
            shopping.setDestinationCountry(enterDestinationCountry);
            shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
            shopping.setServices(enterServices);

            ShoppingDAO shoppingDAO = new ShoppingDAO();
            Long lastId = null;
            try {
                lastId = shoppingDAO.findLastShoppingId() + 1L;
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to find last shopping id.", e);
            }
            try {
                img.write(savePath + File.separator + lastId + ".jpg");
            } catch (IOException e) {
                throw new BusinessLogicException("Failed to write to file (Shopping).", e);
            }
            shopping.setPathImage("/images/shoppings/"+ lastId + ".jpg");


            try {
                if (shoppingDAO.create(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create shopping.", e);
            }
        }
            return flag;
        }
    }
