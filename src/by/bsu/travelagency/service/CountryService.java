package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.service.exception.ServiceException;

import java.util.List;

/**
 * The Interface CountryService.
 */
public interface CountryService {

    /**
     * Find all countries.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Country> findAllCountries() throws ServiceException;

    /**
     * Check create country.
     *
     * @param enterName the enter name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkCreateCountry(String enterName) throws ServiceException;

    /**
     * Check edit country.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditCountry(String enterId, String enterName) throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the country
     * @throws ServiceException the service exception
     */
    Country findEntityById(Long id) throws ServiceException;
}
