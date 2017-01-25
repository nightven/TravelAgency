package by.bsu.travelagency.command.shopping;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class ShoppingListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ShoppingListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<Shopping> shoppings = null;
        try {
            shoppings = shoppingService.findAllShoppingsAfterNow(new java.sql.Date(nowDate.getTime()));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shoppings", shoppings);
        page = ConfigurationManager.getProperty("path.page.shopping.list");
        return page;
    }
}
