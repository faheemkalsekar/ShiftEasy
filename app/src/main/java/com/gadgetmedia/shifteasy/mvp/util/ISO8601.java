package com.gadgetmedia.shifteasy.mvp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601 {

    private static final DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
    private static final DateFormat simpleDateFormat = new SimpleDateFormat("E dd/MM/yy hh:mm a", Locale.getDefault());
    private static final String PLACEHOLDER_TIME = "???";

    /**
     * Private constructor: class cannot be instantiated
     */
    private ISO8601() {
    }

    public static String convertISOToDate(final String iso8601String) {
        try {
            return simpleDateFormat.format(iso8601Format.parse(iso8601String));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return PLACEHOLDER_TIME;
    }

    /**
     * Return an ISO 8601 combined date and time string for current date/time
     *
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    public static String getISO8601StringForCurrentDate() {
        Date now = new Date();
        return getISO8601StringForDate(now);
    }

    /**
     * Return an ISO 8601 combined date and time string for specified date/time
     *
     * @param date Date
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    private static String getISO8601StringForDate(final Date date) {
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format.format(date);
    }
}
