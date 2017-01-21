package by.bsu.travelagency.command.shopping;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcShoppingDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EditShoppingPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(EditShoppingPageCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        JdbcShoppingDAO shoppingDAO = new JdbcShoppingDAO();
        JdbcCityDAO cityDAO = new JdbcCityDAO();
        Shopping shopping = null;
        List<City> cities = null;
        try {
            shopping = shoppingDAO.findEntityById(id);
            cities = cityDAO.findAllCities();
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        if (id == shopping.getId()) {
            request.setAttribute("shopping", shopping);
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.shopping");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }
}
