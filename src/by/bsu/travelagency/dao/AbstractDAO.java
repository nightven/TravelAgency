package by.bsu.travelagency.dao;

/**
 * Created by Михаил on 2/24/2016.
 */

import by.bsu.travelagency.dao.exceptions.DAOException;
import by.bsu.travelagency.entity.Entity;

/**
 * The Class AbstractDAO.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */
public abstract class AbstractDAO <K, T extends Entity> {
    
    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the t
     * @throws DAOException the DAO exception
     */
    public abstract T findEntityById(K id) throws DAOException;
    
    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean delete(K id) throws DAOException;
    
    /**
     * Delete.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean delete(T entity) throws DAOException;
    
    /**
     * Creates the.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean create(T entity) throws DAOException;
    
    /**
     * Update.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean update(T entity) throws DAOException;
}
