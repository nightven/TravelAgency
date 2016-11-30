package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ShoppingDeleteAdminListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ShoppingDeleteAdminListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        ShoppingDAO shoppingDAO = new ShoppingDAO();
        List<Shopping> shoppings = null;
        try {
            shoppings = shoppingDAO.findAllShoppings();
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shoppings", shoppings);
        page = ConfigurationManager.getProperty("path.page.admin.delete.list.shopping");
        return page;
    }
}
