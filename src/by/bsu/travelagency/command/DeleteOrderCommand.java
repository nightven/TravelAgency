package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exceptions.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.logic.OrderLogic;
import by.bsu.travelagency.logic.exceptions.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class DeleteOrderCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(DeleteOrderCommand.class);

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
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
        Long orderId = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        OrderDAO orderDAO = new OrderDAO();
        try {
            Date departureDate = orderDAO.findDepartureDateById(orderId);
            int totalPrice = orderDAO.findTotalPriceById(orderId);
            if (OrderLogic.checkOrderDelete(orderId, userId, departureDate, totalPrice)) {
                List<OrderTourInfo> orderTourInfos = orderDAO.findAllUserOrdersByUserId(userId);
                request.setAttribute("orders", orderTourInfos);
                page = ConfigurationManager.getProperty("path.page.orders");
            } else {
                List<OrderTourInfo> orderTourInfos = orderDAO.findAllUserOrdersByUserId(userId);
                request.setAttribute("orders", orderTourInfos);
                request.setAttribute("errorOrderDeleteMessage",
                        TravelController.messageManager.getProperty("message.ordercancelerror"));
                page = ConfigurationManager.getProperty("path.page.orders");
            }
        } catch (BusinessLogicException | DAOException e){
            throw new CommandException(e);
        }

        return page;
    }

}
