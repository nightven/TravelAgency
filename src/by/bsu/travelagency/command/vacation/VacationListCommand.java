package by.bsu.travelagency.command.vacation;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class VacationListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(VacationListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<Vacation> vacations = null;
        try {
            vacations = vacationService.findAllVacationsAfterNow(new java.sql.Date(nowDate.getTime()));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("vacations", vacations);
        page = ConfigurationManager.getProperty("path.page.vacation.list");
        return page;
    }
}
