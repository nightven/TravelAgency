package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ChangePasswordLogic {
    private final static Logger LOG = Logger.getLogger(ChangePasswordLogic.class);

    public static boolean checkPassword(String enterOldPass, String enterNewPass, Long userId) {
        boolean flag = false;
        if (Validator.validatePassword(enterNewPass)){
            UserDAO userDAO = new UserDAO();
            String passwordOld = userDAO.findPasswordByUserId(userId);
            if (passwordOld.equals(MD5Util.md5Encode(enterOldPass))){
                if (userDAO.updatePasswordByUserId(userId, MD5Util.md5Encode(enterNewPass))){
                    flag = true;
                }
            }
        }
        return flag;
    }
}
