package by.bsu.travelagency.service.impl;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.util.MD5Util;
import by.bsu.travelagency.util.Validator;
import by.bsu.travelagency.util.exception.UtilException;
import by.bsu.travelagency.service.UserService;
import by.bsu.travelagency.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The Class UserServiceImpl.
 */
public class UserServiceImpl implements UserService {

    private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private final static int USER_ID_FOR_INSERT = 0;

    private final static int USER_DEFAULT_ROLE = 0;

    private final static double USER_DEFAULT_DISCOUNT = 0;

    private final static int USER_DEFAULT_MONEY = 0;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#findEntityById(java.lang.Long)
     */
    @Override
    public User findEntityById(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#checkBalanceToAdd(java.lang.Long, double)
     */
    public boolean checkBalanceToAdd(Long userId, double money) throws ServiceException {
        boolean flag = false;
        if (Validator.validateBalanceToAdd(money)){
            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();

            try {
                if (userDAO.updateUserBalanceAddition(userId, money)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update user balance (adding).", e);
            }
        }
        return flag;
    }

    /**
     * Check password.
     *
     * @param enterOldPass the enter old pass
     * @param enterNewPass the enter new pass
     * @param userId the user id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public static boolean checkPassword(String enterOldPass, String enterNewPass, Long userId) throws ServiceException {
        boolean flag = false;
        if (Validator.validatePassword(enterNewPass)){
            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
            String passwordOld = null;
            try {
                passwordOld = userDAO.findPasswordByUserId(userId);
            } catch (DAOException e) {
                throw new ServiceException("Failed to find password by user id.", e);
            }
            try {
                if (passwordOld.equals(MD5Util.md5Encode(enterOldPass))){
                    if (userDAO.updatePasswordByUserId(userId, MD5Util.md5Encode(enterNewPass))){
                        flag = true;
                    }
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update password by user id.", e);
            } catch (UtilException e) {
                throw new ServiceException(e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#checkCreateUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, double, double)
     */
    public boolean checkCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            flag = generalCreateUser(enterLogin,enterPass,enterEmail,enterName,enterSurname,enterRole,enterDiscount,enterMoney);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#delete(java.lang.Long)
     */
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#checkEditUser(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, double, double)
     */
    public boolean checkEditUser(Long enterId, String enterLogin, String enterEmail, String enterName,
                                        String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException {
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

            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
            String password = null;
            try {
                password = userDAO.findPasswordByUserId(user.getId());
                user.setPassword(password);

                if (userDAO.update(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update user.", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#checkEditUser(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, double, double)
     */
    public boolean checkEditUser(Long enterId, String enterLogin, String enterPass, String enterEmail, String enterName,
                                        String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname) && Validator.validateUserCreateMoney(enterMoney) && Validator.validateUserCreateDiscount(enterDiscount)){
            try {
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

            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
                if (userDAO.update(user)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update user.", e);
            } catch (UtilException e) {
                throw new ServiceException(e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#findUserByName(java.lang.String)
     */
    @Override
    public User findUserByName(String name) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findUserByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#checkLogin(java.lang.String, java.lang.String)
     */
    public boolean checkLogin(String enterLogin, String enterPass) throws ServiceException {
        boolean flag = false;
        JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
        List<User> users = null;
        try {
            users = userDAO.findAllUsers();
            for (int i = 0; i < users.size(); i++) {
                if (enterLogin.equals(users.get(i).getLogin())) {
                    if (MD5Util.md5Encode(enterPass).equals(users.get(i).getPassword())) {
                        flag = true;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to find all users.", e);
        } catch (UtilException e) {
            throw new ServiceException(e);
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#checkRegister(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean checkRegister(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname) throws ServiceException {
        boolean flag = false;
        if (Validator.validateLogin(enterLogin) && Validator.validatePassword(enterPass) && Validator.validateEmail(enterEmail) && Validator.validateName(enterName) && Validator.validateSurname(enterSurname)){
            flag = generalCreateUser(enterLogin, enterPass, enterEmail, enterName, enterSurname, USER_DEFAULT_ROLE, USER_DEFAULT_DISCOUNT, USER_DEFAULT_MONEY);
        }
        return flag;
    }


    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#findAllUsersWithOrderCount()
     */
    @Override
    public List<UserOrderNumber> findAllUsersWithOrderCount() throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findAllUsersWithOrderCount();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.UserService#findMoneyByUserId(java.lang.Long)
     */
    @Override
    public double findMoneyByUserId(Long id) throws ServiceException {
        try {
            return JdbcUserDAO.getInstance().findMoneyByUserId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * General create user.
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
     * @throws ServiceException the service exception
     */
    private boolean generalCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException{
            boolean flag = false;
            JdbcUserDAO userDAO = JdbcUserDAO.getInstance();
            try {
                if (userDAO.findUserByName(enterLogin).getId() == 0) {
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

                    if (userDAO.create(user)) {
                        flag = true;
                    }
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create user (Register).", e);
            } catch (UtilException e) {
                throw new ServiceException(e);
            }
        return flag;
    }
}
