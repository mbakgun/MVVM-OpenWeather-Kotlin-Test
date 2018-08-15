package com.mbakgun.weatherlogger.utils;

/**
 * Created by burakakgun on 13.08.2018.
 */

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return formatter.format(new Date(Long.parseLong(date) * 1000));
    }

    public static String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}