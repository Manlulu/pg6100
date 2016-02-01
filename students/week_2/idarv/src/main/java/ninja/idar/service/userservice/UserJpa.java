package ninja.idar.service.userservice;

import ninja.idar.models.User;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
@Stateless
public class UserJpa implements UserDao {
    @Override
    public List getUsers() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public void persist(User user) {
        // Not implemented
    }

    @Override
    public void close() {
        // Not implemented
    }
}
