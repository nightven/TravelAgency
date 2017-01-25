package by.bsu.travelagency.command.city;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.CityServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCityPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateCityPageCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        CityServiceImpl cityService = new CityServiceImpl();
        List<Country> countries = null;
        try {
            countries = cityService.findAllCountries();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
            request.setAttribute("countries", countries);
            page = ConfigurationManager.getProperty("path.page.admin.create.city");
        return page;
    }

}
