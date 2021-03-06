package by.bsu.travelagency.command.factory;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.EmptyCommand;
import by.bsu.travelagency.command.client.CommandEnum;
import by.bsu.travelagency.controller.TravelController;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ActionFactory {

    private final static Logger LOG = Logger.getLogger(ActionFactory.class);

    private static final String PARAM_NAME_COMMAND = "command";

    /**
     * Define command.
     *
     * @param request the request
     * @param response the response
     * @return the action command
     */
    public ActionCommand defineCommand(HttpServletRequest request, HttpServletResponse response) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        LOG.debug("Action = " + action);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + TravelController.messageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
