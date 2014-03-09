import converters.Html5CalendarFormatter;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;

import java.util.Calendar;

/**
 * Created by danilo on 08/03/14.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        Formatters.register(Calendar.class, new Html5CalendarFormatter());
    }
}
