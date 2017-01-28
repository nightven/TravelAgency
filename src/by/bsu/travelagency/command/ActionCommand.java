package by.bsu.travelagency.command;

import by.bsu.travelagency.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {
    
    /**
     * Execute.
     *
     * @param request the request
     * @param response the response
     * @return the string
     * @throws CommandException the command exception
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
