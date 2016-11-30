package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.LoginLogic;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Михаил on 2/16/2016.
 */
public class LoginCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(LoginCommand.class);

    /** The Constant PARAM_NAME_LOGIN. */
    private static final String PARAM_NAME_LOGIN = "login";
    
    /** The Constant PARAM_NAME_PASSWORD. */
    private static final String PARAM_NAME_PASSWORD = "password";
    
    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            if (LoginLogic.checkLogin(login, pass)) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.findUserByName(login);
                HttpSession session = request.getSession(true);
                if (session.getAttribute("user") == null) {
                    session.setAttribute("user", user.getLogin());
                }
                if (session.getAttribute("role") == null) {
                    session.setAttribute("role", user.getRole());
                }
                if (session.getAttribute("iduser") == null) {
                    session.setAttribute("iduser", user.getId());
                }
                page = ConfigurationManager.getProperty("path.page.main");
            } else {
                request.setAttribute("errorLoginPassMessage",
                        TravelController.messageManager.getProperty("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.login");
            }
        } catch (BusinessLogicException | DAOException e) {
            throw new CommandException(e);
        }

        return page;
    }
}
