package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditUserPageCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        UserServiceImpl userService = new UserServiceImpl();
        User user = null;
        try {
            user = userService.findEntityById(id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == user.getId()) {
            request.setAttribute("userProfile", user);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.user");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }

}
