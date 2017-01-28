package by.bsu.travelagency.command.city;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.CityServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCityCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(DeleteCityCommand.class);

    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        CityServiceImpl cityService = new CityServiceImpl();
        try {
            cityService.delete(id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        page = ConfigurationManager.getProperty("path.page.admin.panel");
        return page;
    }

}
