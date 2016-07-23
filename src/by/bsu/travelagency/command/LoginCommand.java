package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.logic.LoginLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class LoginCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(LoginCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
// извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
// проверка логина и пароля
        if (LoginLogic.checkLogin(login, pass)) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserByName(login);
            HttpSession session = request.getSession(true);
            if (session.getAttribute("role") == null) {
                session.setAttribute("role", user.getRole());
            }
            if (session.getAttribute("user") == null) {
                session.setAttribute("user", user.getLogin());
            }
//            request.setAttribute("user", login);
//            определение пути к main.jsp
            page = ConfigurationManager.getProperty("path.page.main");
        } else {
            request.setAttribute("errorLoginPassMessage",
                    TravelController.messageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
