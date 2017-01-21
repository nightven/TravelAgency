package by.bsu.travelagency.logic;

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.dao.jdbc.JdbcCityDAO;
import by.bsu.travelagency.entity.City;
import by.bsu.travelagency.entity.Country;
import by.bsu.travelagency.logic.exception.BusinessLogicException;
import org.apache.log4j.Logger;

public class CityLogic {

    /** The Constant LOG. */
    private final static Logger LOG = Logger.getLogger(CityLogic.class);

    /** The Constant CITY_ID_FOR_INSERT. */
    private final static int CITY_ID_FOR_INSERT = 0;

    /**
     * Check create city.
     *
     * @param enterName the enter name
     * @param enterCountryId the enter country id
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkCreateCity(String enterName, String enterCountryId) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                City city = new City();
                city.setIdCity(CITY_ID_FOR_INSERT);
                city.setNameCity(enterName);
                Country country = new Country();
                country.setIdCountry(Long.parseLong(enterCountryId));
                city.setCountry(country);
                JdbcCityDAO cityDAO = new JdbcCityDAO();

                if (cityDAO.create(city)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create city.", e);
            }
        }
        return flag;
    }

    /**
     * Check edit city.
     *
     * @param enterId the enter id
     * @param enterName the enter name
     * @param enterCountryId the enter country id
     * @return true, if successful
     * @throws BusinessLogicException the business logic exception
     */
    public static boolean checkEditCity(String enterId, String enterName, String enterCountryId) throws BusinessLogicException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                City city = new City();
                city.setIdCity(Long.parseLong(enterId));
                city.setNameCity(enterName);
                Country country = new Country();
                country.setIdCountry(Long.parseLong(enterCountryId));
                city.setCountry(country);
                JdbcCityDAO cityDAO = new JdbcCityDAO();

                if (cityDAO.update(city)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new BusinessLogicException("Failed to create city.", e);
            }
        }
        return flag;
    }
}
