package ninja.idar.helpers;

import ninja.idar.models.User;

/**
 * Created by Idar Vassdal on 05.02.2016.
 */
public class UserTestHelper {
    public static User getLegalUser(){
        return new User("username", "email@email.com", "password_");
    }
}
