package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Shopping;
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
public class CreateShoppingLogic {
    private final static Logger LOG = Logger.getLogger(CreateShoppingLogic.class);

    final static int SHOPPING_ID_FOR_INSERT = 0;

    public static boolean checkCreateShopping(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) {
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                shopping.setArrivalDate(format.parse(enterArrivalDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            shopping.setPrice(Integer.parseInt(enterPrice));
            shopping.setLastMinute(("on".equals(enterLastMinute)));
            shopping.setShops(enterShops);
            shopping.setDestinationCity(enterDestinationCity);
            shopping.setDestinationCountry(enterDestinationCountry);
            shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
            shopping.setServices(enterServices);

            ShoppingDAO shoppingDAO = new ShoppingDAO();
            Long lastId = shoppingDAO.findLastShoppingId() + 1L;
            try {
                img.write(savePath + File.separator + lastId + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            shopping.setPathImage("/images/shoppings/"+ lastId + ".jpg"); // картинка!!!


            if (shoppingDAO.insertShopping(shopping)){
                flag = true;
            }
        }
            return flag;
        }
    }
