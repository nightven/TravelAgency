package by.bsu.travelagency.controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Class AdminAuthFilter.
 */

public class AdminAuthFilter implements Filter {

    /**
     * The Constant LOG.
     */
    private final static Logger LOG = Logger.getLogger(AdminAuthFilter.class);

    /**
     * The Constant PARAM_NAME_COMMAND
     */
    private static final String PARAM_NAME_COMMAND = "command";

    /**
     * The Constant PARAM_NAME_PAGE.
     */
    private static final String PARAM_NAME_PAGE = "page";

    /**
     * The Constant PARAM_NAME_USER_ROLE.
     */
    private static final String PARAM_NAME_USER_ROLE = "role";

    /**
     * The Constant COMMAND_NAME_FORWARD.
     */
    private static final String COMMAND_NAME_FORWARD = "forward";

    /**
     * The Constant REDIRECT_ADDRESS.
     */
    private static final String REDIRECT_ADDRESS = "travel?command=tour_list";

    /**
     * The restricted command list.
     */
    private ArrayList<String> commands;

    /**
     * The restricted page list.
     */
    private ArrayList<String> pages;

    /**
     * Inits the.
     *
     * @param fConfig the f config
     * @throws ServletException the servlet exception
     */
    public void init(FilterConfig fConfig) throws ServletException {
        commands = new ArrayList<>();
        pages = new ArrayList<>();
        commands.add("create_vacation");
        commands.add("create_vacation_page");
        commands.add("create_trip");
        commands.add("create_trip_page");
        commands.add("create_shopping");
        commands.add("create_shopping_page");
        commands.add("vacation_admin_list");
        commands.add("trip_admin_list");
        commands.add("edit_vacation_page");
        commands.add("edit_vacation");
        commands.add("edit_trip_page");
        commands.add("edit_trip");
        commands.add("edit_shopping_page");
        commands.add("edit_shopping");
        commands.add("vacation_delete_admin_list");
        commands.add("trip_delete_admin_list");
        commands.add("shopping_delete_admin_list");
        commands.add("delete_vacation");
        commands.add("delete_trip");
        commands.add("delete_shopping");
        commands.add("create_user");
        commands.add("user_admin_list");
        commands.add("edit_user_page");
        commands.add("edit_user");
        commands.add("user_delete_admin_list");
        commands.add("delete_user");
        commands.add("create_country");
        commands.add("create_city");
        commands.add("create_city_page");
        commands.add("country_admin_list");
        commands.add("city_admin_list");
        commands.add("edit_country");
        commands.add("edit_country_page");
        commands.add("edit_city");
        commands.add("edit_city_page");
        commands.add("country_delete_admin_list");
        commands.add("city_delete_admin_list");
        commands.add("delete_country");
        commands.add("delete_city");
        commands.add("forward");

        pages.add("admin_panel");
        pages.add("create_vacation");
        pages.add("create_trip");
        pages.add("create_shopping");
        pages.add("create_user");
        pages.add("create_country");
    }

    /**
     * Do filter.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        boolean pass = true;
        int role = 0;
        if (session.getAttribute(PARAM_NAME_USER_ROLE) != null) {
            role = (Integer) session.getAttribute(PARAM_NAME_USER_ROLE);
        }
        LOG.debug("User role = " + session.getAttribute(PARAM_NAME_USER_ROLE) + ", isNotAdmin = " + (role != 1));
        if (role != 1) {
            pass = checkRestrictedCommands(httpRequest);
        }

        if (pass) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(REDIRECT_ADDRESS);
        }
    }

    /**
     * Destroy.
     */
    public void destroy() {
    }

    private boolean checkRestrictedCommands(HttpServletRequest httpRequest) {
        boolean toReturn = true;
        String command = httpRequest.getParameter(PARAM_NAME_COMMAND);
        if (command != null && commands.contains(command)) {
            if (command.equals(COMMAND_NAME_FORWARD)) {
                String page = httpRequest.getParameter(PARAM_NAME_PAGE);
                if (page != null && pages.contains(page)) {
                    toReturn = false;
                }
            } else {
                toReturn = false;
            }
        }
        return toReturn;
    }

}