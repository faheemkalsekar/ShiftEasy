package com.gadgetmedia.shifteasy.mvp.util;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    private static final String PLACEHOLDER_TIME = "???";

    /**
     * Private constructor: class cannot be instantiated
     */
    private DateTimeUtil() {
    }

    private static String formatISOString(final Date date) {

        final Format simpleDateFormat = new SimpleDateFormat("E dd/MM/yy hh:mm a", Locale.getDefault());
        return simpleDateFormat.format(date);

    }

    public static String parseDate(final String time) {
        try {
            final Date date = ISO8601Utils.parse(time, new ParsePosition(0));
            return formatISOString(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return PLACEHOLDER_TIME;
    }
}
