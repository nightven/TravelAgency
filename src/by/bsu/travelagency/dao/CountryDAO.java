package by.bsu.travelagency.dao;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Country;

import java.util.List;

public interface CountryDAO extends GenericDAO<Long, Country> {
    /**
     * Find all countries.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Country> findAllCountries() throws DAOException;
}