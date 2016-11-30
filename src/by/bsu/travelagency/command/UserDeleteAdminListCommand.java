package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class UserDeleteAdminListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(UserDeleteAdminListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        UserDAO userDAO = new UserDAO();
        List<UserOrderNumber> userOrderNumbers = null;
        try {
            userOrderNumbers = userDAO.findAllUsersWithOrderCount();
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("users", userOrderNumbers);
        page = ConfigurationManager.getProperty("path.page.admin.delete.list.user");
        return page;
    }
}
