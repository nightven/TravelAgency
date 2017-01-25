package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (userService.checkLogin(login, pass)) {
                User user = userService.findUserByName(login);
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
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }
}
