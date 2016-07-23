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
}
