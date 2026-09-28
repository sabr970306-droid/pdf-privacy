package com.bigbossgames.widgets;
import android.app.*; import android.appwidget.*; import android.content.*; import android.os.BatteryManager; import android.widget.RemoteViews;
public final class WidgetUpdater{
 private WidgetUpdater(){}
 public static void update(Context c,AppWidgetManager m,int[] ids,int layout){
  for(int id:ids){
   RemoteViews v=new RemoteViews(c.getPackageName(),layout);
   if(layout==R.layout.widget_neon_lime || layout==R.layout.widget_clock_battery){
    BatteryManager b=(BatteryManager)c.getSystemService(Context.BATTERY_SERVICE);
    int level=b!=null?b.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY):-1;
    int target=layout==R.layout.widget_neon_lime?R.id.battery_percent:R.id.combo_battery;
    v.setTextViewText(target,level>=0?level+"%":"—");
   }
   Intent in=new Intent(c,MainActivity.class);
   in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
   PendingIntent pi=PendingIntent.getActivity(c,layout,in,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
   v.setOnClickPendingIntent(R.id.widget_root,pi);
   m.updateAppWidget(id,v);
  }
 }
 public static void refreshAll(Context c){
  AppWidgetManager m=AppWidgetManager.getInstance(c);
  refresh(c,m,WidgetProviders.AmoledClock.class,R.layout.widget_amoled);
  refresh(c,m,WidgetProviders.GlassBlue.class,R.layout.widget_glass);
  refresh(c,m,WidgetProviders.NeonLime.class,R.layout.widget_neon_lime);
  refresh(c,m,WidgetProviders.PurpleGlow.class,R.layout.widget_purple);
  refresh(c,m,WidgetProviders.Sunset.class,R.layout.widget_sunset);
  refresh(c,m,WidgetProviders.Minimal.class,R.layout.widget_minimal);
  refresh(c,m,WidgetProviders.Calendar.class,R.layout.widget_calendar);
  refresh(c,m,WidgetProviders.BigClock.class,R.layout.widget_big_clock);
  refresh(c,m,WidgetProviders.Today.class,R.layout.widget_today);
  refresh(c,m,WidgetProviders.ClockBattery.class,R.layout.widget_clock_battery);
 }
 static void refresh(Context c,AppWidgetManager m,Class<?> p,int l){int[] ids=m.getAppWidgetIds(new ComponentName(c,p)); if(ids!=null&&ids.length>0)update(c,m,ids,l);}
}