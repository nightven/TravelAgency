package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.LoginLogic;
import by.bsu.travelagency.logic.RegisterLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Михаил on 2/16/2016.
 */
public class RegisterCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(RegisterCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SURNAME = "surname";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
// извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
// проверка логина и пароля
        if (RegisterLogic.checkRegister(login, pass, email, name, surname)) {
            page = ConfigurationManager.getProperty("path.page.login");
        }
        else {
            request.setAttribute("errorRegisterPassMessage",
                    TravelController.messageManager.getProperty("message.registererror"));
            page = ConfigurationManager.getProperty("path.page.register");
        }
        return page;
    }
}
