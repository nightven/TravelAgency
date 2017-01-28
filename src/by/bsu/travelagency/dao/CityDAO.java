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
}