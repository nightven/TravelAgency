package by.bsu.travelagency.dao;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Trip;

import java.sql.Date;
import java.util.List;

public interface TripDAO extends GenericDAO<Long, Trip> {
    /**
     * Find all trips.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findAllTrips() throws DAOException;

    /**
     * Find all trips after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findAllTripsAfterNow(Date nowDate) throws DAOException;

    /**
     * Find all sort trips after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findAllSortTripsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException;

    /**
     * Find trips by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findTripsByNameAfterNow(Date nowDate, String name) throws DAOException;

    /**
     * Find trips by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findTripsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException;

    /**
     * Find trips by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findTripsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException;

    /**
     * Find trips by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findTripsByPriceAfterNow(Date nowDate, double price) throws DAOException;

    /**
     * Find trips by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> findTripsByTransportAfterNow(Date nowDate, String transport) throws DAOException;

    /**
     * Find last trip id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    Long findLastTripId() throws DAOException;

    /**
     * Find path image trip by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    String findPathImageTripById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean create(Trip trip) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean update(Trip trip) throws DAOException;

    /**
     * Select last trips.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Trip> selectLastTrips(Date nowDate) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    Trip findEntityById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    boolean delete(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean delete(Trip entity);
}
