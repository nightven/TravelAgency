package by.bsu.travelagency.command;

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
public class CreateVacationCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateVacationCommand.class);

    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SUMMARY = "summary";
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure-date";
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival-date";
    private static final String PARAM_NAME_DESTINATION_COUNTRY = "destination-country";
    private static final String PARAM_NAME_DESTINATION_CITY = "destination-city";
    private static final String PARAM_NAME_HOTEL = "hotel";
    private static final String PARAM_NAME_LAST_MINUTE = "last-minute";
    private static final String PARAM_NAME_PRICE = "price";
    private static final String PARAM_NAME_TRANSPORT = "transport";
    private static final String PARAM_NAME_SERVICES = "services";
    private static final String PARAM_NAME_DESCRIPTION = "description";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

//        String name = request.getParameter(PARAM_NAME_LOGIN);
//        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        String SAVE_DIR = "images";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;

        LOG.debug("Save Path = " + savePath);
        Part filePart = null;
        try {
            filePart = request.getPart("fileUpload");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        String fileName = filePart.getSubmittedFileName();
        try {
            filePart.write(savePath + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        page = ConfigurationManager.getProperty("path.page.main");
        return page;
    }

}
