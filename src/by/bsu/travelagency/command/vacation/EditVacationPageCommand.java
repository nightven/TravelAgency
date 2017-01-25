package by.bsu.travelagency.command.vacation;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditVacationPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditVacationPageCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        VacationServiceImpl vacationService = new VacationServiceImpl();
        Vacation vacation = null;
        List<City> cities = null;
        try {
            vacation = vacationService.findEntityById(id);
            cities = vacationService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == vacation.getId()) {
            request.setAttribute("vacation", vacation);
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.vacation");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }

}
