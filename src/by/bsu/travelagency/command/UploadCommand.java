package by.bsu.travelagency.command;

import by.bsu.travelagency.controller.TravelController;
import by.bsu.travelagency.dao.UserDAO;
import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.logic.LoginLogic;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class UploadCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(UploadCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ITEMS = "items";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        String SAVE_DIR = "images";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;

        LOG.debug("Save Path = " + savePath);
        Part filePart = null;
        try {
            filePart = request.getPart("fileUpload");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        String fileName = filePart.getSubmittedFileName();
        try {
            filePart.write(savePath + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        page = ConfigurationManager.getProperty("path.page.main");
        return page;
    }

}
