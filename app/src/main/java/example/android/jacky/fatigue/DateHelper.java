package example.android.jacky.fatigue;

import java.util.Calendar;

/**
 * Created by Jacky on 15/02/2017.
 */

public class DateHelper {

    /**
     * Generate a ISO 8601 date
     *
     * @return a string representing the date in the ISO 8601 format
     */
    public static String getIsoDate(boolean isWeek, boolean endOfDay) {
        Calendar calendar = Calendar.getInstance();
        if (isWeek){
            calendar.add(Calendar.DAY_OF_MONTH,-6);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(calendar.get(Calendar.YEAR));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.MONTH) + 1));
        buffer.append("-");
        buffer.append(twoDigit(calendar.get(Calendar.DAY_OF_MONTH)));
        buffer.append("T");
        if(endOfDay){
            buffer.append("23:");
            buffer.append("59:");
            buffer.append("59");
            buffer.append(".999");
        }
        else {
            //buffer.append(twoDigit(calendar.get(Calendar.HOUR_OF_DAY)));
            buffer.append("00:");
            //buffer.append(twoDigit(calendar.get(Calendar.MINUTE)));
            buffer.append("00:");
            buffer.append("00");
            //buffer.append(twoDigit(calendar.get(Calendar.SECOND)));
            buffer.append(".000");
            //buffer.append(twoDigit(calendar.get(Calendar.MILLISECOND) / 10));
        }
        buffer.append("Z");
//        int offset = calendar.get(Calendar.ZONE_OFFSET) / (1000 * 60 * 60);
//        if (offset > 0) {
//            buffer.append("+");
//        } else {
//            buffer.append("-");
//        }
//        buffer.append(twoDigit(offset));
//        buffer.append(":00");
        return buffer.toString();
    }

    public static String twoDigit(int i) {
        if (i >= 0 && i < 10) {
            return "0" + String.valueOf(i);
        }
        return String.valueOf(i);
    }

    public long getStartOfDayMillis(){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long millis = c.getTimeInMillis();

        return millis;
    }

    public long getEndOfDayMillis(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        long millis = c.getTimeInMillis();

        return millis;
    }

    public long getWeekStartOfDayMillis(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,-6);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long millis =c.getTimeInMillis();

        return millis;
    }

}
