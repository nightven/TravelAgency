package by.bsu.travelagency.command.shopping;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        Shopping shopping = null;
        List<City> cities = null;
        try {
            shopping = shoppingService.findEntityById(id);
            cities = shoppingService.findAllCities();
        } catch (ServiceException e) {
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
