package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.BalanceLogic;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;

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

    /** The Constant PARAM_HEADER_REFERER. */
    private static final String PARAM_HEADER_REFERER = "referer";

    /** The Constant PARAM_NAME_SERVLET. */
    private static final String PARAM_NAME_SERVLET = "travel?";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
        int moneyToAdd = Integer.parseInt(request.getParameter(PARAM_NAME_MONEY));
        JdbcUserDAO userDAO = new JdbcUserDAO();
        try {
            if (BalanceLogic.checkBalanceToAdd(userId, moneyToAdd)) {
                User user = userDAO.findEntityById(userId);
                request.setAttribute("userProfile", user);
                URL url = new URL(request.getHeader(PARAM_HEADER_REFERER));
                request.setAttribute("redirect", PARAM_NAME_SERVLET + url.getQuery());
                page = ConfigurationManager.getProperty("path.page.balance");
            } else {
                User user = userDAO.findEntityById(userId);
                request.setAttribute("userProfile", user);
                URL url = new URL(request.getHeader(PARAM_HEADER_REFERER));
                request.setAttribute("redirect", PARAM_NAME_SERVLET + url.getQuery());
                request.setAttribute("errorBalanceAddMessage",
                        TravelController.messageManager.getProperty("message.balanceadderror"));
                page = ConfigurationManager.getProperty("path.page.balance");
            }
        } catch (MalformedURLException | BusinessLogicException | DAOException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
