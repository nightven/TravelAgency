package by.bsu.travelagency.service;

import by.bsu.travelagency.entity.User;
import by.bsu.travelagency.entity.UserOrderNumber;
import by.bsu.travelagency.service.exception.ServiceException;

import java.util.List;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    User findEntityById(Long id) throws ServiceException;

    /**
     * Check balance to add.
     *
     * @param userId the user id
     * @param money the money
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkBalanceToAdd(Long userId, double money) throws ServiceException;

    /**
     * Check create user.
     *
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @param enterRole the enter role
     * @param enterDiscount the enter discount
     * @param enterMoney the enter money
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkCreateUser(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * Check edit user.
     *
     * @param enterId the enter id
     * @param enterLogin the enter login
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @param enterRole the enter role
     * @param enterDiscount the enter discount
     * @param enterMoney the enter money
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditUser(Long enterId, String enterLogin, String enterEmail, String enterName,
                          String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException;

    /**
     * Check edit user.
     *
     * @param enterId the enter id
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @param enterRole the enter role
     * @param enterDiscount the enter discount
     * @param enterMoney the enter money
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkEditUser(Long enterId, String enterLogin, String enterPass, String enterEmail, String enterName,
                          String enterSurname, int enterRole, double enterDiscount, double enterMoney) throws ServiceException;

    /**
     * Find user by name.
     *
     * @param name the name
     * @return the user
     * @throws ServiceException the service exception
     */
    User findUserByName(String name) throws ServiceException;

    /**
     * Check login.
     *
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkLogin(String enterLogin, String enterPass) throws ServiceException;

    /**
     * Check register.
     *
     * @param enterLogin the enter login
     * @param enterPass the enter pass
     * @param enterEmail the enter email
     * @param enterName the enter name
     * @param enterSurname the enter surname
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean checkRegister(String enterLogin, String enterPass, String enterEmail, String enterName, String enterSurname) throws ServiceException;

    /**
     * Find all users with order count.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<UserOrderNumber> findAllUsersWithOrderCount() throws ServiceException;

    /**
     * Find money by user id.
     *
     * @param id the id
     * @return the double
     * @throws ServiceException the service exception
     */
    double findMoneyByUserId(Long id) throws ServiceException;

}
