package by.bsu.travelagency.command.trip;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.TripServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditTripPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditTripPageCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        TripServiceImpl tripService = new TripServiceImpl();
        Trip trip = null;
        List<City> cities = null;
        try {
            trip = tripService.findEntityById(id);
            cities = tripService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == trip.getId()) {
            request.setAttribute("trip", trip);
            request.setAttribute("cities", cities);
            request.setAttribute("cities_size", trip.getCities().size());
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.trip");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }
}
