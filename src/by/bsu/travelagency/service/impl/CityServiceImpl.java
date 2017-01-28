package by.bsu.travelagency.service.impl;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.service.CityService;
import by.bsu.travelagency.service.exception.ServiceException;
import by.bsu.travelagency.util.Validator;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The Class CityServiceImpl.
 */
public class CityServiceImpl implements CityService {

    private final static Logger LOG = Logger.getLogger(CityServiceImpl.class);

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
                city.setId(CITY_ID_FOR_INSERT);
                city.setName(enterName);
                Country country = new Country();
                country.setId(Long.parseLong(enterCountryId));
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
                city.setId(Long.parseLong(enterId));
                city.setName(enterName);
                Country country = new Country();
                country.setId(Long.parseLong(enterCountryId));
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
}
