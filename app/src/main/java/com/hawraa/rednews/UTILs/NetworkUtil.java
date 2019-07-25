package com.hawraa.rednews.UTILs;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class NetworkUtil {

    final static String News_BASE_URL = "https://newsapi.org/v2/top-headlines";
    final static String KEY_API = "apikey";
    final static String API_VALUE = "b0ee514f2e9a48d6a123d37aaad14bd9";
    final static String KEY_LANGUAGE = "language";
    final static String LANGUAGE_VALUE = "en";
    final static String KEY_FROM = "from";
    final static String KEY_TO = "to";

    static Date currentDate = Calendar.getInstance().getTime();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    final static String DATE_VALUE =  dateFormat.format(currentDate);



    private NetworkUtil() { }

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(News_BASE_URL).buildUpon()
                .appendQueryParameter(KEY_API, API_VALUE)
                .appendQueryParameter(KEY_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(KEY_FROM, API_VALUE)
                .appendQueryParameter(KEY_TO, DATE_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
