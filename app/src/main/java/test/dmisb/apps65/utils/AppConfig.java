package test.dmisb.apps65.utils;

import okhttp3.logging.HttpLoggingInterceptor;
import test.dmisb.apps65.BuildConfig;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class AppConfig {
    // Network setting
    public static final String BASE_URL = "http://gitlab.65apps.com/";
    public static final int MAX_CONNECTION_TIMEOUT = 10000;
    public static final int MAX_READ_TIMEOUT = 10000;
    public static final int MAX_WRITE_TIMEOUT = 5000;

    // Log Setting
    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static final boolean DB_LOGGING_ENABLED = DEBUG;
    public static final HttpLoggingInterceptor.Level HTTP_LOG_LEVEL = DEBUG ? BODY : NONE;
}
