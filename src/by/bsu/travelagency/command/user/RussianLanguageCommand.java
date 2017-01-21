package by.bsu.travelagency.command.user;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.ConfigurationManager;
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
public class RussianLanguageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(RussianLanguageCommand.class);

    /** The Constant COOKIE_NAME. */
    private final static String COOKIE_NAME = "locale";
    
    /** The Constant COOKIE_VALUE. */
    private final static String COOKIE_VALUE = "ru_RU";
    
    /** The Constant LOCALE_VALUE. */
    private final static String LOCALE_VALUE = "ru";
    
    /** The Constant COOKIE_AGE_IN_SEC. */
    private final static int COOKIE_AGE_IN_SEC = 86_400;

    /** The Constant PARAM_HEADER_REFERER. */
    private static final String PARAM_HEADER_REFERER = "referer";

    /** The Constant PARAM_NAME_SERVLET. */
    private static final String PARAM_NAME_SERVLET = "travel?";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Locale rus = new Locale(LOCALE_VALUE, LOCALE_VALUE.toUpperCase());
        Locale.setDefault(rus);
        TravelController.messageManager.changeResource(rus);
        TravelController.resourceManager.changeResource(rus);
        HttpSession session = request.getSession(true);
        Cookie cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        cookie.setMaxAge(COOKIE_AGE_IN_SEC);
        response.addCookie(cookie);
        request.setAttribute("lang", COOKIE_VALUE);
        session.setAttribute("lang", COOKIE_VALUE);
        try {
            URL url = new URL(request.getHeader(PARAM_HEADER_REFERER));
            request.setAttribute("redirect", PARAM_NAME_SERVLET + url.getQuery());
        } catch (MalformedURLException e) {
            throw new CommandException("URL malformed.");
        }
        if (session.getAttribute("user") == null) {
            page = ConfigurationManager.getProperty("path.page.login");
        }
        else {
            page = ConfigurationManager.getProperty("path.page.main");
        }
        return page;
    }
}
