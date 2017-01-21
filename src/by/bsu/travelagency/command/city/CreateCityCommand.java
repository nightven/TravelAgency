package by.bsu.travelagency.command.city;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.logic.CityLogic;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class CreateCityCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateCityCommand.class);

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

        String name = request.getParameter(PARAM_NAME_NAME);
        String countryId = request.getParameter(PARAM_NAME_COUNTRY);
        try{
            if (CityLogic.checkCreateCity(name, countryId)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateCityPassMessage",
                        TravelController.messageManager.getProperty("message.createcityerror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.city");
            }
        } catch (BusinessLogicException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
