package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class TripListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TripListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        TripDAO tripDAO = new TripDAO();
        List<Trip> trips = tripDAO.findAllTrips();
        request.setAttribute("trips", trips);
        page = ConfigurationManager.getProperty("path.page.trip.list");
        return page;
    }
}
