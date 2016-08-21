package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class UserAdminListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(UserAdminListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        UserDAO userDAO = new UserDAO();
        List<UserOrderNumber> users = userDAO.findAllUsersWithOrderCount();
        request.setAttribute("users", users);
        page = ConfigurationManager.getProperty("path.page.admin.edit.list.user");
        return page;
    }
}
