package by.bsu.travelagency.command.country;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.CountryServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCountryCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateCountryCommand.class);

    /** The Constant PARAM_NAME_NAME. */
    private static final String PARAM_NAME_NAME = "name";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String name = request.getParameter(PARAM_NAME_NAME);
        CountryServiceImpl countryService = new CountryServiceImpl();

        try{
            if (countryService.checkCreateCountry(name)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateCountryPassMessage",
                        TravelController.messageManager.getProperty("message.createcountryerror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.country");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
