package by.bsu.travelagency.command.trip;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.TripServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTripCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateTripCommand.class);

    /** The Constant PARAM_NAME_NAME. */
    private static final String PARAM_NAME_NAME = "name";
    
    /** The Constant PARAM_NAME_SUMMARY. */
    private static final String PARAM_NAME_SUMMARY = "summary";
    
    /** The Constant PARAM_NAME_DEPARTURE_DATE. */
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure-date";
    
    /** The Constant PARAM_NAME_ARRIVAL_DATE. */
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival-date";
    
    /** The Constant PARAM_NAME_ARRIVAL_ATTRACTIONS. */
    private static final String PARAM_NAME_ARRIVAL_ATTRACTIONS = "attractions";
    
    /** The Constant PARAM_NAME_LAST_MINUTE. */
    private static final String PARAM_NAME_LAST_MINUTE = "last-minute";
    
    /** The Constant PARAM_NAME_PRICE. */
    private static final String PARAM_NAME_PRICE = "price";
    
    /** The Constant PARAM_NAME_TRANSPORT. */
    private static final String PARAM_NAME_TRANSPORT = "transport";
    
    /** The Constant PARAM_NAME_SERVICES. */
    private static final String PARAM_NAME_SERVICES = "services";
    
    /** The Constant PARAM_NAME_DESCRIPTION. */
    private static final String PARAM_NAME_DESCRIPTION = "description";

    /** The Constant PARAM_NAME_COUNT_CITIES. */
    private static final String PARAM_NAME_COUNT_CITIES = "count-cities";

    /** The Constant PARAM_NAME_CITY. */
    private static final String PARAM_NAME_CITY = "city";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;

        String name = request.getParameter(PARAM_NAME_NAME);
        String summary = request.getParameter(PARAM_NAME_SUMMARY);
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        String attractions = request.getParameter(PARAM_NAME_ARRIVAL_ATTRACTIONS);
        String lastMinute = request.getParameter(PARAM_NAME_LAST_MINUTE);
        LOG.debug("Last minute test: " + request.getParameter(PARAM_NAME_LAST_MINUTE));
        String price = request.getParameter(PARAM_NAME_PRICE);
        String transport = request.getParameter(PARAM_NAME_TRANSPORT);
        String services = request.getParameter(PARAM_NAME_SERVICES);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        TripServiceImpl tripService = new TripServiceImpl();
        try {
        int countCities = Integer.parseInt(request.getParameter(PARAM_NAME_COUNT_CITIES));
        ArrayList<City> cities = new ArrayList<>();
        for (int i = 1; i <= countCities; i++) {
            City city = tripService.findCityById(Long.parseLong(request.getParameter(PARAM_NAME_CITY+i)));
            cities.add(city);
        }

        String SAVE_DIR = "images" + File.separator + "trips";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;

        LOG.debug("Save Path = " + savePath);
        Part filePart = null;
            filePart = request.getPart("img");

            if (tripService.checkCreateTrip(name, summary, departureDate, arrivalDate, attractions, lastMinute, price, transport, services, description, filePart, savePath, cities)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateTripPassMessage",
                        TravelController.messageManager.getProperty("message.createtriperror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.trip");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException | ServletException e) {
            throw new CommandException("Failed to get parts from request.",e);
        }

        return page;
    }

}
