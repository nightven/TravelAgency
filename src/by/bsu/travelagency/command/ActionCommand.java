package by.bsu.travelagency.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
