package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
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
        ShoppingDAO shoppingDAO = new ShoppingDAO();
        List<Shopping> shoppings = null;
        try {
            shoppings = shoppingDAO.findAllShoppingsAfterNow(new java.sql.Date(nowDate.getTime()));
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shoppings", shoppings);
        page = ConfigurationManager.getProperty("path.page.shopping.list");
        return page;
    }
}
