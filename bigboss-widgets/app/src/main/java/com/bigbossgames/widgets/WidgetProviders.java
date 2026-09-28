package com.bigbossgames.widgets;
import android.appwidget.*; import android.content.Context;
public final class WidgetProviders {
 private WidgetProviders(){}
 public static class AmoledClock extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_amoled);}}
 public static class GlassBlue extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_glass);}}
 public static class NeonLime extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_neon_lime);}}
 public static class PurpleGlow extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_purple);}}
 public static class Sunset extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_sunset);}}
 public static class Minimal extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_minimal);}}
 public static class Calendar extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_calendar);}}
 public static class BigClock extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_big_clock);}}
 public static class Today extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_today);}}
 public static class ClockBattery extends AppWidgetProvider{public void onUpdate(Context c,AppWidgetManager m,int[] i){WidgetUpdater.update(c,m,i,R.layout.widget_clock_battery);}}
}