package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.logic.CreateShoppingLogic;
import by.bsu.travelagency.logic.CreateVacationLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateShoppingCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateShoppingCommand.class);

    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SUMMARY = "summary";
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure-date";
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival-date";
    private static final String PARAM_NAME_DESTINATION_COUNTRY = "destination-country";
    private static final String PARAM_NAME_DESTINATION_CITY = "destination-city";
    private static final String PARAM_NAME_SHOPS = "shops";
    private static final String PARAM_NAME_LAST_MINUTE = "last-minute";
    private static final String PARAM_NAME_PRICE = "price";
    private static final String PARAM_NAME_TRANSPORT = "transport";
    private static final String PARAM_NAME_SERVICES = "services";
    private static final String PARAM_NAME_DESCRIPTION = "description";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        String name = request.getParameter(PARAM_NAME_NAME);
        String summary = request.getParameter(PARAM_NAME_SUMMARY);
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        String destinationCountry = request.getParameter(PARAM_NAME_DESTINATION_COUNTRY);
        String destinationCity = request.getParameter(PARAM_NAME_DESTINATION_CITY);
        String shops = request.getParameter(PARAM_NAME_SHOPS);
        String lastMinute = request.getParameter(PARAM_NAME_LAST_MINUTE);
        LOG.debug("Last minute test: " + request.getParameter(PARAM_NAME_LAST_MINUTE));
        String price = request.getParameter(PARAM_NAME_PRICE);
        String transport = request.getParameter(PARAM_NAME_TRANSPORT);
        String services = request.getParameter(PARAM_NAME_SERVICES);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);

        String SAVE_DIR = "images" + File.separator + "shoppings";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;

        LOG.debug("Save Path = " + savePath);
        Part filePart = null;
        try {
            filePart = request.getPart("img");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        if (CreateShoppingLogic.checkCreateShopping(name, summary, departureDate, arrivalDate, destinationCountry, destinationCity, shops, lastMinute, price, transport, services, description, filePart, savePath)) {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        else {
            request.setAttribute("errorCreateShoppingPassMessage",
                    TravelController.messageManager.getProperty("message.createshoppingerror"));
            page = ConfigurationManager.getProperty("path.page.admin.create.shopping");
        }

        return page;
    }

}