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

public class CreateUserCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateUserCommand.class);

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
    
    /** The Constant PARAM_NAME_ROLE. */
    private static final String PARAM_NAME_ROLE = "role";
    
    /** The Constant PARAM_NAME_DISCOUNT. */
    private static final String PARAM_NAME_DISCOUNT = "discount";
    
    /** The Constant PARAM_NAME_MONEY. */
    private static final String PARAM_NAME_MONEY = "money";

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
        int role = Integer.parseInt(request.getParameter(PARAM_NAME_ROLE));
        double discount = Double.parseDouble(request.getParameter(PARAM_NAME_DISCOUNT));
        double money = Double.parseDouble(request.getParameter(PARAM_NAME_MONEY));
        UserServiceImpl userService = new UserServiceImpl();

        try {
            if (userService.checkCreateUser(login, pass, email, name, surname, role, discount, money)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateUserMessage",
                        TravelController.messageManager.getProperty("message.createusererror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.user");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
