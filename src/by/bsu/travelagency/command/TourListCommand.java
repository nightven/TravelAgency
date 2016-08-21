package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class TourListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TourListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Date nowDate = new Date();
        VacationDAO vacationDAO = new VacationDAO();
        List<Vacation> vacations = vacationDAO.selectLastVacations(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("vacations", vacations);
        TripDAO tripDAO = new TripDAO();
        List<Trip> trips = tripDAO.selectLastTrips(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("trips", trips);
        ShoppingDAO shoppingDAO = new ShoppingDAO();
        List<Shopping> shoppings = shoppingDAO.selectLastShoppings(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("shoppings", shoppings);
        page = ConfigurationManager.getProperty("path.page.main");
        return page;
    }
}
