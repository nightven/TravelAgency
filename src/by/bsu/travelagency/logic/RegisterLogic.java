package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 2/16/2016.
 */
public class RegisterLogic {
    private final static Logger LOG = Logger.getLogger(RegisterLogic.class);

    final static int USER_ID_FOR_INSERT = 0;
    final static int USER_DEFAULT_ROLE = 0;

    public static boolean checkRegister(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname) {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname)){
            User user = new User();
            user.setId(USER_ID_FOR_INSERT);
            user.setLogin(enterLogin);
            user.setPassword(MD5Util.md5Encode(enterPass));
            user.setRole(USER_DEFAULT_ROLE);
            user.setEmail(enterEmail);
            user.setName(enterName);
            user.setSurname(enterSurname);

            UserDAO userDAO = new UserDAO();
            if (userDAO.insertUser(user)){
                flag = true;
            }
        }
            return flag;
        }
    }
