package by.bsu.travelagency.command.shopping;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateShoppingPageCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CreateShoppingPageCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<City> cities = null;
        try {
            cities = shoppingService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.create.shopping");
        return page;
    }

}
