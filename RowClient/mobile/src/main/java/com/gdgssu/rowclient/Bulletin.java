package com.gdgssu.rowclient;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class Bulletin extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String date = pref.getString("date", "2015110118:00");
        String person = pref.getString("person", "여자친구");
        String channel = pref.getString("channel", "SBS");
        String body = pref.getString("body", "런닝맨");

        Calendar tempcal=Calendar.getInstance();
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHH:mm");
        Date startday=sf.parse(date, new ParsePosition(0));

        long startTime=startday.getTime();

        Calendar cal=Calendar.getInstance();
        Date endDate=cal.getTime();
        long endTime=endDate.getTime();

        long mills=startTime-endTime;

        //분으로 변환
        long min=mills/60000;

        StringBuffer diffTime=new StringBuffer();
        diffTime.append(min);

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bulletin);
        views.setTextViewText(R.id.appwidget_text2, diffTime.toString() +"분 후 "+channel+" - " +body+"에서");
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        HttpURLConnection conn = null;
        try {

            URL url = new URL("http://172.30.0.113:8080");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int resCode = conn.getResponseCode();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

