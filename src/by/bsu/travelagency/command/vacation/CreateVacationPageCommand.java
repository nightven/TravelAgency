package by.bsu.travelagency.command.vacation;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateVacationPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateVacationPageCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<City> cities = null;
        try {
            cities = vacationService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.create.vacation");
        return page;
    }

}
