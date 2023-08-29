package com.shubham.mathexpression.utils;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppCommonMethods {
    private Activity activity;
    public AppCommonMethods(Activity context) {
        activity = context;
    }

    /**
     * This function is used to convert unix time stamp to expected date format
     * @param expectedFormat - date format which we expects
     * getDate("dd-MM-yyyy")"
     */
    public static String getDate(String expectedFormat) {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
        return sdf.format(date);
    }
}
