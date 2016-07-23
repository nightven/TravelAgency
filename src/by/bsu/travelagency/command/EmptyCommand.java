package by.bsu.travelagency.command;

import by.bsu.travelagency.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Михаил on 2/16/2016.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
/* в случае ошибки или прямого обращения к контроллеру
* переадресация на страницу ввода логина */
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
