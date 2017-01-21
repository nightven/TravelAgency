package by.bsu.travelagency.command.trip;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcTripDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
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
        JdbcTripDAO tripDAO = new JdbcTripDAO();
        JdbcCityDAO cityDAO = new JdbcCityDAO();
        Trip trip = null;
        List<City> cities = null;
        try {
            trip = tripDAO.findEntityById(id);
            cities = cityDAO.findAllCities();
        } catch (DAOException e) {
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
