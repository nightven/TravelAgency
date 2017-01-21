package by.bsu.travelagency.dao;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.City;

import java.util.List;

public interface CityDAO extends GenericDAO<Long, City> {
    /**
     * Find all cities.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<City> findAllCities() throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#create(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean create(City city) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#findEntityById(java.lang.Object)
     */
    @Override
    City findEntityById(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(java.lang.Object)
     */
    @Override
    boolean delete(Long id) throws DAOException;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#delete(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean delete(City entity);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.dao.GenericDAO#update(by.bsu.travelagency.entity.Entity)
     */
    @Override
    boolean update(City entity) throws DAOException;
}