package test.dmisb.apps65.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {
    private static SimpleDateFormat resFormat;
    private static SimpleDateFormat resBackFormat;
    private static SimpleDateFormat dateFormat;

    @SuppressLint("SimpleDateFormat")
    public static void initFormats() {
        resFormat = new SimpleDateFormat("yyyy-MM-dd");
        resBackFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    public static SimpleDateFormat getResFormat() {
        return resFormat;
    }

    public static SimpleDateFormat getResBackFormat() {
        return resBackFormat;
    }

    public static String resFormatDate(Date date) {
        return resFormat.format(date);
    }

    public static String dateFormat(Date date) {
        return dateFormat.format(date);
    }
}
