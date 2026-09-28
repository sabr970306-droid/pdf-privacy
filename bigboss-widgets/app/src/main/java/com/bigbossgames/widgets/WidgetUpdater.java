package com.bigbossgames.widgets;

import android.app.*;
import android.app.usage.*;
import android.appwidget.*;
import android.content.*;
import android.graphics.*;
import android.net.Uri;
import android.os.BatteryManager;
import android.view.View;
import android.widget.RemoteViews;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public final class WidgetUpdater{
    private WidgetUpdater(){}

    private static final int[] BG={
        R.drawable.bg_p0,R.drawable.bg_p1,R.drawable.bg_p2,R.drawable.bg_p3,R.drawable.bg_p4,
        R.drawable.bg_p5,R.drawable.bg_p6,R.drawable.bg_p7,R.drawable.bg_p8,R.drawable.bg_p9
    };
    private static final int[] FG={
        0xffffffff,0xffe2ebff,0xffe7d8ff,0xff69ff96,0xffffdccd,
        0xff1e1d1c,0xffdbf9ff,0xffffead2,0xfff6f7fa,0xfff4deff
    };

    public static void copyPending(Context c,int id){
        android.content.SharedPreferences p=c.getSharedPreferences("widgets",Context.MODE_PRIVATE);
        p.edit()
          .putString(k(id,"type"),p.getString("pending_type","Clock"))
          .putString(k(id,"style"),p.getString("pending_style","Simple1"))
          .putInt(k(id,"palette"),p.getInt("pending_palette",0))
          .putInt(k(id,"size"),p.getInt("pending_size",1))
          .putBoolean(k(id,"24h"),p.getBoolean("pending_24h",true))
          .putString(k(id,"photo"),p.getString("pending_photo",""))
          .putString(k(id,"city"),p.getString("pending_city","Almaty"))
          .apply();
    }

    static String k(int id,String suffix){return id+"_"+suffix;}

    public static void refreshAll(Context c){
        AppWidgetManager m=AppWidgetManager.getInstance(c);
        int[] ids=m.getAppWidgetIds(new ComponentName(c,GenericWidgetProvider.class));
        for(int id:ids)update(c,m,id);
    }

    public static void update(Context c,AppWidgetManager m,int id){
        android.content.SharedPreferences p=c.getSharedPreferences("widgets",Context.MODE_PRIVATE);
        String type=p.getString(k(id,"type"),"Clock");
        String style=p.getString(k(id,"style"),"Simple1");
        int pal=p.getInt(k(id,"palette"),0);if(pal<0||pal>=BG.length)pal=0;
        int size=p.getInt(k(id,"size"),1);
        boolean use24=p.getBoolean(k(id,"24h"),true);
        String photo=p.getString(k(id,"photo"),"");
        String city=p.getString(k(id,"city"),"Almaty");

        RemoteViews v=new RemoteViews(c.getPackageName(),R.layout.widget_generic);
        v.setInt(R.id.widget_root,"setBackgroundResource",BG[pal]);
        int fg=FG[pal];
        v.setTextColor(R.id.widget_label,fg);v.setTextColor(R.id.widget_time,fg);v.setTextColor(R.id.widget_primary,fg);v.setTextColor(R.id.widget_secondary,fg);v.setTextColor(R.id.widget_tertiary,fg);
        v.setFloat(R.id.widget_label,"setAlpha",0.70f);v.setFloat(R.id.widget_secondary,"setAlpha",0.78f);v.setFloat(R.id.widget_tertiary,"setAlpha",0.58f);
        v.setCharSequence(R.id.widget_time,"setFormat24Hour","HH:mm");v.setCharSequence(R.id.widget_time,"setFormat12Hour",use24?"HH:mm":"h:mm a");

        v.setViewVisibility(R.id.widget_photo,View.GONE);
        v.setViewVisibility(R.id.widget_time,View.VISIBLE);
        v.setViewVisibility(R.id.widget_primary,View.VISIBLE);
        v.setViewVisibility(R.id.widget_secondary,View.VISIBLE);
        v.setViewVisibility(R.id.widget_tertiary,View.VISIBLE);
        v.setTextViewText(R.id.widget_label,style.toUpperCase(Locale.ROOT));
        v.setTextViewTextSize(R.id.widget_time,android.util.TypedValue.COMPLEX_UNIT_SP,size==0?30:(size==1?40:48));
        v.setTextViewTextSize(R.id.widget_primary,android.util.TypedValue.COMPLEX_UNIT_SP,size==0?20:(size==1?28:34));

        String date=new SimpleDateFormat("EEE • d MMM",Locale.getDefault()).format(new Date()).toUpperCase();
        String fullDate=new SimpleDateFormat("EEEE • d MMMM yyyy",Locale.getDefault()).format(new Date()).toUpperCase();
        int battery=battery(c);

        if("Clock".equals(type)){
            v.setTextViewText(R.id.widget_primary,date);v.setTextViewText(R.id.widget_secondary,"BIGBOSS WIDGETS");v.setTextViewText(R.id.widget_tertiary,"");
        }else if("WeatherClock".equals(type)){
            v.setTextViewText(R.id.widget_primary,"23°  •  "+city.toUpperCase());v.setTextViewText(R.id.widget_secondary,date);v.setTextViewText(R.id.widget_tertiary,"WEATHER CLOCK");
        }else if("Weather".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);v.setTextViewText(R.id.widget_primary,"23°");v.setTextViewText(R.id.widget_secondary,city.toUpperCase()+"  •  WEATHER");v.setTextViewText(R.id.widget_tertiary,fullDate);
        }else if("Battery".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);v.setTextViewText(R.id.widget_primary,battery+"%");v.setTextViewText(R.id.widget_secondary,"BATTERY");v.setTextViewText(R.id.widget_tertiary,fullDate);
        }else if("XPanel".equals(type)){
            v.setTextViewText(R.id.widget_primary,"BATTERY "+battery+"%");v.setTextViewText(R.id.widget_secondary,date);v.setTextViewText(R.id.widget_tertiary,"XPANEL");
        }else if("Calendar".equals(type)||"Date".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);
            v.setTextViewText(R.id.widget_primary,new SimpleDateFormat("dd",Locale.getDefault()).format(new Date()));
            v.setTextViewText(R.id.widget_secondary,new SimpleDateFormat("MMMM yyyy",Locale.getDefault()).format(new Date()).toUpperCase());
            v.setTextViewText(R.id.widget_tertiary,new SimpleDateFormat("EEEE",Locale.getDefault()).format(new Date()).toUpperCase());
        }else if("Photo".equals(type)){
            v.setViewVisibility(R.id.widget_label,View.GONE);v.setViewVisibility(R.id.widget_secondary,View.GONE);v.setViewVisibility(R.id.widget_tertiary,View.GONE);
            if(photo!=null&&!photo.isEmpty()){
                Bitmap bm=loadBitmap(c,photo);
                if(bm!=null){v.setImageViewBitmap(R.id.widget_photo,bm);v.setViewVisibility(R.id.widget_photo,View.VISIBLE);}
                v.setTextViewText(R.id.widget_primary,"");
            }else{
                v.setViewVisibility(R.id.widget_time,View.GONE);v.setTextViewText(R.id.widget_primary,"PHOTO");v.setViewVisibility(R.id.widget_secondary,View.VISIBLE);v.setTextViewText(R.id.widget_secondary,"Open BigBoss Widgets to choose a photo");
            }
        }else if("Gif".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);v.setTextViewText(R.id.widget_primary,"GIF");v.setTextViewText(R.id.widget_secondary,"MOTION WIDGET");v.setTextViewText(R.id.widget_tertiary,style);
        }else if("ScreenTime".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);String usage=screenTime(c);v.setTextViewText(R.id.widget_primary,usage);v.setTextViewText(R.id.widget_secondary,"SCREEN TIME");v.setTextViewText(R.id.widget_tertiary,"TODAY");
        }else if("Music".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);v.setTextViewText(R.id.widget_primary,"♫");v.setTextViewText(R.id.widget_secondary,"MUSIC");v.setTextViewText(R.id.widget_tertiary,"Tap to open BigBoss Widgets");
        }else if("Distance".equals(type)){
            v.setViewVisibility(R.id.widget_time,View.GONE);v.setTextViewText(R.id.widget_primary,"0.0 KM");v.setTextViewText(R.id.widget_secondary,"DISTANCE");v.setTextViewText(R.id.widget_tertiary,"TODAY");
        }

        Intent open=new Intent(c,MainActivity.class);open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi=PendingIntent.getActivity(c,id,open,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        v.setOnClickPendingIntent(R.id.widget_root,pi);
        m.updateAppWidget(id,v);
    }

    private static int battery(Context c){
        BatteryManager b=(BatteryManager)c.getSystemService(Context.BATTERY_SERVICE);
        int x=b==null?-1:b.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);return x<0?0:x;
    }

    private static Bitmap loadBitmap(Context c,String uri){
        try(InputStream in=c.getContentResolver().openInputStream(Uri.parse(uri))){
            Bitmap bm=BitmapFactory.decodeStream(in);
            if(bm==null)return null;
            int max=900;if(bm.getWidth()>max){int h=(int)(bm.getHeight()*(max/(float)bm.getWidth()));Bitmap scaled=Bitmap.createScaledBitmap(bm,max,h,true);if(scaled!=bm)bm.recycle();return scaled;}
            return bm;
        }catch(Exception e){return null;}
    }

    private static String screenTime(Context c){
        try{
            UsageStatsManager u=(UsageStatsManager)c.getSystemService(Context.USAGE_STATS_SERVICE);
            Calendar cal=Calendar.getInstance();cal.set(Calendar.HOUR_OF_DAY,0);cal.set(Calendar.MINUTE,0);cal.set(Calendar.SECOND,0);cal.set(Calendar.MILLISECOND,0);
            long now=System.currentTimeMillis(),sum=0;
            java.util.List<UsageStats> list=u.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,cal.getTimeInMillis(),now);
            if(list==null||list.isEmpty())return "ALLOW ACCESS";
            for(UsageStats s:list)sum+=s.getTotalTimeInForeground();
            long min=sum/60000;return (min/60)+"h "+(min%60)+"m";
        }catch(Exception e){return "ALLOW ACCESS";}
    }
}