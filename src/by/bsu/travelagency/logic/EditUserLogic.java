package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Transport;
import by.bsu.travelagency.entity.User;
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
public class EditUserLogic {
    private final static Logger LOG = Logger.getLogger(EditUserLogic.class);

    public static boolean checkEditUser(Long enterId, String enterLogin, String enterEmail, String enterName,
                                              String enterSurname, int enterRole, double enterDiscount, int enterMoney) {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            User user = new User();
            user.setId(enterId);
            user.setLogin(enterLogin);
            user.setRole(enterRole);
            user.setEmail(enterEmail);
            user.setName(enterName);
            user.setSurname(enterSurname);
            user.setDiscount(enterDiscount);
            user.setMoney(enterMoney);

            UserDAO userDAO = new UserDAO();
            String password = userDAO.findPasswordByUserId(user.getId());
            user.setPassword(password);

            if (userDAO.updateUser(user)){
                flag = true;
            }
        }
            return flag;
        }

    public static boolean checkEditUser(Long enterId, String enterLogin, String enterPass, String enterEmail, String enterName,
                                        String enterSurname, int enterRole, double enterDiscount, int enterMoney) {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            User user = new User();
            user.setId(enterId);
            user.setLogin(enterLogin);
            user.setPassword(MD5Util.md5Encode(enterPass));
            user.setRole(enterRole);
            user.setEmail(enterEmail);
            user.setName(enterName);
            user.setSurname(enterSurname);
            user.setDiscount(enterDiscount);
            user.setMoney(enterMoney);

            UserDAO userDAO = new UserDAO();
            if (userDAO.updateUser(user)){
                flag = true;
            }
        }
        return flag;
    }
}
