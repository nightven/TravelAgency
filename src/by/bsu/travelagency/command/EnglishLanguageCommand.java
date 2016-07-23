package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.LoginLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EnglishLanguageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EnglishLanguageCommand.class);

    final static String COOKIE_NAME = "locale";
    final static String COOKIE_VALUE = "en_US";
    final static int COOKIE_AGE_IN_SEC = 86_400;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Locale.setDefault(Locale.US);
        TravelController.messageManager.changeResource(Locale.US);
        TravelController.resourceManager.changeResource(Locale.US);
        HttpSession session = request.getSession(true);
        Cookie cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        cookie.setMaxAge(COOKIE_AGE_IN_SEC);
        response.addCookie(cookie);
        request.setAttribute("lang", COOKIE_VALUE);
        session.setAttribute("lang", COOKIE_VALUE);
        if (session.getAttribute("user") == null) {
            page = ConfigurationManager.getProperty("path.page.login");
        }
        else {
            page = ConfigurationManager.getProperty("path.page.main");
        }
        return page;
    }
}
