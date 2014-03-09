package converters;

import play.data.format.Formatters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by danilo on 08/03/14.
 */
public class Html5CalendarFormatter extends Formatters.SimpleFormatter<Calendar> {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Calendar parse(String value, Locale locale) throws ParseException {
        if (!value.trim().isEmpty()){
            Date date = format.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
        return null;
    }

    @Override
    public String print(Calendar calendar, Locale locale) {
        if (calendar != null){
            return format.format(calendar.getTime());
        }
        return "";
    }
}
