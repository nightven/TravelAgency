package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.BalanceLogic;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Михаил on 2/16/2016.
 */
public class BalanceAddCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(BalanceAddCommand.class);

    /** The Constant PARAM_NAME_MONEY. */
    private static final String PARAM_NAME_MONEY = "money";
    
    /** The Constant PARAM_NAME_ID_USER. */
    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
        int moneyToAdd = Integer.parseInt(request.getParameter(PARAM_NAME_MONEY));
        UserDAO userDAO = new UserDAO();
        try {
            if (BalanceLogic.checkBalanceToAdd(userId, moneyToAdd)) {
                User user = userDAO.findEntityById(userId);
                request.setAttribute("userProfile", user);
                page = ConfigurationManager.getProperty("path.page.balance");
            } else {
                User user = userDAO.findEntityById(userId);
                request.setAttribute("userProfile", user);
                request.setAttribute("errorBalanceAddMessage",
                        TravelController.messageManager.getProperty("message.balanceadderror"));
                page = ConfigurationManager.getProperty("path.page.balance");
            }
        } catch (BusinessLogicException | DAOException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
