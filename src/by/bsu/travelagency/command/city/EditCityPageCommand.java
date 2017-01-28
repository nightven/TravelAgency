package by.bsu.travelagency.command.city;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.CityServiceImpl;
import by.bsu.travelagency.service.impl.CountryServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditCityPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditCityPageCommand.class);

    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        CityServiceImpl cityService = new CityServiceImpl();
        CountryServiceImpl countryService = new CountryServiceImpl();
        City city = null;
        List<Country> countries = null;
        try {
            city = cityService.findEntityById(id);
            countries = countryService.findAllCountries();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == city.getId()) {
            request.setAttribute("city", city);
            request.setAttribute("countries", countries);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.city");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }

}
