package by.bsu.travelagency.command.city;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.CityServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCityCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditCityCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";
    
    /** The Constant PARAM_NAME_NAME. */
    private static final String PARAM_NAME_NAME = "name";

    /** The Constant PARAM_NAME_COUNTRY. */
    private static final String PARAM_NAME_COUNTRY = "country";


    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;

        String id = request.getParameter(PARAM_NAME_ID);
        String name = request.getParameter(PARAM_NAME_NAME);
        String countryId = request.getParameter(PARAM_NAME_COUNTRY);
        CityServiceImpl cityService = new CityServiceImpl();

        try {
            if (cityService.checkEditCity(id,name,countryId)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorEditCityPassMessage",
                        TravelController.messageManager.getProperty("message.editcityerror"));
                page = ConfigurationManager.getProperty("path.page.admin.edit.info.city");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
