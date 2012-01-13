package com.greef.util;

import java.util.Calendar;

/**
 * Calendar utiliy methods. Use only static methods.
 * @author Adrian BER
 */
public class CalendarUtils {

    private CalendarUtils() {}

    /** Returns the difference in days between the two calendars.
     * The time is ignored when comparing.
     * Example: diffInDays(25/09/2004 14:09:14, 30/09/2004 10:23:41) = -5.
     */
    public static int diffInDays(Calendar a, Calendar b) {
        a.set(Calendar.HOUR_OF_DAY, 0);
        a.set(Calendar.MINUTE, 0);
        a.set(Calendar.SECOND, 0);
        a.set(Calendar.MILLISECOND, 0);
        b.set(Calendar.HOUR_OF_DAY, 0);
        b.set(Calendar.MINUTE, 0);
        b.set(Calendar.SECOND, 0);
        b.set(Calendar.MILLISECOND, 0);
        return (int) ((a.getTime().getTime() - b.getTime().getTime())
                /(1000*60*60*24));
    }
}
