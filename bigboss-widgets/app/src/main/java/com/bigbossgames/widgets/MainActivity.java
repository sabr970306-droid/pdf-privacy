package com.bigbossgames.widgets;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    private LinearLayout root, list;
    private String category="All";

    private final int[] BG={
        Color.rgb(10,11,15),Color.rgb(29,40,66),Color.rgb(31,21,57),Color.rgb(7,42,26),
        Color.rgb(86,35,52),Color.rgb(241,235,226),Color.rgb(20,48,60),Color.rgb(40,31,27),
        Color.rgb(20,22,28),Color.rgb(52,36,74)
    };
    private final int[] FG={
        Color.WHITE,Color.rgb(226,235,255),Color.rgb(231,216,255),Color.rgb(105,255,150),
        Color.rgb(255,220,205),Color.rgb(30,29,28),Color.rgb(219,249,255),Color.rgb(255,234,210),
        Color.rgb(246,247,250),Color.rgb(244,222,255)
    };

    int dp(float v){return Math.round(v*getResources().getDisplayMetrics().density);}
    TextView text(String s,float sp,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(sp);v.setTextColor(c);return v;}
    GradientDrawable round(int c,float r){GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));return g;}
    GradientDrawable border(int c,int st,float r){GradientDrawable g=round(c,r);g.setStroke(dp(1),st);return g;}

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(8,9,13));
        getWindow().setNavigationBarColor(Color.rgb(8,9,13));

        ScrollView scroll=new ScrollView(this);
        scroll.setFillViewport(true); scroll.setBackgroundColor(Color.rgb(8,9,13));
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18),dp(18),dp(18),dp(40));
        scroll.addView(root,new ScrollView.LayoutParams(-1,-2));

        header();
        categoryBar();
        list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL);
        root.addView(list,new LinearLayout.LayoutParams(-1,-2));
        rebuild();
        setContentView(scroll);
    }

    private void header(){
        LinearLayout top=new LinearLayout(this); top.setOrientation(LinearLayout.HORIZONTAL); top.setGravity(Gravity.CENTER_VERTICAL);
        TextView logo=text("BIGBOSS",13,Color.rgb(135,107,255)); logo.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        top.addView(logo,new LinearLayout.LayoutParams(0,-2,1));
        TextView count=text("99 STYLES",12,Color.rgb(156,163,181)); count.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        top.addView(count);
        root.addView(top);

        TextView title=text("Widgets",38,Color.WHITE); title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        LinearLayout.LayoutParams tp=new LinearLayout.LayoutParams(-1,-2);tp.topMargin=dp(3);
        root.addView(title,tp);

        TextView sub=text("Старый набор полностью открыт — без рекламы и покупок внутри.",16,Color.rgb(177,183,198));
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,-2);sp.topMargin=dp(2);sp.bottomMargin=dp(14);
        root.addView(sub,sp);

        TextView paid=text("390 ₸ В GOOGLE PLAY  •  ПЛАТИШЬ ОДИН РАЗ  •  ВСЁ ОТКРЫТО",12,Color.rgb(150,255,190));
        paid.setGravity(Gravity.CENTER);paid.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        paid.setPadding(dp(10),dp(11),dp(10),dp(11));
        paid.setBackground(border(Color.rgb(10,48,30),Color.rgb(36,104,64),16));
        root.addView(paid);

        TextView hint=text("Clock • Photo • GIF • Weather • Calendar • Battery • XPanel • Screen Time • Life • DIY",13,Color.rgb(143,150,168));
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,-2);hp.topMargin=dp(12);
        root.addView(hint,hp);
    }

    private void categoryBar(){
        HorizontalScrollView hs=new HorizontalScrollView(this);hs.setHorizontalScrollBarEnabled(false);
        LinearLayout row=new LinearLayout(this);row.setOrientation(LinearLayout.HORIZONTAL);
        for(String c:StyleCatalog.CATEGORIES){
            TextView chip=text(c,13,Color.WHITE);chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(15),dp(9),dp(15),dp(9));
            chip.setBackground(border(Color.rgb(22,24,32),Color.rgb(47,51,65),18));
            chip.setOnClickListener(v->{category=c;rebuild();});
            LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-2,-2);p.rightMargin=dp(8);
            row.addView(chip,p);
        }
        hs.addView(row);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(16);p.bottomMargin=dp(12);
        root.addView(hs,p);
    }

    private void rebuild(){
        list.removeAllViews();
        int count=0;
        for(StyleCatalog.Style s:StyleCatalog.ALL) if(StyleCatalog.inCategory(s,category)) count++;
        TextView head=text(category+"  •  "+count+" styles",13,Color.rgb(128,135,153));
        head.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,-2);hp.bottomMargin=dp(9);
        list.addView(head,hp);

        for(StyleCatalog.Style s:StyleCatalog.ALL){
            if(!StyleCatalog.inCategory(s,category)) continue;
            list.addView(card(s));
        }
    }

    private View card(StyleCatalog.Style s){
        int p=StyleCatalog.palette(s);
        LinearLayout card=new LinearLayout(this);card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(12),dp(12),dp(12),dp(13));
        card.setBackground(border(Color.rgb(17,19,26),Color.rgb(38,42,55),24));

        LinearLayout prev=new LinearLayout(this);prev.setOrientation(LinearLayout.VERTICAL);
        prev.setGravity(Gravity.CENTER_VERTICAL);prev.setPadding(dp(17),dp(13),dp(17),dp(13));
        prev.setBackground(round(BG[p],20));

        String[] pv=preview(s);
        TextView a=text(pv[0],34,FG[p]);a.setTypeface(Typeface.DEFAULT,Typeface.BOLD);prev.addView(a);
        TextView b=text(pv[1],13,FG[p]);b.setAlpha(.78f);prev.addView(b);
        if(pv[2].length()>0){TextView c=text(pv[2],11,FG[p]);c.setAlpha(.58f);LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2);cp.topMargin=dp(3);prev.addView(c,cp);}
        card.addView(prev,new LinearLayout.LayoutParams(-1,dp(116)));

        LinearLayout line=new LinearLayout(this);line.setGravity(Gravity.CENTER_VERTICAL);line.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout texts=new LinearLayout(this);texts.setOrientation(LinearLayout.VERTICAL);
        TextView name=text(StyleCatalog.title(s),17,Color.WHITE);name.setTypeface(Typeface.DEFAULT,Typeface.BOLD);texts.addView(name);
        TextView sub=text(s.type+"  •  "+StyleCatalog.subtitle(s),12,Color.rgb(150,157,175));texts.addView(sub);
        line.addView(texts,new LinearLayout.LayoutParams(0,-2,1));

        Button customize=new Button(this);customize.setAllCaps(false);customize.setText("Настроить");
        customize.setTextColor(Color.WHITE);customize.setTextSize(12);customize.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        customize.setBackground(round(Color.rgb(104,82,255),15));
        customize.setOnClickListener(v->open(s));
        line.addView(customize,new LinearLayout.LayoutParams(dp(108),dp(44)));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.topMargin=dp(11);
        card.addView(line,lp);

        LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2);cp.bottomMargin=dp(11);
        card.setLayoutParams(cp);
        card.setOnClickListener(v->open(s));
        return card;
    }

    private String[] preview(StyleCatalog.Style s){
        String time=new SimpleDateFormat("HH:mm",Locale.getDefault()).format(new Date());
        String date=new SimpleDateFormat("EEE • d MMM",Locale.getDefault()).format(new Date()).toUpperCase();
        int bat=battery();
        if("Battery".equals(s.type)) return new String[]{bat+"%","BATTERY",StyleCatalog.title(s)};
        if("Calendar".equals(s.type)||"Date".equals(s.type)) return new String[]{new SimpleDateFormat("dd",Locale.getDefault()).format(new Date()),new SimpleDateFormat("MMMM yyyy",Locale.getDefault()).format(new Date()).toUpperCase(),new SimpleDateFormat("EEEE",Locale.getDefault()).format(new Date()).toUpperCase()};
        if("XPanel".equals(s.type)) return new String[]{time,"BATTERY "+bat+"%  •  "+date,StyleCatalog.title(s)};
        if("Weather".equals(s.type)) return new String[]{"23°","ALMATY  •  WEATHER",StyleCatalog.title(s)};
        if("WeatherClock".equals(s.type)) return new String[]{time,"23°  •  ALMATY",date};
        if("Photo".equals(s.type)) return new String[]{"PHOTO","YOUR MEMORY",StyleCatalog.title(s)};
        if("Gif".equals(s.type)) return new String[]{"GIF","MOTION WIDGET",StyleCatalog.title(s)};
        if("ScreenTime".equals(s.type)) return new String[]{"2h 18m","SCREEN TIME","TODAY"};
        if("Music".equals(s.type)) return new String[]{"♫","NOW PLAYING",StyleCatalog.title(s)};
        if("Distance".equals(s.type)) return new String[]{"0.0 KM","DISTANCE","TODAY"};
        return new String[]{time,date,StyleCatalog.title(s)};
    }

    private int battery(){
        BatteryManager b=(BatteryManager)getSystemService(BATTERY_SERVICE);
        int v=b==null?-1:b.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return v<0?0:v;
    }

    private void open(StyleCatalog.Style s){
        Intent i=new Intent(this,CustomizeActivity.class);
        i.putExtra("type",s.type);i.putExtra("style",s.name);i.putExtra("palette",StyleCatalog.palette(s));
        startActivity(i);
    }

    @Override protected void onResume(){
        super.onResume();
        WidgetUpdater.refreshAll(this);
    }
}