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

public class RegisterCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(RegisterCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    
    private static final String PARAM_NAME_PASSWORD = "password";
    
    private static final String PARAM_NAME_EMAIL = "email";
    
    private static final String PARAM_NAME_NAME = "name";
    
    private static final String PARAM_NAME_SURNAME = "surname";
    
    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        LOG.debug("Encoding (Register): " + request.getCharacterEncoding());
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (userService.checkRegister(login, pass, email, name, surname)) {
                page = ConfigurationManager.getProperty("path.page.login");
            }
            else {
                request.setAttribute("errorRegisterPassMessage",
                        TravelController.messageManager.getProperty("message.registererror"));
                page = ConfigurationManager.getProperty("path.page.register");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
