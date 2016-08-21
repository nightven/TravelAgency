package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateUserLogic {
    private final static Logger LOG = Logger.getLogger(CreateUserLogic.class);

    final static int USER_ID_FOR_INSERT = 0;


    public static boolean checkCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, int enterMoney) {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            User user = new User();
            user.setId(USER_ID_FOR_INSERT);
            user.setLogin(enterLogin);
            user.setPassword(MD5Util.md5Encode(enterPass));
            user.setRole(enterRole);
            user.setEmail(enterEmail);
            user.setName(enterName);
            user.setSurname(enterSurname);
            user.setDiscount(enterDiscount);
            user.setMoney(enterMoney);

            UserDAO userDAO = new UserDAO();
            if (userDAO.insertUser(user)){
                flag = true;
            }
        }
            return flag;
        }
    }
