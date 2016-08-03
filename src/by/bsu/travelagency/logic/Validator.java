package by.bsu.travelagency.logic;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 2/16/2016.
 */
public class Validator {
    private final static Logger LOG = Logger.getLogger(Validator.class);

    final static String REGEX_LOGIN = "([A-Za-zА-Яа-я0-9_-]){3,16}";
    final static String REGEX_PASSWORD = "([A-Za-zА-Яа-я0-9_-]){8,20}";
    final static String REGEX_EMAIL = "([A-za-z0-9_\\.-]+)@([A-za-z0-9_\\.-]+)\\.([A-za-z\\.]{2,6})";
    final static String REGEX_NAME = "([A-Za-zА-Яа-я]){2,25}";
    final static String REGEX_SURNAME = "([A-Za-zА-Яа-я]){2,25}";
    final static String REGEX_NAME_TOUR = "(.){1,45}";
    final static String REGEX_SUMMARY = "(.){1,255}";
    final static String REGEX_PRICE = "([0-9]){1,45}";
    final static String REGEX_HOTEL = "(.){1,100}";
    final static String REGEX_DESTINATION_COUNTRY = "(.){1,50}";
    final static String REGEX_DESTINATION_CITY = "(.){1,50}";


    public static boolean validateLogin(String enterLogin) {
        boolean flag = false;
        Pattern pLogin = Pattern.compile(REGEX_LOGIN);
        Matcher mLogin = pLogin.matcher(enterLogin);
        LOG.debug("Validate Login: " + mLogin.matches());
        if (mLogin.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validatePassword(String enterPass) {
        boolean flag = false;
        Pattern pPassword = Pattern.compile(REGEX_PASSWORD);
        Matcher mPassword = pPassword.matcher(enterPass);
        LOG.debug("Validate Password: " + mPassword.matches());
        if (mPassword.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateEmail(String enterEmail) {
        boolean flag = false;
        Pattern pEmail = Pattern.compile(REGEX_EMAIL);
        Matcher mEmail = pEmail.matcher(enterEmail);
        LOG.debug("Validate Email: " + mEmail.matches());
        if (mEmail.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateName(String enterName) {
        boolean flag = false;
        Pattern pName = Pattern.compile(REGEX_NAME);
        Matcher mName = pName.matcher(enterName);
        LOG.debug("Validate Name: " + mName.matches());
        if (mName.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateSurname(String enterSurname) {
        boolean flag = false;
        Pattern pSurname = Pattern.compile(REGEX_SURNAME);
        Matcher mSurname = pSurname.matcher(enterSurname);
        LOG.debug("Validate Surname: " + mSurname.matches());
        if (mSurname.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateNameTour(String enterNameTour) {
        boolean flag = false;
        Pattern pNameVacation = Pattern.compile(REGEX_NAME_TOUR);
        Matcher mNameVacation = pNameVacation.matcher(enterNameTour);
        LOG.debug("Validate NameTour: " + mNameVacation.matches());
        if (mNameVacation.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateSummary(String enterSummary) {
        boolean flag = false;
        Pattern pSummary = Pattern.compile(REGEX_SUMMARY);
        Matcher mSummary = pSummary.matcher(enterSummary);
        LOG.debug("Validate Summary: " + mSummary.matches());
        if (mSummary.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validatePrice(String enterPrice) {
        boolean flag = false;
        Pattern pPrice = Pattern.compile(REGEX_PRICE);
        Matcher mPrice = pPrice.matcher(enterPrice);
        LOG.debug("Validate Price: " + mPrice.matches());
        if (mPrice.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateHotel(String enterHotel) {
        boolean flag = false;
        Pattern pHotel = Pattern.compile(REGEX_HOTEL);
        Matcher mHotel = pHotel.matcher(enterHotel);
        LOG.debug("Validate Hotel: " + mHotel.matches());
        if (mHotel.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateDestinationCountry(String enterDestinationCountry) {
        boolean flag = false;
        Pattern pDestinationCountry = Pattern.compile(REGEX_DESTINATION_COUNTRY);
        Matcher mDestinationCountry = pDestinationCountry.matcher(enterDestinationCountry);
        LOG.debug("Validate DestinationCountry: " + mDestinationCountry.matches());
        if (mDestinationCountry.matches()){
            flag = true;
        }
        return flag;
    }

    public static boolean validateDestinationCity(String enterDestinationCity) {
        boolean flag = false;
        Pattern pDestinationCity = Pattern.compile(REGEX_DESTINATION_CITY);
        Matcher mDestinationCity = pDestinationCity.matcher(enterDestinationCity);
        LOG.debug("Validate DestinationCity: " + mDestinationCity.matches());
        if (mDestinationCity.matches()){
            flag = true;
        }
        return flag;
    }
}
