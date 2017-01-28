package by.bsu.travelagency.command.trip;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.TripServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class TripAdminListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TripAdminListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        TripServiceImpl tripService = new TripServiceImpl();
        List<Trip> trips = null;
        try {
            trips = tripService.findAllTripsAfterNow(new java.sql.Date(nowDate.getTime()));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("trips", trips);
        page = ConfigurationManager.getProperty("path.page.admin.edit.list.trip");
        return page;
    }
}
