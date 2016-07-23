package by.bsu.travelagency.controller;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.factory.ActionFactory;
import by.bsu.travelagency.pool.ConnectionPool;
import by.bsu.travelagency.resource.ConfigurationManager;
import by.bsu.travelagency.resource.MessageManager;
import by.bsu.travelagency.resource.ResourceManager;
import org.apache.commons.io.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Михаил on 04.01.2016.
 */
@WebServlet(
        urlPatterns = "/travel",
        initParams = @WebInitParam(name = "init_log4j", value = "log4j.xml")
)
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50    // 50MB
)
public class TravelController extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(TravelController.class);
    public static ConnectionPool connectionPool;
    public static ResourceManager resourceManager = ResourceManager.INSTANCE;
    public static MessageManager messageManager = MessageManager.INSTANCE;

    @Override
    public void init() throws ServletException {
        String prefix = getServletContext().getRealPath("/");
        String filename = getInitParameter("init_log4j");
        if (filename != null) {
            new DOMConfigurator().doConfigure(prefix + filename, LogManager.getLoggerRepository());
        }
        try {
            connectionPool = new ConnectionPool(20);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("locale".equals(cookie.getName())){
                request.setAttribute("lang", cookie.getValue());
            }
        }
        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request, response);
        page = command.execute(request, response);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    messageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}