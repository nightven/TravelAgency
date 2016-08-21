package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.OrderDAO;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.OrderTourInfo;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.BalanceLogic;
import by.bsu.travelagency.logic.OrderLogic;
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
public class BalanceAddCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(BalanceAddCommand.class);

    private static final String PARAM_NAME_MONEY = "money";
    private static final String PARAM_NAME_ID_USER = "iduser";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
        int moneyToAdd = Integer.parseInt(request.getParameter(PARAM_NAME_MONEY));
        UserDAO userDAO = new UserDAO();
        if (BalanceLogic.checkBalanceToAdd(userId, moneyToAdd)) {
            User user = userDAO.findEntityById(userId);
            request.setAttribute("userProfile", user);
            page = ConfigurationManager.getProperty("path.page.balance");
        } else {
            User user = userDAO.findEntityById(userId);
            request.setAttribute("userProfile", user);
            request.setAttribute("errorBalanceAddMessage",
                    TravelController.messageManager.getProperty("message.balanceadderror"));
            page = ConfigurationManager.getProperty("path.page.balance");
        }

        return page;
    }

}
