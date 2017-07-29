package com.bkdev.translation.util;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * TimeUtil.
 *
 * @author BiNC
 */
public final class TimeUtil {
    public static final String FORMAT_YEAR_MONTH = "yyyy 年 M 月";

    private TimeUtil() {
    }

    public static Calendar calendarTrimHour(Calendar calendar) {
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        return calendar;
    }

    public static String getTimeFromCal(Calendar calendar, String dtFormat) {
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat(dtFormat, Locale.getDefault());
        return format.format(date);
    }

    public static int getDayCurrent() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public static long daysBetween(long timeStart, long timeEnd) {
        return TimeUnit.MILLISECONDS.toDays(Math.abs(timeEnd - timeStart)) + 1;
    }

    public static int getDaysYearMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DATE);
    }

    public static int getDayPositionOffset(Calendar calendar) {
        Calendar cd = (Calendar) calendar.clone();
        cd.set(Calendar.DATE, 1);
        return (cd.get(Calendar.DAY_OF_WEEK) + 6) % cd.DAY_OF_WEEK;
    }

    public static boolean isCompareDate(Calendar c1, Calendar c2) {
        return ((c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)));

    }

    @NonNull
    public static Calendar getCalendar(@NonNull String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(date));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException("Illegal date: " + date, e);
        }
    }

    @NonNull
    public static int getNumberMonthBetWeenTwoDays(Calendar c1, Calendar c2) {
        int diffYear = Math.abs(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));
        return diffYear * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
    }
}
