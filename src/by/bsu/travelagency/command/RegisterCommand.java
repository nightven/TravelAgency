package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.logic.RegisterLogic;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class RegisterCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(RegisterCommand.class);

    /** The Constant PARAM_NAME_LOGIN. */
    private static final String PARAM_NAME_LOGIN = "login";
    
    /** The Constant PARAM_NAME_PASSWORD. */
    private static final String PARAM_NAME_PASSWORD = "password";
    
    /** The Constant PARAM_NAME_EMAIL. */
    private static final String PARAM_NAME_EMAIL = "email";
    
    /** The Constant PARAM_NAME_NAME. */
    private static final String PARAM_NAME_NAME = "name";
    
    /** The Constant PARAM_NAME_SURNAME. */
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
        try {
            if (RegisterLogic.checkRegister(login, pass, email, name, surname)) {
                page = ConfigurationManager.getProperty("path.page.login");
            }
            else {
                request.setAttribute("errorRegisterPassMessage",
                        TravelController.messageManager.getProperty("message.registererror"));
                page = ConfigurationManager.getProperty("path.page.register");
            }
        } catch (BusinessLogicException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
