package com.example.musicplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MusicWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_WIDGET_PLAY_PAUSE = "com.example.musicplayer.ACTION_WIDGET_PLAY_PAUSE";
    public static final String ACTION_WIDGET_NEXT = "com.example.musicplayer.ACTION_WIDGET_NEXT";
    public static final String ACTION_WIDGET_PREVIOUS = "com.example.musicplayer.ACTION_WIDGET_PREVIOUS";
    public static final String ACTION_WIDGET_UPDATE = "com.example.musicplayer.ACTION_WIDGET_UPDATE";
    public static final String EXTRA_IS_PLAYING = "com.example.musicplayer.EXTRA_IS_PLAYING";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            Intent playPauseIntent = new Intent(context, MusicService.class).setAction(MusicService.ACTION_PLAY_PAUSE);
            PendingIntent playPausePendingIntent = PendingIntent.getService(context, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.btn_play_pause, playPausePendingIntent);

            Intent nextIntent = new Intent(context, MusicService.class).setAction(MusicService.ACTION_NEXT);
            PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.btn_next, nextPendingIntent);

            Intent previousIntent = new Intent(context, MusicService.class).setAction(MusicService.ACTION_PREVIOUS);
            PendingIntent previousPendingIntent = PendingIntent.getService(context, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.btn_previous, previousPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_WIDGET_PLAY_PAUSE:
                    Intent serviceIntent = new Intent(context, MusicService.class);
                    serviceIntent.setAction(MusicService.ACTION_PLAY_PAUSE);
                    context.startService(serviceIntent);
                    break;
            }
        }
    }
}
