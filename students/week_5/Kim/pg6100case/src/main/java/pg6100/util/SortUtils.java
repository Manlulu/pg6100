package pg6100.util;

import java.util.Date;

public class SortUtils {

    public static int sortDate(Date t, Date r) {
        if (t.after(r)) {
            return 1;
        } else if (t.before(r)) {
            return -1;
        } else {
            return 0;
        }
    }

    public static int sort(int i, int y) {
        if (i < y) {
            return 1;
        } else if (i > y) {
            return -1;
        } else {
            return 0;
        }
    }
}
