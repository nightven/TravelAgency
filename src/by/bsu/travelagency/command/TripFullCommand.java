package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class TripFullCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TripFullCommand.class);

    private static final String PARAM_NAME_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        TripDAO tripDAO = new TripDAO();
        Trip trip = tripDAO.findEntityById(id);
        request.setAttribute("trip", trip);
        page = ConfigurationManager.getProperty("path.page.trip.full");
        return page;
    }
}
