package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCountryDAO;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import org.apache.log4j.Logger;

public class CountryLogic {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CountryLogic.class);

    /** The Constant COUNTRY_ID_FOR_INSERT. */
    private final static int COUNTRY_ID_FOR_INSERT = 0;

    /**
     * Check create country.
     *
     * @param enterName the enter name
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkCreateCountry(String enterName) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                Country country = new Country();
                country.setIdCountry(COUNTRY_ID_FOR_INSERT);
                country.setNameCountry(enterName);
                JdbcCountryDAO countryDAO = new JdbcCountryDAO();

                if (countryDAO.create(country)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create country.", e);
            }
        }
        return flag;
    }

    /**
     * Check create country.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkEditCountry(String enterId, String enterName) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                Country country = new Country();
                country.setIdCountry(Long.parseLong(enterId));
                country.setNameCountry(enterName);
                JdbcCountryDAO countryDAO = new JdbcCountryDAO();

                if (countryDAO.update(country)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create country.", e);
            }
        }
        return flag;
    }
}
