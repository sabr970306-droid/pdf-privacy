package com.bigbossgames.widgets;
import android.appwidget.*; import android.content.Context;
public final class WidgetProviders {
 private WidgetProviders(){}
 public static class AmoledClock extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_amoled);}}
 public static class ClockDate extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_clock_date);}}
 public static class Calendar extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_calendar);}}
 public static class BatteryNeon extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_battery);}}
 public static class Glass extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_glass);}}
 public static class Minimal extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_minimal);}}
}