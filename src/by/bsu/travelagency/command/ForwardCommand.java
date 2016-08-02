package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.LoginLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ForwardCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(ForwardCommand.class);

    private static final String PARAM_NAME_PAGE = "page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        String requestPage = request.getParameter(PARAM_NAME_PAGE);
        switch (requestPage){
            case "login":
                page = ConfigurationManager.getProperty("path.page.login");
                break;
            case "register":
                page = ConfigurationManager.getProperty("path.page.register");
                break;
            case "main":
                page = ConfigurationManager.getProperty("path.page.main");
                break;
            case "vacations":
                page = ConfigurationManager.getProperty("path.page.vacation.list");
                break;
            case "trips":
                page = ConfigurationManager.getProperty("path.page.trip.list");
                break;
            case "shoppings":
                page = ConfigurationManager.getProperty("path.page.shopping.list");
                break;
            case "admin_panel":
                page = ConfigurationManager.getProperty("path.page.admin.panel");
                break;
            case "create_vacation":
                page = ConfigurationManager.getProperty("path.page.admin.create.vacation");
                break;
            case "create_trip":
                page = ConfigurationManager.getProperty("path.page.admin.create.trip");
                break;
            default:
                page = ConfigurationManager.getProperty("path.page.login");
                break;
        }
        return page;
    }
}
