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
public class VacationAdminListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(VacationAdminListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        VacationDAO vacationDAO = new VacationDAO();
        List<Vacation> vacations = vacationDAO.findAllVacations();
        request.setAttribute("vacations", vacations);
        page = ConfigurationManager.getProperty("path.page.admin.edit.list.vacation");
        return page;
    }
}
