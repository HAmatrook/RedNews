package com.hawraa.rednews.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.hawraa.rednews.ArticleDetailsActivity;
import com.hawraa.rednews.R;
import com.hawraa.rednews.UTILs.NetworkTasks;

import static com.hawraa.rednews.MainActivity.EXTRA_SELECTED_ITEM;
import static com.hawraa.rednews.UTILs.NetworkTasks.item;
import static com.hawraa.rednews.WelcomeUI.mRecentNewsList;

/**
 * Implementation of App Widget functionality.
 */
public class NewsWidgetProvider extends AppWidgetProvider {
    public static RemoteViews views;
    public static ComponentName mWidget;
    public static AppWidgetManager mAppWidgetManager;
    private static final String MyOnClick = "On Click";

    static public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
         views = new RemoteViews(context.getPackageName(), R.layout.news_widget);
         mWidget = new ComponentName(context, NewsWidgetProvider.class);
         mAppWidgetManager =appWidgetManager;
         new NetworkTasks(null,context).execute();
//        views.setOnClickPendingIntent(R.id.widget,
//                getPendingSelfIntent(context, MyOnClick));

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//         There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (MyOnClick.equals(intent.getAction())){
            Intent clickintent = new Intent(context, ArticleDetailsActivity.class);
            intent.putExtra(EXTRA_SELECTED_ITEM,item);
            context.startActivity(clickintent);
        }
    }

//    protected  PendingIntent getPendingSelfIntent(Context context, String action) {
//        Intent intent = new Intent(context, getClass());
//        intent.setAction(action);
//        return PendingIntent.getBroadcast(context, 0, intent, 0);
//    }
}


