package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.ShoppingDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.dao.VacationDAO;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.logic.OrderLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class OrderShoppingCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(OrderShoppingCommand.class);

    private static final String PARAM_NAME_ID_USER = "iduser";
    private static final String PARAM_NAME_ID_TOUR = "idtour";
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure_date";
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival_date";
    private static final String PARAM_NAME_PRICE = "price";
    private static final String PARAM_NAME_QUANTITY = "quantity";
    private static final String PARAM_NAME_DISCOUNT = "discount";
    private static final String PARAM_NAME_TOUR_TYPE = "tour_type";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        Long userId = Long.parseLong(request.getParameter(PARAM_NAME_ID_USER));
        Long tourId = Long.parseLong(request.getParameter(PARAM_NAME_ID_TOUR));
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        int price = Integer.parseInt(request.getParameter(PARAM_NAME_PRICE));
        int quantity = Integer.parseInt(request.getParameter(PARAM_NAME_QUANTITY));
        double discount = Double.parseDouble(request.getParameter(PARAM_NAME_DISCOUNT));
        String tourType = request.getParameter(PARAM_NAME_TOUR_TYPE);
        int totalPrice = (int) Math.ceil(price*quantity*(1-discount));

        ShoppingDAO shoppingDAO = new ShoppingDAO();
        Shopping shopping = shoppingDAO.findEntityById(tourId);
        request.setAttribute("shopping", shopping);
        if (userId != null) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findEntityById(userId);
            request.setAttribute("userProfile", user);
        }

        UserDAO userDAO = new UserDAO();
        int balance = userDAO.findMoneyByUserId(userId);
        if (balance >= totalPrice) {
            if (OrderLogic.checkOrder(userId, tourId, departureDate, arrivalDate, price, quantity, discount, tourType, totalPrice, balance)) {
                request.setAttribute("orderSuccessMessage",
                        TravelController.messageManager.getProperty("message.ordersuccess"));
                page = ConfigurationManager.getProperty("path.page.shopping.full");
            } else {
                request.setAttribute("errorOrderPassMessage",
                        TravelController.messageManager.getProperty("message.ordererror"));
                page = ConfigurationManager.getProperty("path.page.shopping.full");
            }
        } else {
            request.setAttribute("errorOrderNotEnoughMoneyMessage",
                    TravelController.messageManager.getProperty("message.ordernotenoughmoneyerror"));
            page = ConfigurationManager.getProperty("path.page.shopping.full");
        }

        return page;
    }

}
