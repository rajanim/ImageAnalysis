package sfsu.cs.db;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rajanishivarajmaski1 on 5/7/17.
 */
public class Utils {

    // Return System date-time in SQLite format
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
