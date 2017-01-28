package by.bsu.travelagency.command.tour;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.ShoppingServiceImpl;
import by.bsu.travelagency.service.impl.TripServiceImpl;
import by.bsu.travelagency.service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class TourListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TourListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        VacationServiceImpl vacationService = new VacationServiceImpl();
        TripServiceImpl tripService = new TripServiceImpl();
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();

        try {
        List<Vacation> vacations = vacationService.selectLastVacations(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("vacations", vacations);

        List<Trip> trips = tripService.selectLastTrips(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("trips", trips);

        List<Shopping> shoppings = shoppingService.selectLastShoppings(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("shoppings", shoppings);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        page = ConfigurationManager.getProperty("path.page.main");
        return page;
    }
}
