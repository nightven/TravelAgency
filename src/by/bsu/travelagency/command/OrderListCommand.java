package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.TripDAO;
import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class OrderListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(OrderListCommand.class);
    private static final String PARAM_NAME_ID_USER = "iduser";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        HttpSession session = request.getSession();
        if (session.getAttribute(PARAM_NAME_ID_USER) != null) {
            Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
            OrderDAO orderDAO = new OrderDAO();
            List<OrderTourInfo> orderTourInfos = orderDAO.findAllUserOrdersByUserId(userId);
            request.setAttribute("orders", orderTourInfos);
            page = ConfigurationManager.getProperty("path.page.orders");
        } else {
            request.setAttribute("errorNotAuthorizedMessage",
                    TravelController.messageManager.getProperty("message.notauthorizederror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}
