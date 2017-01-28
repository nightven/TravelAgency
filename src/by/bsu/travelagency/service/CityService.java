package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.service.exception.ServiceException;

import java.util.List;

/**
/**
 * The Interface CityService.
 */
public interface CityService {

    /**
     * Find all cities.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<City> findAllCities() throws ServiceException;

    /**
     * Check create city.
     *
     * @param enterName the enter name
     * @param enterCountryId the enter country id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkCreateCity(String enterName, String enterCountryId) throws ServiceException;

    /**
     * Check edit city.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterCountryId the enter country id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditCity(String enterId, String enterName, String enterCountryId) throws ServiceException;

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
     * @return the city
     * @throws ServiceException the service exception
     */
    City findEntityById(Long id) throws ServiceException;

}
