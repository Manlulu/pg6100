package ninja.idar.service.userservice;

import ninja.idar.models.User;

import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public interface UserDao {
    List getUsers();

    User update(User user);

    User getUser(int id);

    boolean deleteUser(int id);

    void persist(User user);

    void close();
}

