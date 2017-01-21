package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class UserAdminListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(UserAdminListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        JdbcUserDAO userDAO = new JdbcUserDAO();
        List<UserOrderNumber> users = null;
        try {
            users = userDAO.findAllUsersWithOrderCount();
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("users", users);
        page = ConfigurationManager.getProperty("path.page.admin.edit.list.user");
        return page;
    }
}