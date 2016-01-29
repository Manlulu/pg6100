package helpers;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class StringHelper {

    public String buildStringOfLength(int length) {
        String string = "";
        for(int i = 0; i < length; i++){
            string += 'a';
        }

        return string;
    }
}
