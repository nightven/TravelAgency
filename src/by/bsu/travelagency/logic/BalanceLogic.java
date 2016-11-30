package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import org.apache.log4j.Logger;

/**
 * Created by Михаил on 2/16/2016.
 */
public class BalanceLogic {
    
    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(BalanceLogic.class);

    /**
     * Check balance to add.
     *
     * @param userId the user id
     * @param money the money
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkBalanceToAdd(Long userId, int money) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateBalanceToAdd(money)){
            UserDAO userDAO = new UserDAO();

            try {
                if (userDAO.updateUserBalanceAddition(userId, money)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to update user balance (adding).", e);
            }
        }
        return flag;
    }
    }
