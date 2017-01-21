package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.logic.EditUserLogic;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditUserCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditUserCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";
    
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

        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        int role = Integer.parseInt(request.getParameter(PARAM_NAME_ROLE));
        double discount = Double.parseDouble(request.getParameter(PARAM_NAME_DISCOUNT));
        int money = Integer.parseInt(request.getParameter(PARAM_NAME_MONEY));

        try {
            if (pass.isEmpty()) {
                if (EditUserLogic.checkEditUser(id, login, email, name, surname, role, discount, money)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                } else {
                    request.setAttribute("errorEditUserMessage",
                            TravelController.messageManager.getProperty("message.editusererror"));
                    page = ConfigurationManager.getProperty("path.page.admin.edit.info.user");
                }
            } else {
                if (EditUserLogic.checkEditUser(id, login, pass, email, name, surname, role, discount, money)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                } else {
                    request.setAttribute("errorEditUserMessage",
                            TravelController.messageManager.getProperty("message.editusererror"));
                    page = ConfigurationManager.getProperty("path.page.admin.edit.info.user");
                }
            }
        } catch (BusinessLogicException e){
            throw new CommandException(e);
        }

        return page;
    }

}
