package com.a3abcarinho.ahmed.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by ahmed on 18/01/18.
 */

public class WidgetProvider extends AppWidgetProvider {
    public static final String ingredients = "ingredients";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int length = appWidgetIds.length;
        for(int i=0; i<length;i++){
            Intent intent = new Intent(context,WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setRemoteAdapter(R.id.widgetContainer,intent);
            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
