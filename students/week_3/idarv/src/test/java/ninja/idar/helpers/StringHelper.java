package ninja.idar.helpers;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class StringHelper {
    public static final String DEFAULT_TEST_USERNAME = "SuperUserName";
    public static final String DEFAULT_TEST_EMAIL = "SuperUser@email.com";
    public static final String DEFAULT_TEST_PASSWORD = "_!superUser33Password";

    /**
     * Builds a string of n length characters
     * @param n length of string
     * @return String of length n
     */
    public String buildStringOfLength(int n) {
        String s = "";

        for(int i = 0; i < n; i++){
            s += 'a';
        }

        return s;
    }
}
