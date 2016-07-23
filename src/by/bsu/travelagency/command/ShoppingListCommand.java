package by.bsu.travelagency.command;

import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ShoppingListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(ShoppingListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        ShoppingDAO shoppingDAO = new ShoppingDAO();
        List<Shopping> shoppings = shoppingDAO.findAllShoppings();
        request.setAttribute("shoppings", shoppings);
        page = ConfigurationManager.getProperty("path.page.shopping.list");
        return page;
    }
}
