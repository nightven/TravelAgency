package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.logic.CreateVacationLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditVacationPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditVacationPageCommand.class);

    private static final String PARAM_NAME_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        VacationDAO vacationDAO = new VacationDAO();
        Vacation vacation = vacationDAO.findEntityById(id);
        request.setAttribute("vacation", vacation);
        page = ConfigurationManager.getProperty("path.page.admin.edit.info.vacation");
        return page;
    }

}
