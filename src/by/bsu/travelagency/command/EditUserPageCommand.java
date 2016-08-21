package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditUserPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditUserPageCommand.class);

    private static final String PARAM_NAME_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findEntityById(id);
        request.setAttribute("userProfile", user);
        page = ConfigurationManager.getProperty("path.page.admin.edit.info.user");
        return page;
    }

}
