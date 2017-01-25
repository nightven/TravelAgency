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

public class OrderUpcomingListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(OrderUpcomingListCommand.class);
    
    /** The Constant PARAM_NAME_ID_USER. */
    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        HttpSession session = request.getSession();
        if (session.getAttribute(PARAM_NAME_ID_USER) != null) {
            Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
            OrderServiceImpl orderService = new OrderServiceImpl();
            List<OrderTourInfo> orderTourInfos = null;
            try {
                orderTourInfos = orderService.findAllUserOrdersByUserIdAfterNow(userId, new java.sql.Date(nowDate.getTime()));
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            request.setAttribute("orders", orderTourInfos);
            request.setAttribute("time", "upcoming");
            page = ConfigurationManager.getProperty("path.page.orders");
        } else {
            request.setAttribute("errorNotAuthorizedMessage",
                    TravelController.messageManager.getProperty("message.notauthorizederror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}
