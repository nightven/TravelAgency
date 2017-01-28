package by.bsu.travelagency.dao;

/**
 * Created by Михаил on 2/24/2016.
 */

import by.bsu.travelagency.dao.exception.DAOException;
import by.bsu.travelagency.entity.Entity;

/**
 * The Class GenericDAO.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */
public interface GenericDAO<K, T extends Entity> {
    
    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the t
     * @throws DAOException the DAO exception
     */
    T findEntityById(K id) throws DAOException;
    
    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    boolean delete(K id) throws DAOException;
    
    /**
     * Creates the.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    boolean create(T entity) throws DAOException;
    
    /**
     * Update.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    boolean update(T entity) throws DAOException;
}
