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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditShoppingLogic {
    private final static Logger LOG = Logger.getLogger(EditShoppingLogic.class);

    final static String REGEX_FILE_NAME = "([0-9])*";


    public static boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateDestinationCountry(enterDestinationCountry) && Validator.validateDestinationCity(enterDestinationCity)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(Integer.parseInt(enterId));
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
            String pathImage = shoppingDAO.findPathImageShoppingById(shopping.getId());
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
            try {
                img.write(savePath + File.separator + fileName + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            shopping.setPathImage("/images/shoppings/"+ fileName + ".jpg"); // картинка!!!


            if (shoppingDAO.updateShopping(shopping)){
                flag = true;
            }
        }
            return flag;
        }

    public static boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCountry, String enterDestinationCity,
                                            String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription) {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateDestinationCountry(enterDestinationCountry) && Validator.validateDestinationCity(enterDestinationCity)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(Integer.parseInt(enterId));
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
            String pathImage = shoppingDAO.findPathImageShoppingById(shopping.getId());

            LOG.debug("Logic: pathImage: " + pathImage);

            shopping.setPathImage(pathImage); // картинка!!!


            if (shoppingDAO.updateShopping(shopping)){
                flag = true;
            }
        }
        return flag;
    }
    }
