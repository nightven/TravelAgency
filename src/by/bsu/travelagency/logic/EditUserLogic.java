package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditUserLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditUserLogic.class);

    /**
     * Check edit user.
     *
     * @param enterId the enter id
     * @param enterLogin the enter login
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @param enterRole the enter role
     * @param enterDiscount the enter discount
     * @param enterMoney the enter money
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkEditUser(Long enterId, String enterLogin, String enterEmail, String enterName,
                                              String enterSurname, int enterRole, double enterDiscount, int enterMoney) throws BusinessLogicException {
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
            String password = null;
            try {
                password = userDAO.findPasswordByUserId(user.getId());
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to find password by user id.", e);
            }
            user.setPassword(password);

            try {
                if (userDAO.update(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to update user.", e);
            }
        }
            return flag;
        }

    /**
     * Check edit user.
     *
     * @param enterId the enter id
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
    public static boolean checkEditUser(Long enterId, String enterLogin, String enterPass, String enterEmail, String enterName,
                                        String enterSurname, int enterRole, double enterDiscount, int enterMoney) throws BusinessLogicException {
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
            try {
                if (userDAO.update(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to update user.", e);
            }
        }
        return flag;
    }
}
