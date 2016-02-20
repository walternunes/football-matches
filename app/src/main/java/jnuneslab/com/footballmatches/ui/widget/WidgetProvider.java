package jnuneslab.com.footballmatches.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.service.FetchScoreTask;
import jnuneslab.com.footballmatches.ui.activity.MainActivity;

public class WidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);

            // Create intent to start application if clicked on widget menu
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Set adapters
            views.setRemoteAdapter(R.id.widget_scores_list, new Intent(context, WidgetRemoteViewService.class));
            views.setEmptyView(R.id.widget_scores_list, R.id.widget_scores_empty);

            // Update Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);

         // Check if the broadcast was sent by the service and then notify the Widget to be updated
         if (FetchScoreTask.BROADCAST_MATCHES_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_scores_list);
         }
    }
}
