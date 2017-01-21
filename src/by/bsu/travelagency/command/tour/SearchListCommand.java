package by.bsu.travelagency.command.tour;

import by.bsu.travelagency.command.ActionCommand;
import by.bsu.travelagency.command.exception.CommandException;
import by.bsu.travelagency.dao.jdbc.JdbcShoppingDAO;
import by.bsu.travelagency.dao.jdbc.JdbcTripDAO;
import by.bsu.travelagency.dao.jdbc.JdbcVacationDAO;
import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Shopping;
import by.bsu.travelagency.entity.Trip;
import by.bsu.travelagency.entity.Vacation;
import by.bsu.travelagency.resource.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Михаил on 2/16/2016.
 */
public class SearchListCommand implements ActionCommand {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(SearchListCommand.class);
    
    /** The Constant PARAM_NAME_TOUR_TYPE. */
    private static final String PARAM_NAME_TOUR_TYPE = "tour_type";
    
    /** The Constant PARAM_NAME_TOUR_FIELD. */
    private static final String PARAM_NAME_TOUR_FIELD = "tour_field";
    
    /** The Constant PARAM_NAME_SEARCH_TEXT. */
    private static final String PARAM_NAME_SEARCH_TEXT = "search_text";
    
    /** The Constant PARAM_NAME_SEARCH_TEXT_SELECT. */
    private static final String PARAM_NAME_SEARCH_TEXT_SELECT = "search_text_select";

    /** The Constant TOUR_TYPE_VACATION. */
    private static final String TOUR_TYPE_VACATION = "vacation";
    
    /** The Constant TOUR_TYPE_TRIP. */
    private static final String TOUR_TYPE_TRIP = "trip";
    
    /** The Constant TOUR_TYPE_SHOPPING. */
    private static final String TOUR_TYPE_SHOPPING = "shopping";

    /** The Constant TOUR_FIELD_NAME. */
    private static final String TOUR_FIELD_NAME = "name";
    
    /** The Constant TOUR_FIELD_DEPARTURE_DATE. */
    private static final String TOUR_FIELD_DEPARTURE_DATE = "departure_date";
    
    /** The Constant TOUR_FIELD_ARRIVAL_DATE. */
    private static final String TOUR_FIELD_ARRIVAL_DATE = "arrival_date";
    
    /** The Constant TOUR_FIELD_PRICE. */
    private static final String TOUR_FIELD_PRICE = "price";
    
    /** The Constant TOUR_FIELD_TRANSPORT. */
    private static final String TOUR_FIELD_TRANSPORT = "transport";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String tourType = request.getParameter(PARAM_NAME_TOUR_TYPE);
        String tourField = request.getParameter(PARAM_NAME_TOUR_FIELD);
        String searchText = request.getParameter(PARAM_NAME_SEARCH_TEXT);
        String searchTextSelect = request.getParameter(PARAM_NAME_SEARCH_TEXT_SELECT);

        switch (tourType){
            case TOUR_TYPE_VACATION:
                JdbcVacationDAO vacationDAO = new JdbcVacationDAO();
                List<Vacation> vacations = new ArrayList<Vacation>();

                switch (tourField){
                    case TOUR_FIELD_NAME:
                        try {
                            vacations = vacationDAO.findVacationsByNameAfterNow(new java.sql.Date(nowDate.getTime()), searchText);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("vacations", vacations);
                        page = ConfigurationManager.getProperty("path.page.vacation.list");
                        break;
                    case TOUR_FIELD_DEPARTURE_DATE:
                        Date departureDate = null;
                        try {
                            departureDate = format.parse(searchText);
                            vacations = vacationDAO.findVacationsByDepartureDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(departureDate.getTime()));
                            request.setAttribute("vacations", vacations);
                            page = ConfigurationManager.getProperty("path.page.vacation.list");
                        } catch (ParseException e) {
                            throw new CommandException("Failed to parse date.", e);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        break;
                    case TOUR_FIELD_ARRIVAL_DATE:
                        Date arrivalDate = null;
                        try {
                            arrivalDate = format.parse(searchText);
                            vacations = vacationDAO.findVacationsByArrivalDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(arrivalDate.getTime()));
                            request.setAttribute("vacations", vacations);
                            page = ConfigurationManager.getProperty("path.page.vacation.list");
                        } catch (ParseException e) {
                            throw new CommandException("Failed to parse date.", e);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        break;
                    case TOUR_FIELD_PRICE:
                        int price = Integer.parseInt(searchText);
                        try {
                            vacations = vacationDAO.findVacationsByPriceAfterNow(new java.sql.Date(nowDate.getTime()), price);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("vacations", vacations);
                        page = ConfigurationManager.getProperty("path.page.vacation.list");
                        break;
                    case TOUR_FIELD_TRANSPORT:
                        try {
                            vacations = vacationDAO.findVacationsByTransportAfterNow(new java.sql.Date(nowDate.getTime()), searchTextSelect);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("vacations", vacations);
                        page = ConfigurationManager.getProperty("path.page.vacation.list");
                        break;
                }
                break;
            case TOUR_TYPE_TRIP:
                JdbcTripDAO tripDAO = new JdbcTripDAO();
                List<Trip> trips = new ArrayList<Trip>();

                switch (tourField){
                    case TOUR_FIELD_NAME:
                        try {
                            trips = tripDAO.findTripsByNameAfterNow(new java.sql.Date(nowDate.getTime()), searchText);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("trips", trips);
                        page = ConfigurationManager.getProperty("path.page.trip.list");
                        break;
                    case TOUR_FIELD_DEPARTURE_DATE:
                        Date departureDate = null;
                        try {
                            departureDate = format.parse(searchText);
                            trips = tripDAO.findTripsByDepartureDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(departureDate.getTime()));
                            request.setAttribute("trips", trips);
                            page = ConfigurationManager.getProperty("path.page.trip.list");
                        } catch (ParseException e) {
                            throw new CommandException("Failed to parse date.", e);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        break;
                    case TOUR_FIELD_ARRIVAL_DATE:
                        Date arrivalDate = null;
                        try {
                            arrivalDate = format.parse(searchText);
                            trips = tripDAO.findTripsByArrivalDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(arrivalDate.getTime()));
                            request.setAttribute("trips", trips);
                            page = ConfigurationManager.getProperty("path.page.trip.list");
                        } catch (ParseException e) {
                            throw new CommandException("Failed to parse date.", e);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        break;
                    case TOUR_FIELD_PRICE:
                        int price = Integer.parseInt(searchText);
                        try {
                            trips = tripDAO.findTripsByPriceAfterNow(new java.sql.Date(nowDate.getTime()), price);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("trips", trips);
                        page = ConfigurationManager.getProperty("path.page.trip.list");
                        break;
                    case TOUR_FIELD_TRANSPORT:
                        try {
                            trips = tripDAO.findTripsByTransportAfterNow(new java.sql.Date(nowDate.getTime()), searchTextSelect);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("trips", trips);
                        page = ConfigurationManager.getProperty("path.page.trip.list");
                        break;
                }
                break;
            case TOUR_TYPE_SHOPPING:
                JdbcShoppingDAO shoppingDAO = new JdbcShoppingDAO();
                List<Shopping> shoppings = new ArrayList<Shopping>();

                switch (tourField){
                    case TOUR_FIELD_NAME:
                        try {
                            shoppings = shoppingDAO.findShoppingsByNameAfterNow(new java.sql.Date(nowDate.getTime()), searchText);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("shoppings", shoppings);
                        page = ConfigurationManager.getProperty("path.page.shopping.list");
                        break;
                    case TOUR_FIELD_DEPARTURE_DATE:
                        Date departureDate = null;
                        try {
                            departureDate = format.parse(searchText);
                            shoppings = shoppingDAO.findShoppingsByDepartureDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(departureDate.getTime()));
                            request.setAttribute("shoppings", shoppings);
                            page = ConfigurationManager.getProperty("path.page.shopping.list");
                        } catch (ParseException e) {
                            throw new CommandException("Failed to parse date.", e);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        break;
                    case TOUR_FIELD_ARRIVAL_DATE:
                        Date arrivalDate = null;
                        try {
                            arrivalDate = format.parse(searchText);
                            shoppings = shoppingDAO.findShoppingsByArrivalDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(arrivalDate.getTime()));
                            request.setAttribute("shoppings", shoppings);
                            page = ConfigurationManager.getProperty("path.page.shopping.list");
                        } catch (ParseException e) {
                            throw new CommandException("Failed to parse date.", e);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        break;
                    case TOUR_FIELD_PRICE:
                        int price = Integer.parseInt(searchText);
                        try {
                            shoppings = shoppingDAO.findShoppingsByPriceAfterNow(new java.sql.Date(nowDate.getTime()), price);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("shoppings", shoppings);
                        page = ConfigurationManager.getProperty("path.page.shopping.list");
                        break;
                    case TOUR_FIELD_TRANSPORT:
                        try {
                            shoppings = shoppingDAO.findShoppingsByTransportAfterNow(new java.sql.Date(nowDate.getTime()), searchTextSelect);
                        } catch (DAOException e) {
                            throw new CommandException(e);
                        }
                        request.setAttribute("shoppings", shoppings);
                        page = ConfigurationManager.getProperty("path.page.shopping.list");
                        break;
                }
                break;
            default:

                break;
        }

        return page;
    }
}
