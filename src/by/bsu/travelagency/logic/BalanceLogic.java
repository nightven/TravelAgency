package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.Order;
import by.bsu.travelagency.entity.TourType;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Михаил on 2/16/2016.
 */
public class BalanceLogic {
    private final static Logger LOG = Logger.getLogger(BalanceLogic.class);

    public static boolean checkBalanceToAdd(Long userId, int money) {
        boolean flag = false;
        if (Validator.validateBalanceToAdd(money)){
            UserDAO userDAO = new UserDAO();

            if (userDAO.updateUserBalanceAddition(userId, money)){
                flag = true;
            }
        }
        return flag;
    }
    }
