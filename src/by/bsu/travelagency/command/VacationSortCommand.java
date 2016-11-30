package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class VacationSortCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(VacationSortCommand.class);

    /** The Constant PARAM_NAME_CRITERION. */
    private static final String PARAM_NAME_CRITERION = "criterion";
    
    /** The Constant PARAM_NAME_ORDER. */
    private static final String PARAM_NAME_ORDER = "order";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String criterion = request.getParameter(PARAM_NAME_CRITERION);
        boolean order = Boolean.parseBoolean(request.getParameter(PARAM_NAME_ORDER));
        Date nowDate = new Date();
        VacationDAO vacationDAO = new VacationDAO();
        List<Vacation> vacations = null;
        try {
            vacations = vacationDAO.findAllSortVacationsAfterNow(new java.sql.Date(nowDate.getTime()), criterion, order);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("sortCriterion", criterion);
        request.setAttribute("sortOrder", order);
        request.setAttribute("vacations", vacations);
        page = ConfigurationManager.getProperty("path.page.vacation.list");
        return page;
    }
}
