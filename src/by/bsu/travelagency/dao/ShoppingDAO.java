package by.bsu.travelagency.dao;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Shopping;

import java.sql.Date;
import java.util.List;

public interface ShoppingDAO extends GenericDAO<Long, Shopping> {
    /**
     * Find all shoppings.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findAllShoppings() throws DAOException;

    /**
     * Find all shoppings after now.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws DAOException;

    /**
     * Find all sort shoppings after now.
     *
     * @param nowDate the now date
     * @param criterion the criterion
     * @param order the order
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException;

    /**
     * Find shoppings by name after now.
     *
     * @param nowDate the now date
     * @param name the name
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws DAOException;

    /**
     * Find shoppings by departure date after now.
     *
     * @param nowDate the now date
     * @param departureDate the departure date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException;

    /**
     * Find shoppings by arrival date after now.
     *
     * @param nowDate the now date
     * @param arrivalDate the arrival date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException;

    /**
     * Find shoppings by price after now.
     *
     * @param nowDate the now date
     * @param price the price
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws DAOException;

    /**
     * Find shoppings by transport after now.
     *
     * @param nowDate the now date
     * @param transport the transport
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws DAOException;

    /**
     * Find last shopping id.
     *
     * @return the long
     * @throws DAOException the DAO exception
     */
    Long findLastShoppingId() throws DAOException;

    /**
     * Find path image shopping by id.
     *
     * @param id the id
     * @return the string
     * @throws DAOException the DAO exception
     */
    String findPathImageShoppingById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean create(Shopping shopping) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean update(Shopping shopping) throws DAOException;

    /**
     * Select last shoppings.
     *
     * @param nowDate the now date
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Shopping> selectLastShoppings(Date nowDate) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    Shopping findEntityById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    boolean delete(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean delete(Shopping entity);
}
