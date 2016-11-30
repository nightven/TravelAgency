package by.bsu.travelagency.logic;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 2/16/2016.
 */
public class Validator {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(Validator.class);

    /** The Constant REGEX_LOGIN. */
    final static String REGEX_LOGIN = "([A-Za-zА-Яа-я0-9_-]){3,16}";
    
    /** The Constant REGEX_PASSWORD. */
    final static String REGEX_PASSWORD = "([A-Za-zА-Яа-я0-9_-]){8,20}";
    
    /** The Constant REGEX_EMAIL. */
    final static String REGEX_EMAIL = "([A-za-z0-9_\\.-]+)@([A-za-z0-9_\\.-]+)\\.([A-za-z\\.]{2,6})";
    
    /** The Constant REGEX_NAME. */
    final static String REGEX_NAME = "([A-Za-zА-Яа-я]){2,25}";
    
    /** The Constant REGEX_SURNAME. */
    final static String REGEX_SURNAME = "([A-Za-zА-Яа-я]){2,25}";
    
    /** The Constant REGEX_NAME_TOUR. */
    final static String REGEX_NAME_TOUR = "(.){1,45}";
    
    /** The Constant REGEX_SUMMARY. */
    final static String REGEX_SUMMARY = "(.){1,255}";
    
    /** The Constant REGEX_PRICE. */
    final static String REGEX_PRICE = "([0-9]){1,45}";
    
    /** The Constant REGEX_HOTEL. */
    final static String REGEX_HOTEL = "(.){1,100}";
    
    /** The Constant REGEX_DESTINATION_COUNTRY. */
    final static String REGEX_DESTINATION_COUNTRY = "(.){1,50}";
    
    /** The Constant REGEX_DESTINATION_CITY. */
    final static String REGEX_DESTINATION_CITY = "(.){1,50}";
    
    /** The Constant ORDER_MIN_QUANTITY. */
    final static int ORDER_MIN_QUANTITY = 1;
    
    /** The Constant ORDER_MAX_QUANTITY. */
    final static int ORDER_MAX_QUANTITY = 100;
    
    /** The Constant BALANCE_MIN_MONEY_TO_ADD. */
    final static int BALANCE_MIN_MONEY_TO_ADD = 1;
    
    /** The Constant BALANCE_MAX_MONEY_TO_ADD. */
    final static int BALANCE_MAX_MONEY_TO_ADD = 10000;
    
    /** The Constant USER_MIN_MONEY_CREATE_USER. */
    final static int USER_MIN_MONEY_CREATE_USER = 0;
    
    /** The Constant USER_MAX_MONEY_CREATE_USER. */
    final static int USER_MAX_MONEY_CREATE_USER = 100000;
    
    /** The Constant USER_MIN_DISCOUNT. */
    final static int USER_MIN_DISCOUNT = 0;
    
    /** The Constant USER_MAX_DISCOUNT. */
    final static int USER_MAX_DISCOUNT = 1;


    /**
     * Validate login.
     *
     * @param enterLogin the enter login
     * @return true, if successful
     */
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

    /**
     * Validate password.
     *
     * @param enterPass the enter pass
     * @return true, if successful
     */
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

    /**
     * Validate email.
     *
     * @param enterEmail the enter email
     * @return true, if successful
     */
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

    /**
     * Validate name.
     *
     * @param enterName the enter name
     * @return true, if successful
     */
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

    /**
     * Validate surname.
     *
     * @param enterSurname the enter surname
     * @return true, if successful
     */
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

    /**
     * Validate name tour.
     *
     * @param enterNameTour the enter name tour
     * @return true, if successful
     */
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

    /**
     * Validate summary.
     *
     * @param enterSummary the enter summary
     * @return true, if successful
     */
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

    /**
     * Validate price.
     *
     * @param enterPrice the enter price
     * @return true, if successful
     */
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

    /**
     * Validate hotel.
     *
     * @param enterHotel the enter hotel
     * @return true, if successful
     */
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

    /**
     * Validate destination country.
     *
     * @param enterDestinationCountry the enter destination country
     * @return true, if successful
     */
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

    /**
     * Validate destination city.
     *
     * @param enterDestinationCity the enter destination city
     * @return true, if successful
     */
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

    /**
     * Validate quantity.
     *
     * @param quantity the quantity
     * @return true, if successful
     */
    public static boolean validateQuantity(int quantity) {
        boolean flag = false;
        if (quantity >= ORDER_MIN_QUANTITY && quantity <= ORDER_MAX_QUANTITY){
            flag = true;
        }
        LOG.debug("Validate quantity: " + flag);
        return flag;
    }

    /**
     * Validate order date.
     *
     * @param departureDate the departure date
     * @return true, if successful
     */
    public static boolean validateOrderDate(Date departureDate) {
        boolean flag = false;
        Date nowDate = new Date();
        if (nowDate.before(departureDate)){
            flag = true;
        }
        LOG.debug("Validate order date: " + flag);
        return flag;
    }

    /**
     * Validate balance to add.
     *
     * @param money the money
     * @return true, if successful
     */
    public static boolean validateBalanceToAdd(int money) {
        boolean flag = false;
        if (money >= BALANCE_MIN_MONEY_TO_ADD && money <= BALANCE_MAX_MONEY_TO_ADD){
            flag = true;
        }
        LOG.debug("Validate money to add: " + flag);
        return flag;
    }

    /**
     * Validate user create money.
     *
     * @param money the money
     * @return true, if successful
     */
    public static boolean validateUserCreateMoney(int money) {
        boolean flag = false;
        if (money >= USER_MIN_MONEY_CREATE_USER && money <= USER_MAX_MONEY_CREATE_USER){
            flag = true;
        }
        LOG.debug("Validate money create user: " + flag);
        return flag;
    }

    /**
     * Validate user create discount.
     *
     * @param discount the discount
     * @return true, if successful
     */
    public static boolean validateUserCreateDiscount(double discount) {
        boolean flag = false;
        if (discount >= USER_MIN_DISCOUNT && discount <= USER_MAX_DISCOUNT){
            flag = true;
        }
        LOG.debug("Validate discount create user: " + flag);
        return flag;
    }
}
