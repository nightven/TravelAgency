package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ChangePasswordLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ChangePasswordLogic.class);

    /**
     * Check password.
     *
     * @param enterOldPass the enter old pass
     * @param enterNewPass the enter new pass
     * @param userId the user id
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkPassword(String enterOldPass, String enterNewPass, Long userId) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validatePassword(enterNewPass)){
            UserDAO userDAO = new UserDAO();
            String passwordOld = null;
            try {
                passwordOld = userDAO.findPasswordByUserId(userId);
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to find password by user id.", e);
            }
            if (passwordOld.equals(MD5Util.md5Encode(enterOldPass))){
                try {
                    if (userDAO.updatePasswordByUserId(userId, MD5Util.md5Encode(enterNewPass))){
                        flag = true;
                    }
                } catch (DAOException e) {
                    throw new BusinessLogicException("Failed to update password by user id.", e);
                }
            }
        }
        return flag;
    }
}
