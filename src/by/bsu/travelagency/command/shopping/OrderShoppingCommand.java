package by.bsu.travelagency.command.shopping;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.OrderServiceImpl;
import by.bsu.travelagency.service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderShoppingCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(OrderShoppingCommand.class);

    /** The Constant PARAM_NAME_ID_USER. */
    private static final String PARAM_NAME_ID_USER = "iduser";
    
    /** The Constant PARAM_NAME_ID_TOUR. */
    private static final String PARAM_NAME_ID_TOUR = "idtour";
    
    /** The Constant PARAM_NAME_DEPARTURE_DATE. */
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure_date";
    
    /** The Constant PARAM_NAME_ARRIVAL_DATE. */
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival_date";
    
    /** The Constant PARAM_NAME_PRICE. */
    private static final String PARAM_NAME_PRICE = "price";
    
    /** The Constant PARAM_NAME_QUANTITY. */
    private static final String PARAM_NAME_QUANTITY = "quantity";
    
    /** The Constant PARAM_NAME_DISCOUNT. */
    private static final String PARAM_NAME_DISCOUNT = "discount";
    
    /** The Constant PARAM_NAME_TOUR_TYPE. */
    private static final String PARAM_NAME_TOUR_TYPE = "tour_type";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;

        Long userId = Long.parseLong(request.getParameter(PARAM_NAME_ID_USER));
        Long tourId = Long.parseLong(request.getParameter(PARAM_NAME_ID_TOUR));
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        double price = Double.parseDouble(request.getParameter(PARAM_NAME_PRICE));
        int quantity = Integer.parseInt(request.getParameter(PARAM_NAME_QUANTITY));
        double discount = Double.parseDouble(request.getParameter(PARAM_NAME_DISCOUNT));
        String tourType = request.getParameter(PARAM_NAME_TOUR_TYPE);
        double totalPrice = Math.ceil(price*quantity*(1-discount));

        try {
            ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
            double balance = shoppingService.findMoneyByUserId(userId);
            if (userId != null) {
                request.setAttribute("userProfile", shoppingService.findUserById(userId));
            }
            request.setAttribute("shopping", shoppingService.findEntityById(tourId));
            if (balance >= totalPrice) {
                OrderServiceImpl orderService = new OrderServiceImpl();
                if (orderService.checkOrder(userId, tourId, departureDate, arrivalDate, quantity, tourType, totalPrice, balance)) {
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
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
