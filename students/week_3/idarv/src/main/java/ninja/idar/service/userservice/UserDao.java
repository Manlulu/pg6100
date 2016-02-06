package ninja.idar.service.userservice;

import ninja.idar.models.User;

import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public interface UserDao {
    void persist(User user, String password);
}

