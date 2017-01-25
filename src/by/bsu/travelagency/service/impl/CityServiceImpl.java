package by.bsu.travelagency.service.impl;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.dao.jdbc.JdbcCountryDAO;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.util.Validator;
import by.bsu.travelagency.service.CityService;
import by.bsu.travelagency.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CityServiceImpl.
 */
public class CityServiceImpl implements CityService {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CityServiceImpl.class);

    /** The Constant CITY_ID_FOR_INSERT. */
    private final static int CITY_ID_FOR_INSERT = 0;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CityService#findAllCities()
     */
    @Override
    public List<City> findAllCities() throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().findAllCities();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CityService#checkCreateCity(java.lang.String, java.lang.String)
     */
    public boolean checkCreateCity(String enterName, String enterCountryId) throws ServiceException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                City city = new City();
                city.setIdCity(CITY_ID_FOR_INSERT);
                city.setNameCity(enterName);
                Country country = new Country();
                country.setIdCountry(Long.parseLong(enterCountryId));
                city.setCountry(country);

                if (JdbcCityDAO.getInstance().create(city)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create city.", e);
            }
        }
        return flag;
    }


    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CityService#checkEditCity(java.lang.String, java.lang.String, java.lang.String)
     */
    public boolean checkEditCity(String enterId, String enterName, String enterCountryId) throws ServiceException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                City city = new City();
                city.setIdCity(Long.parseLong(enterId));
                city.setNameCity(enterName);
                Country country = new Country();
                country.setIdCountry(Long.parseLong(enterCountryId));
                city.setCountry(country);

                if (JdbcCityDAO.getInstance().update(city)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create city.", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CityService#delete(java.lang.Long)
     */
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CityService#findEntityById(java.lang.Long)
     */
    @Override
    public City findEntityById(Long id) throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CityService#findAllCountries()
     */
    @Override
    public List<Country> findAllCountries() throws ServiceException {
        try {
            return JdbcCountryDAO.getInstance().findAllCountries();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
