package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class LoginLogic {
    private final static Logger LOG = Logger.getLogger(LoginLogic.class);

    public static boolean checkLogin(String enterLogin, String enterPass) {
        boolean flag = false;
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.findAllUsers();
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
