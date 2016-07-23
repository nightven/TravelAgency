package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class VacationFullCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(VacationFullCommand.class);

    private static final String PARAM_NAME_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        VacationDAO vacationDAO = new VacationDAO();
        Vacation vacation = vacationDAO.findEntityById(id);
        request.setAttribute("vacation", vacation);
        page = ConfigurationManager.getProperty("path.page.vacation.full");
        return page;
    }
}
