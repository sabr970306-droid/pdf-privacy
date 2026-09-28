package com.bigbossgames.widgets;
import android.app.Activity; import android.appwidget.AppWidgetManager; import android.content.ComponentName; import android.graphics.Color; import android.graphics.drawable.GradientDrawable; import android.os.Bundle; import android.view.Gravity; import android.view.ViewGroup; import android.widget.*;
public class MainActivity extends Activity {
 int dp(float v){return Math.round(v*getResources().getDisplayMetrics().density);}
 TextView text(String v,float sp,int c){TextView t=new TextView(this);t.setText(v);t.setTextSize(sp);t.setTextColor(c);return t;}
 GradientDrawable rounded(int c,float r){GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));return g;}
 @Override protected void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(Color.rgb(12,14,20));getWindow().setNavigationBarColor(Color.rgb(12,14,20));
  ScrollView s=new ScrollView(this);s.setFillViewport(true);s.setBackgroundColor(Color.rgb(12,14,20));
  LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(20),dp(22),dp(20),dp(36));s.addView(root,new ScrollView.LayoutParams(-1,-2));
  TextView brand=text("BIGBOSS WIDGETS",31,Color.WHITE);brand.setTypeface(null,1);root.addView(brand);
  TextView sub=text("Красивые виджеты для главного экрана",17,Color.rgb(173,179,194));LinearLayout.LayoutParams slp=new LinearLayout.LayoutParams(-1,-2);slp.topMargin=dp(5);slp.bottomMargin=dp(16);root.addView(sub,slp);
  TextView full=text("ПОЛНАЯ ВЕРСИЯ  •  ВСЕ ВИДЖЕТЫ ОТКРЫТЫ",13,Color.rgb(140,255,185));full.setGravity(Gravity.CENTER);full.setTypeface(null,1);full.setPadding(dp(10),dp(11),dp(10),dp(11));full.setBackground(rounded(Color.rgb(12,54,33),14));LinearLayout.LayoutParams flp=new LinearLayout.LayoutParams(-1,-2);flp.bottomMargin=dp(20);root.addView(full,flp);
  add(root,"AMOLED CLOCK","Чистые цифровые часы • экономный тёмный стиль",Color.rgb(20,22,29),Color.WHITE,WidgetProviders.AmoledClock.class);
  add(root,"CLOCK + DATE","Крупное время, день недели и дата",Color.rgb(29,38,60),Color.WHITE,WidgetProviders.ClockDate.class);
  add(root,"CALENDAR","Минималистичная дата и текущий месяц",Color.rgb(239,229,218),Color.rgb(27,25,23),WidgetProviders.Calendar.class);
  add(root,"BATTERY NEON","Процент батареи в неоновом стиле",Color.rgb(8,34,21),Color.rgb(102,255,145),WidgetProviders.BatteryNeon.class);
  add(root,"GLASS","Стеклянный стиль: часы и дата",Color.rgb(43,62,95),Color.WHITE,WidgetProviders.Glass.class);
  add(root,"MINIMAL","Светлый минимальный виджет времени",Color.rgb(245,245,247),Color.rgb(18,18,20),WidgetProviders.Minimal.class);
  TextView note=text("Нажми «Добавить», затем подтверди размещение виджета на главном экране.",14,Color.rgb(150,156,170));note.setGravity(Gravity.CENTER);LinearLayout.LayoutParams nlp=new LinearLayout.LayoutParams(-1,-2);nlp.topMargin=dp(8);root.addView(note,nlp);
  setContentView(s);
 }
 @Override protected void onResume(){super.onResume();WidgetUpdater.refreshAll(this);}
 void add(LinearLayout p,String title,String desc,int bg,int fg,Class<?> cls){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(dp(18),dp(18),dp(18),dp(16));c.setBackground(rounded(bg,24));
  TextView tv=text(title,25,fg);tv.setTypeface(null,1);c.addView(tv);TextView dv=text(desc,15,fg);dv.setAlpha(.78f);LinearLayout.LayoutParams dlp=new LinearLayout.LayoutParams(-1,-2);dlp.topMargin=dp(4);c.addView(dv,dlp);
  Button b=new Button(this);b.setAllCaps(false);b.setText("Добавить на экран");b.setTextSize(15);b.setTextColor(bg);b.setTypeface(null,1);b.setBackground(rounded(fg,16));b.setOnClickListener(v->pin(cls));LinearLayout.LayoutParams blp=new LinearLayout.LayoutParams(-1,dp(52));blp.topMargin=dp(15);c.addView(b,blp);
  LinearLayout.LayoutParams clp=new LinearLayout.LayoutParams(-1,-2);clp.bottomMargin=dp(14);p.addView(c,clp);
 }
 void pin(Class<?> cls){AppWidgetManager m=AppWidgetManager.getInstance(this);ComponentName p=new ComponentName(this,cls);if(m.isRequestPinAppWidgetSupported()){if(!m.requestPinAppWidget(p,null,null))Toast.makeText(this,"Не удалось открыть добавление виджета",Toast.LENGTH_SHORT).show();}else Toast.makeText(this,"Зажми пустое место на главном экране → Виджеты → BigBoss Widgets",Toast.LENGTH_LONG).show();}
}