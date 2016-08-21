package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.logic.CreateUserLogic;
import by.bsu.travelagency.logic.CreateVacationLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.IntegerPatternConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateUserCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateUserCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SURNAME = "surname";
    private static final String PARAM_NAME_ROLE = "role";
    private static final String PARAM_NAME_DISCOUNT = "discount";
    private static final String PARAM_NAME_MONEY = "money";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        int role = Integer.parseInt(request.getParameter(PARAM_NAME_ROLE));
        double discount = Double.parseDouble(request.getParameter(PARAM_NAME_DISCOUNT));
        int money = Integer.parseInt(request.getParameter(PARAM_NAME_MONEY));


        if (CreateUserLogic.checkCreateUser(login, pass, email, name, surname, role, discount, money)) {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        else {
            request.setAttribute("errorCreateUserMessage",
                    TravelController.messageManager.getProperty("message.createusererror"));
            page = ConfigurationManager.getProperty("path.page.admin.create.user");
        }

        return page;
    }

}
