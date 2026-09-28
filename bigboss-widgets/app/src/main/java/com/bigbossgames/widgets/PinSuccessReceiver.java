package com.bigbossgames.widgets;
import android.appwidget.*;import android.content.*;
public class PinSuccessReceiver extends BroadcastReceiver{
    @Override public void onReceive(Context c,Intent i){
        int id=i.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        if(id!=AppWidgetManager.INVALID_APPWIDGET_ID){
            WidgetUpdater.copyPending(c,id);
            WidgetUpdater.update(c,AppWidgetManager.getInstance(c),id);
        }
    }
}