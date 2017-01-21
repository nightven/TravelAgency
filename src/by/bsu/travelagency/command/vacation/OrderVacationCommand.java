package by.bsu.travelagency.command.vacation;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.jdbc.JdbcUserDAO;
import by.bsu.travelagency.dao.jdbc.JdbcVacationDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.logic.OrderLogic;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class OrderVacationCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(OrderVacationCommand.class);

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
        int price = Integer.parseInt(request.getParameter(PARAM_NAME_PRICE));
        int quantity = Integer.parseInt(request.getParameter(PARAM_NAME_QUANTITY));
        double discount = Double.parseDouble(request.getParameter(PARAM_NAME_DISCOUNT));
        String tourType = request.getParameter(PARAM_NAME_TOUR_TYPE);
        int totalPrice = (int) Math.ceil(price*quantity*(1-discount));

        JdbcVacationDAO vacationDAO = new JdbcVacationDAO();
        Vacation vacation = null;
        try {
            vacation = vacationDAO.findEntityById(tourId);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        request.setAttribute("vacation", vacation);
        if (userId != null) {
            JdbcUserDAO userDAO = new JdbcUserDAO();
            User user = null;
            try {
                user = userDAO.findEntityById(userId);
            } catch (DAOException e) {
                throw new CommandException(e);
            }
            request.setAttribute("userProfile", user);
        }

        JdbcUserDAO userDAO = new JdbcUserDAO();
        int balance = 0;
        try {
            balance = userDAO.findMoneyByUserId(userId);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        if (balance >= totalPrice) {
            try {
                if (OrderLogic.checkOrder(userId, tourId, departureDate, arrivalDate, quantity, tourType, totalPrice, balance)) {
                    request.setAttribute("orderSuccessMessage",
                            TravelController.messageManager.getProperty("message.ordersuccess"));
                    page = ConfigurationManager.getProperty("path.page.vacation.full");
                } else {
                    request.setAttribute("errorOrderPassMessage",
                            TravelController.messageManager.getProperty("message.ordererror"));
                    page = ConfigurationManager.getProperty("path.page.vacation.full");
                }
            } catch (BusinessLogicException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute("errorOrderNotEnoughMoneyMessage",
                    TravelController.messageManager.getProperty("message.ordernotenoughmoneyerror"));
            page = ConfigurationManager.getProperty("path.page.vacation.full");
        }

        return page;
    }

}
