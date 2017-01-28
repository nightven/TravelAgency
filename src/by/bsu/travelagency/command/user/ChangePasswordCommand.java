package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(ChangePasswordCommand.class);

    private static final String PARAM_NAME_PASSWORD_OLD = "password_old";
    
    private static final String PARAM_NAME_PASSWORD_NEW = "password_new";
    
    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        HttpSession session = request.getSession();
        if (session.getAttribute(PARAM_NAME_ID_USER) != null) {
            Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
            String passwordOld = request.getParameter(PARAM_NAME_PASSWORD_OLD);
            String passwordNew = request.getParameter(PARAM_NAME_PASSWORD_NEW);
            UserServiceImpl userService = new UserServiceImpl();
            try {
                if (userService.checkPassword(passwordOld, passwordNew, userId)) {
                    request.setAttribute("changePasswordSuccessMessage", TravelController.messageManager.getProperty("message.changepasswordsuccess"));
                    page = ConfigurationManager.getProperty("path.page.change.password");
                }
                else {
                    request.setAttribute("errorChangePasswordMessage", TravelController.messageManager.getProperty("message.changepassworderror"));
                    page = ConfigurationManager.getProperty("path.page.change.password");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute("errorNotAuthorizedMessage",
                    TravelController.messageManager.getProperty("message.notauthorizederror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}
