package by.bsu.travelagency.command.factory;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.EmptyCommand;
import by.bsu.travelagency.command.client.CommandEnum;
import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class ActionFactory {

    private final static Logger LOG = Logger.getLogger(ActionFactory.class);

    public ActionCommand defineCommand(HttpServletRequest request, HttpServletResponse response) {
        ActionCommand current = new EmptyCommand();
// извлечение имени команды из запроса
        String action = request.getParameter("command");
        LOG.debug("Action = " + action);
        if (action == null || action.isEmpty()) {
// если команда не задана в текущем запросе
            return current;
        }
// получение объекта, соответствующего команде
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
