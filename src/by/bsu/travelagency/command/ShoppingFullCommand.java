package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ShoppingFullCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(ShoppingFullCommand.class);

    private static final String PARAM_NAME_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        ShoppingDAO shoppingDAO = new ShoppingDAO();
        Shopping shopping = shoppingDAO.findEntityById(id);
        request.setAttribute("shopping", shopping);
        page = ConfigurationManager.getProperty("path.page.shopping.full");
        return page;
    }
}
