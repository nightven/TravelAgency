package by.bsu.travelagency.command.trip;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.TripServiceImpl;
import by.bsu.travelagency.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TripFullCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TripFullCommand.class);

    private static final String PARAM_NAME_ID = "id";
    
    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        Long iduser = (Long) request.getSession().getAttribute(PARAM_NAME_ID_USER);
        TripServiceImpl tripService = new TripServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        Trip trip = null;
        try {
            trip = tripService.findEntityById(id);
            if (id == trip.getId()) {
                request.setAttribute("trip", trip);
                request.setAttribute("lastCity", trip.getCities().size()-1);
                if (iduser != null) {
                    User user = userService.findEntityById(iduser);
                    request.setAttribute("userProfile", user);
                }
                page = ConfigurationManager.getProperty("path.page.trip.full");
            } else {
                page = ConfigurationManager.getProperty("path.page.main");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
