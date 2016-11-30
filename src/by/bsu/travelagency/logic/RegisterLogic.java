package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class RegisterLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(RegisterLogic.class);

    /** The Constant USER_ID_FOR_INSERT. */
    final static int USER_ID_FOR_INSERT = 0;
    
    /** The Constant USER_DEFAULT_ROLE. */
    final static int USER_DEFAULT_ROLE = 0;
    
    /** The Constant USER_DEFAULT_DISCOUNT. */
    final static double USER_DEFAULT_DISCOUNT = 0;
    
    /** The Constant USER_DEFAULT_MONEY. */
    final static int USER_DEFAULT_MONEY = 0;

    /**
     * Check register.
     *
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkRegister(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname) throws BusinessLogicException {
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
            user.setDiscount(USER_DEFAULT_DISCOUNT);
            user.setMoney(USER_DEFAULT_MONEY);

            UserDAO userDAO = new UserDAO();
            try {
                if (userDAO.create(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create user (Register).", e);
            }
        }
            return flag;
        }
    }
