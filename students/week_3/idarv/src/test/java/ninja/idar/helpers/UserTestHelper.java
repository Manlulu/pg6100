package ninja.idar.helpers;

import ninja.idar.models.User;

/**
 * Created by Idar Vassdal on 05.02.2016.
 */
public class UserTestHelper {
    public static final String DEFAULT_USERNAME = "SuperUserName";
    public static final String DEFAULT_TEST_EMAIL = "SuperUser@email.com";
    public static final String DEFAULT_TEST_HASH = "0b7d34e578e8a3bbf5ede7bc3660a88e9c21e5d33004e85e983ef731f464771a";
    public static final String DEFAULT_TEST_SALT = "_!dlaoul3u4fs3s56ao07iifrfmq";
    public static final String DEFAULT_TEST_PASSWORD = "superSecretP4ssw0rd";

    public static User getLegalUser(){
        return new User(DEFAULT_USERNAME, DEFAULT_TEST_EMAIL, DEFAULT_TEST_HASH, DEFAULT_TEST_SALT);
    }
}
