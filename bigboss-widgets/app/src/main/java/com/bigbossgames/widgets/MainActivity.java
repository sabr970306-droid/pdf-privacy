package com.bigbossgames.widgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private LinearLayout root;
    private int dp(float v){ return Math.round(v*getResources().getDisplayMetrics().density); }

    private TextView t(String s,float sp,int c){
        TextView v=new TextView(this); v.setText(s); v.setTextSize(sp); v.setTextColor(c); return v;
    }

    private GradientDrawable bg(int color,float radius){
        GradientDrawable g=new GradientDrawable(); g.setColor(color); g.setCornerRadius(dp(radius)); return g;
    }

    private GradientDrawable strokeBg(int color,int stroke,float radius){
        GradientDrawable g=bg(color,radius); g.setStroke(dp(1),stroke); return g;
    }

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(10,11,16));
        getWindow().setNavigationBarColor(Color.rgb(10,11,16));

        ScrollView scroll=new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(Color.rgb(10,11,16));

        root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18),dp(18),dp(18),dp(42));
        scroll.addView(root,new ScrollView.LayoutParams(-1,-2));

        header();
        featured();
        categories();

        section("POPULAR");
        widgetCard("AMOLED BLACK","2×1 • Clock + date","Pure black • battery friendly","#0B0C10","#FFFFFF",WidgetProviders.AmoledClock.class,"08:49","SUN 28 SEP");
        widgetCard("GLASS BLUE","4×2 • Clock + date","Soft glass look","#263A5B","#D9E7FF",WidgetProviders.GlassBlue.class,"08:49","SUNDAY • 28 SEPTEMBER");
        widgetCard("NEON LIME","2×2 • Battery","Bright neon battery widget","#071B12","#67FF91",WidgetProviders.NeonLime.class,"74%","BATTERY");
        widgetCard("PURPLE GLOW","4×2 • Clock","Bold purple night style","#271A4E","#D8C9FF",WidgetProviders.PurpleGlow.class,"08:49","BIGBOSS");
        widgetCard("SUNSET","4×2 • Clock + date","Warm gradient inspired style","#5A2436","#FFD6C8",WidgetProviders.Sunset.class,"08:49","28 SEP • SUN");

        section("MINIMAL & DAILY");
        widgetCard("MINIMAL WHITE","4×1 • Clock","Clean light home screen","#F5F5F7","#17171A",WidgetProviders.Minimal.class,"08:49","SUN 28");
        widgetCard("CALENDAR","4×2 • Date","Large day + month","#F2E8DE","#1C1917",WidgetProviders.Calendar.class,"28","SEPTEMBER 2026");
        widgetCard("BIG CLOCK","4×2 • Clock","Huge time, zero clutter","#111217","#FFFFFF",WidgetProviders.BigClock.class,"08:49","ALMATY");
        widgetCard("TODAY","4×2 • Daily","Date + day in one card","#173441","#D7FAFF",WidgetProviders.Today.class,"TODAY","SUNDAY • 28 SEPTEMBER");
        widgetCard("CLOCK + BATTERY","4×2 • Combo","Time and battery together","#171A22","#FFFFFF",WidgetProviders.ClockBattery.class,"08:49","74%  •  BATTERY");

        TextView footer=t("All widgets included • No ads • No subscription",13,Color.rgb(145,151,166));
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams fp=new LinearLayout.LayoutParams(-1,-2); fp.topMargin=dp(16);
        root.addView(footer,fp);

        setContentView(scroll);
    }

    private void header(){
        TextView brand=t("BIGBOSS",13,Color.rgb(129,103,255));
        brand.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        root.addView(brand);

        TextView title=t("Widgets",38,Color.WHITE);
        title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        LinearLayout.LayoutParams tp=new LinearLayout.LayoutParams(-1,-2); tp.topMargin=dp(2);
        root.addView(title,tp);

        TextView sub=t("Make your home screen yours.",18,Color.rgb(177,183,198));
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,-2); sp.topMargin=dp(2); sp.bottomMargin=dp(14);
        root.addView(sub,sp);

        TextView value=t("10 premium widgets  •  No ads  •  Pay once",13,Color.rgb(159,255,194));
        value.setGravity(Gravity.CENTER);
        value.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        value.setPadding(dp(12),dp(10),dp(12),dp(10));
        value.setBackground(strokeBg(Color.rgb(12,47,31),Color.rgb(35,104,64),16));
        root.addView(value,new LinearLayout.LayoutParams(-1,-2));
    }

    private void featured(){
        LinearLayout box=new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(18),dp(18),dp(18),dp(18));
        box.setBackground(strokeBg(Color.rgb(30,38,63),Color.rgb(86,103,149),28));
        LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(-1,dp(210)); bp.topMargin=dp(18);
        root.addView(box,bp);

        TextView tag=t("FEATURED",12,Color.rgb(180,193,255)); tag.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        box.addView(tag);

        TextClock clock=new TextClock(this);
        clock.setFormat24Hour("HH:mm"); clock.setFormat12Hour("h:mm");
        clock.setTextColor(Color.WHITE); clock.setTextSize(52); clock.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2); cp.topMargin=dp(12);
        box.addView(clock,cp);

        TextClock date=new TextClock(this);
        date.setFormat24Hour("EEEE • d MMMM"); date.setFormat12Hour("EEEE • d MMMM");
        date.setTextColor(Color.rgb(212,221,247)); date.setTextSize(16);
        box.addView(date);

        TextView hint=t("GLASS BLUE  •  4×2",13,Color.rgb(158,174,221));
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,-2); hp.topMargin=dp(14);
        box.addView(hint,hp);
    }

    private void categories(){
        HorizontalScrollView hs=new HorizontalScrollView(this); hs.setHorizontalScrollBarEnabled(false);
        LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
        String[] labels={"Popular","Clock","Calendar","Battery","Minimal","Neon","Glass"};
        for(String s:labels){
            TextView chip=t(s,13,Color.rgb(221,224,234));
            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(15),dp(9),dp(15),dp(9));
            chip.setBackground(strokeBg(Color.rgb(23,25,34),Color.rgb(48,52,65),18));
            LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-2,-2); p.rightMargin=dp(8);
            row.addView(chip,p);
        }
        hs.addView(row);
        LinearLayout.LayoutParams hsp=new LinearLayout.LayoutParams(-1,-2); hsp.topMargin=dp(14); hsp.bottomMargin=dp(6);
        root.addView(hs,hsp);
    }

    private void section(String name){
        TextView s=t(name,13,Color.rgb(130,137,154));
        s.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2); p.topMargin=dp(18); p.bottomMargin=dp(8);
        root.addView(s,p);
    }

    private void widgetCard(String title,String size,String desc,String bgHex,String fgHex,Class<?> provider,String big,String small){
        int cardBg=Color.rgb(18,20,27);
        LinearLayout card=new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(12),dp(12),dp(12),dp(14));
        card.setBackground(strokeBg(cardBg,Color.rgb(39,43,55),24));

        LinearLayout preview=new LinearLayout(this);
        preview.setOrientation(LinearLayout.VERTICAL);
        preview.setGravity(Gravity.CENTER_VERTICAL);
        preview.setPadding(dp(18),dp(14),dp(18),dp(14));
        preview.setBackground(bg(Color.parseColor(bgHex),20));

        TextView p1=t(big,34,Color.parseColor(fgHex)); p1.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        preview.addView(p1);
        TextView p2=t(small,13,Color.parseColor(fgHex)); p2.setAlpha(.78f);
        preview.addView(p2);

        card.addView(preview,new LinearLayout.LayoutParams(-1,dp(112)));

        LinearLayout line=new LinearLayout(this); line.setOrientation(LinearLayout.HORIZONTAL); line.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.topMargin=dp(12);
        card.addView(line,lp);

        LinearLayout texts=new LinearLayout(this); texts.setOrientation(LinearLayout.VERTICAL);
        TextView tv=t(title,17,Color.WHITE); tv.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        texts.addView(tv);
        TextView sz=t(size,12,Color.rgb(150,157,175)); texts.addView(sz);
        line.addView(texts,new LinearLayout.LayoutParams(0,-2,1));

        Button add=new Button(this);
        add.setAllCaps(false); add.setText("+ Add"); add.setTextSize(13); add.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        add.setTextColor(Color.WHITE); add.setBackground(bg(Color.rgb(107,82,255),16));
        add.setOnClickListener(v->pin(provider));
        line.addView(add,new LinearLayout.LayoutParams(dp(94),dp(44)));

        TextView dv=t(desc,13,Color.rgb(166,172,188));
        LinearLayout.LayoutParams dlp=new LinearLayout.LayoutParams(-1,-2); dlp.topMargin=dp(7);
        card.addView(dv,dlp);

        LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2); cp.bottomMargin=dp(12);
        root.addView(card,cp);
    }

    private void pin(Class<?> providerClass){
        AppWidgetManager m=AppWidgetManager.getInstance(this);
        ComponentName p=new ComponentName(this,providerClass);
        if(m.isRequestPinAppWidgetSupported()){
            boolean ok=m.requestPinAppWidget(p,null,null);
            if(!ok) Toast.makeText(this,"Открой: Главный экран → Виджеты → BigBoss Widgets",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Зажми пустое место на главном экране → Виджеты → BigBoss Widgets",Toast.LENGTH_LONG).show();
        }
    }

    @Override protected void onResume(){
        super.onResume();
        WidgetUpdater.refreshAll(this);
    }
}
