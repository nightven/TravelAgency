package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserDeleteAdminListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(UserDeleteAdminListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        UserServiceImpl userService = new UserServiceImpl();
        List<UserOrderNumber> userOrderNumbers = null;
        try {
            userOrderNumbers = userService.findAllUsersWithOrderCount();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("users", userOrderNumbers);
        page = ConfigurationManager.getProperty("path.page.admin.delete.list.user");
        return page;
    }
}
