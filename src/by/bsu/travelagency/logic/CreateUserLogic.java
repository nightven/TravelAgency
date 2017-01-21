package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateUserLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateUserLogic.class);

    /** The Constant USER_ID_FOR_INSERT. */
    private final static int USER_ID_FOR_INSERT = 0;


    /**
     * Check create user.
     *
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @param enterRole the enter role
     * @param enterDiscount the enter discount
     * @param enterMoney the enter money
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, int enterMoney) throws BusinessLogicException {
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

            JdbcUserDAO userDAO = new JdbcUserDAO();
            try {
                if (userDAO.create(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create user.", e);
            }
        }
            return flag;
        }
    }
