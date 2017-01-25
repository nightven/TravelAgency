package by.bsu.travelagency.command.order;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class DeleteOrderCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(DeleteOrderCommand.class);

    /** The Constant PARAM_NAME_ID. */
    private static final String PARAM_NAME_ID = "id";
    
    /** The Constant PARAM_NAME_ID_USER. */
    private static final String PARAM_NAME_ID_USER = "iduser";

    /** The Constant ATTR_NAME_ORDERS. */
    private static final String ATTR_NAME_ORDERS = "orders";

    /** The Constant ATTR_NAME_TIME. */
    private static final String ATTR_NAME_TIME = "time";

    /** The Constant ATTR_NAME_UPCOMING. */
    private static final String ATTR_NAME_UPCOMING = "upcoming";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
        Long orderId = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        OrderServiceImpl orderService = new OrderServiceImpl();
        try {
            if (orderService.checkOrderDelete(orderId, userId)) {
                List<OrderTourInfo> orderTourInfos = orderService.findAllUserOrdersByUserIdAfterNow(userId, new java.sql.Date(nowDate.getTime()));
                request.setAttribute(ATTR_NAME_ORDERS, orderTourInfos);
                request.setAttribute(ATTR_NAME_TIME, ATTR_NAME_UPCOMING);
                page = ConfigurationManager.getProperty("path.page.orders");
            } else {
                List<OrderTourInfo> orderTourInfos = orderService.findAllUserOrdersByUserIdAfterNow(userId, new java.sql.Date(nowDate.getTime()));
                request.setAttribute(ATTR_NAME_ORDERS, orderTourInfos);
                request.setAttribute(ATTR_NAME_TIME, ATTR_NAME_UPCOMING);
                request.setAttribute("errorOrderDeleteMessage",
                        TravelController.messageManager.getProperty("message.ordercancelerror"));
                page = ConfigurationManager.getProperty("path.page.orders");
            }
        } catch (ServiceException e){
            throw new CommandException(e);
        }

        return page;
    }

}
