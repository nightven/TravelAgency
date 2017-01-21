package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class LoginLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(LoginLogic.class);

    /**
     * Check login.
     *
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkLogin(String enterLogin, String enterPass) throws BusinessLogicException {
        boolean flag = false;
        JdbcUserDAO userDAO = new JdbcUserDAO();
        List<User> users = null;
        try {
            users = userDAO.findAllUsers();
        } catch (DAOException e) {
            throw new BusinessLogicException("Failed to find all users.", e);
        }
        for (int i = 0; i < users.size(); i++) {
            if (enterLogin.equals(users.get(i).getLogin())) {
                if (MD5Util.md5Encode(enterPass).equals(users.get(i).getPassword())) {
                    flag = true;
                }
            }
        }
            return flag;
        }
    }
