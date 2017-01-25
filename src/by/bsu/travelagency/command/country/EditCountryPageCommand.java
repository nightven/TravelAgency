package by.bsu.travelagency.command.country;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.CountryServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCountryPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditCountryPageCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        CountryServiceImpl countryService = new CountryServiceImpl();
        Country country = null;
        try {
            country = countryService.findEntityById(id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == country.getIdCountry()) {
            request.setAttribute("country", country);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.country");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }

}
