package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ShoppingFullCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(ShoppingFullCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";
    
    /** The Constant PARAM_NAME_ID_USER. */
    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        Long iduser = (Long) request.getSession().getAttribute(PARAM_NAME_ID_USER);
        ShoppingDAO shoppingDAO = new ShoppingDAO();
        Shopping shopping = null;
        try {
            shopping = shoppingDAO.findEntityById(id);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shopping", shopping);
        if (iduser != null) {
            UserDAO userDAO = new UserDAO();
            User user = null;
            try {
                user = userDAO.findEntityById(iduser);
            } catch (DAOException e) {
                throw new CommandException(e);
            }
            request.setAttribute("userProfile", user);
        }
        page = ConfigurationManager.getProperty("path.page.shopping.full");
        return page;
    }
}
