package by.bsu.travelagency.dao;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Vacation;

import java.sql.Date;
import java.util.List;

public interface VacationDAO extends GenericDAO<Long, Vacation> {
    /**
     * Find all vacations.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findAllVacations() throws DAOException;

    /**
     * Find all vacations after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findAllVacationsAfterNow(Date nowDate) throws DAOException;

    /**
     * Find all sort vacations after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findAllSortVacationsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException;

    /**
     * Find vacations by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findVacationsByNameAfterNow(Date nowDate, String name) throws DAOException;

    /**
     * Find vacations by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findVacationsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException;

    /**
     * Find vacations by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findVacationsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException;

    /**
     * Find vacations by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findVacationsByPriceAfterNow(Date nowDate, double price) throws DAOException;

    /**
     * Find vacations by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> findVacationsByTransportAfterNow(Date nowDate, String transport) throws DAOException;

    /**
     * Find last vacation id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    Long findLastVacationId() throws DAOException;

    /**
     * Find path image vacation by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    String findPathImageVacationById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean create(Vacation vacation) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean update(Vacation vacation) throws DAOException;

    /**
     * Select last vacations.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Vacation> selectLastVacations(Date nowDate) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    Vacation findEntityById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    boolean delete(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean delete(Vacation entity);

}
