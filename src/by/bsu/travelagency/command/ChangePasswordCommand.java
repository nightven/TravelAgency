package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.logic.ChangePasswordLogic;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ChangePasswordCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ChangePasswordCommand.class);

    /** The Constant PARAM_NAME_PASSWORD_OLD. */
    private static final String PARAM_NAME_PASSWORD_OLD = "password_old";
    
    /** The Constant PARAM_NAME_PASSWORD_NEW. */
    private static final String PARAM_NAME_PASSWORD_NEW = "password_new";
    
    /** The Constant PARAM_NAME_ID_USER. */
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

            try {
                if (ChangePasswordLogic.checkPassword(passwordOld, passwordNew, userId)) {
                    request.setAttribute("changePasswordSuccessMessage",
                            TravelController.messageManager.getProperty("message.changepasswordsuccess"));
                    page = ConfigurationManager.getProperty("path.page.change.password");
                }
                else {
                    request.setAttribute("errorChangePasswordMessage",
                            TravelController.messageManager.getProperty("message.changepassworderror"));
                    page = ConfigurationManager.getProperty("path.page.change.password");
                }
            } catch (BusinessLogicException e) {
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
