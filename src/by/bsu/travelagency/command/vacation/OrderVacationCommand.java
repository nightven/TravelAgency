package by.bsu.travelagency.command.vacation;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.service.impl.OrderServiceImpl;
import by.bsu.travelagency.service.impl.UserServiceImpl;
import by.bsu.travelagency.service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderVacationCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(OrderVacationCommand.class);

    private static final String PARAM_NAME_ID_USER = "iduser";
    
    private static final String PARAM_NAME_ID_TOUR = "idtour";
    
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure_date";
    
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival_date";
    
    private static final String PARAM_NAME_PRICE = "price";
    
    private static final String PARAM_NAME_QUANTITY = "quantity";
    
    private static final String PARAM_NAME_DISCOUNT = "discount";
    
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
        VacationServiceImpl vacationService = new VacationServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        try {
            request.setAttribute("vacation", vacationService.findEntityById(tourId));
            if (userId != null) {
                request.setAttribute("userProfile", userService.findEntityById(userId));
            }
            double balance = userService.findMoneyByUserId(userId);
            if (balance >= totalPrice) {
                OrderServiceImpl orderService = new OrderServiceImpl();
                if (orderService.checkOrder(userId, tourId, departureDate, arrivalDate, quantity, tourType, totalPrice, balance)) {
                    request.setAttribute("orderSuccessMessage",
                            TravelController.messageManager.getProperty("message.ordersuccess"));
                    page = ConfigurationManager.getProperty("path.page.vacation.full");
                } else {
                    request.setAttribute("errorOrderPassMessage",
                            TravelController.messageManager.getProperty("message.ordererror"));
                    page = ConfigurationManager.getProperty("path.page.vacation.full");
                }
            } else {
                request.setAttribute("errorOrderNotEnoughMoneyMessage",
                        TravelController.messageManager.getProperty("message.ordernotenoughmoneyerror"));
                page = ConfigurationManager.getProperty("path.page.vacation.full");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
