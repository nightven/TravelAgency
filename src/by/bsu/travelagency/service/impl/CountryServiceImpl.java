package by.bsu.travelagency.service.impl;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCountryDAO;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.util.Validator;
import by.bsu.travelagency.service.CountryService;
import by.bsu.travelagency.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryServiceImpl.
 */
public class CountryServiceImpl implements CountryService {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CountryServiceImpl.class);

    /** The Constant COUNTRY_ID_FOR_INSERT. */
    private final static int COUNTRY_ID_FOR_INSERT = 0;

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CountryService#findAllCountries()
     */
    @Override
    public List<Country> findAllCountries() throws ServiceException {
        try {
            return JdbcCountryDAO.getInstance().findAllCountries();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CountryService#checkCreateCountry(java.lang.String)
     */
    public boolean checkCreateCountry(String enterName) throws ServiceException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                Country country = new Country();
                country.setIdCountry(COUNTRY_ID_FOR_INSERT);
                country.setNameCountry(enterName);

                if (JdbcCountryDAO.getInstance().create(country)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create country.", e);
            }
        }
        return flag;
    }


    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CountryService#checkEditCountry(java.lang.String, java.lang.String)
     */
    public boolean checkEditCountry(String enterId, String enterName) throws ServiceException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                Country country = new Country();
                country.setIdCountry(Long.parseLong(enterId));
                country.setNameCountry(enterName);

                if (JdbcCountryDAO.getInstance().update(country)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create country.", e);
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CountryService#delete(java.lang.Long)
     */
    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcCountryDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see by.bsu.travelagency.service.CountryService#findEntityById(java.lang.Long)
     */
    @Override
    public Country findEntityById(Long id) throws ServiceException {
        try {
            return JdbcCountryDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
