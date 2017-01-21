package by.bsu.travelagency.command.trip;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.logic.EditTripLogic;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditTripCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditTripCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";
    
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


        String id = request.getParameter(PARAM_NAME_ID);
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
        try {
        int countCities = Integer.parseInt(request.getParameter(PARAM_NAME_COUNT_CITIES));
        LOG.debug("countCities = " + countCities);
        ArrayList<City> cities = new ArrayList<>();
        for (int i = 1; i <= countCities; i++) {
            JdbcCityDAO cityDAO = new JdbcCityDAO();
            City city = cityDAO.findEntityById(Long.parseLong(request.getParameter(PARAM_NAME_CITY+i)));
            LOG.debug("City to add - " + city.getNameCity());
            cities.add(city);
        }

            if ("application/octet-stream".equals(request.getPart("img").getContentType())){
                if (EditTripLogic.checkEditTrip(id, name, summary, departureDate, arrivalDate, attractions, lastMinute, price, transport, services, description, cities)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                }
                else {
                    request.setAttribute("errorEditTripPassMessage",
                            TravelController.messageManager.getProperty("message.edittriperror"));
                    page = ConfigurationManager.getProperty("path.page.admin.edit.info.trip");
                }
            }
            else {
                String SAVE_DIR = "images" + File.separator + "trips";
                String appPath = request.getServletContext().getRealPath("");
                String savePath = appPath + SAVE_DIR;

                LOG.debug("Save Path = " + savePath);
                Part filePart = request.getPart("img");

                if (EditTripLogic.checkEditTrip(id, name, summary, departureDate, arrivalDate, attractions, lastMinute, price, transport, services, description, filePart, savePath,cities)) {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        else {
            request.setAttribute("errorEditTripPassMessage",
                    TravelController.messageManager.getProperty("message.edittriperror"));
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.trip");
        }
            }
        } catch (IOException | ServletException e) {
            throw new CommandException("Failed to get parts from request.", e);
        } catch (BusinessLogicException | DAOException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
