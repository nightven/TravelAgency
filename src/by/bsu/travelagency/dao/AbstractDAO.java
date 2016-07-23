package by.bsu.travelagency.dao;

/**
 * Created by Михаил on 2/24/2016.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import by.bsu.travelagency.entity.Entity;
public abstract class AbstractDAO <K, T extends Entity> {
    public abstract T findEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean delete(T entity);
    public abstract boolean create(T entity);
    public abstract T update(T entity);
}
