package ninja.idar.service.base;

import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public interface BaseDao<T> {
    List getAll();

    T update(T entity);

    T getById(int id);

    void deleteById(int id);

    void deleteByObject(T entity);

    void persist(T entity);

    void close();
}
