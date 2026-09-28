package com.bigbossgames.widgets;

import android.app.*;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomizeActivity extends Activity {
    private static final int PICK_PHOTO=501;
    private String type,style,photoUri="";
    private int palette,sizeIndex=1;
    private boolean use24=true;
    private LinearLayout preview;
    private TextView p1,p2,p3;
    private EditText city;
    private final int[] BG={0xff0a0b0f,0xff1d2842,0xff1f1539,0xff072a1a,0xff562334,0xfff1ebe2,0xff14303c,0xff281f1b,0xff14161c,0xff34244a};
    private final int[] FG={0xffffffff,0xffe2ebff,0xffe7d8ff,0xff69ff96,0xffffdccd,0xff1e1d1c,0xffdbf9ff,0xffffead2,0xfff6f7fa,0xfff4deff};

    int dp(float v){return Math.round(v*getResources().getDisplayMetrics().density);}
    TextView t(String s,float sp,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(sp);v.setTextColor(c);return v;}
    GradientDrawable bg(int c,float r){GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));return g;}

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        type=getIntent().getStringExtra("type"); style=getIntent().getStringExtra("style");
        palette=getIntent().getIntExtra("palette",0);
        if(type==null)type="Clock";if(style==null)style="Simple1";
        getWindow().setStatusBarColor(0xff090a0e);getWindow().setNavigationBarColor(0xff090a0e);

        ScrollView sc=new ScrollView(this);sc.setBackgroundColor(0xff090a0e);
        LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(18),dp(18),dp(35));
        sc.addView(root,new ScrollView.LayoutParams(-1,-2));

        TextView back=t("‹  BigBoss Widgets",15,0xffaaaec0);back.setOnClickListener(v->finish());root.addView(back);
        TextView title=t("Настрой виджет",31,Color.WHITE);title.setTypeface(null,1);LinearLayout.LayoutParams tlp=new LinearLayout.LayoutParams(-1,-2);tlp.topMargin=dp(9);root.addView(title,tlp);
        TextView sub=t(StyleCatalog.title(new StyleCatalog.Style(type,style))+"  •  "+type,14,0xff9ca3b5);root.addView(sub);

        preview=new LinearLayout(this);preview.setOrientation(LinearLayout.VERTICAL);preview.setGravity(Gravity.CENTER_VERTICAL);preview.setPadding(dp(22),dp(18),dp(22),dp(18));
        LinearLayout.LayoutParams pp=new LinearLayout.LayoutParams(-1,dp(190));pp.topMargin=dp(20);root.addView(preview,pp);
        p1=t("",48,Color.WHITE);p1.setTypeface(null,1);preview.addView(p1);
        p2=t("",16,Color.WHITE);preview.addView(p2);
        p3=t("",12,Color.WHITE);p3.setAlpha(.65f);LinearLayout.LayoutParams p3p=new LinearLayout.LayoutParams(-1,-2);p3p.topMargin=dp(4);preview.addView(p3,p3p);

        label(root,"РАЗМЕР");
        LinearLayout sizes=new LinearLayout(this);sizes.setOrientation(LinearLayout.HORIZONTAL);
        String[] sz={"S","M","L"};for(int i=0;i<3;i++){final int x=i;Button bt=new Button(this);bt.setText(sz[i]);bt.setTextColor(Color.WHITE);bt.setBackground(bg(0xff1d202a,14));bt.setOnClickListener(v->{sizeIndex=x;updatePreview();});LinearLayout.LayoutParams q=new LinearLayout.LayoutParams(0,dp(46),1);q.rightMargin=i<2?dp(8):0;sizes.addView(bt,q);}root.addView(sizes);

        label(root,"ЦВЕТ / ТЕМА");
        HorizontalScrollView hs=new HorizontalScrollView(this);hs.setHorizontalScrollBarEnabled(false);
        LinearLayout colors=new LinearLayout(this);colors.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<10;i++){final int x=i;TextView c=t("●",36,FG[i]);c.setGravity(Gravity.CENTER);c.setBackground(bg(BG[i],18));c.setOnClickListener(v->{palette=x;updatePreview();});LinearLayout.LayoutParams q=new LinearLayout.LayoutParams(dp(56),dp(56));q.rightMargin=dp(8);colors.addView(c,q);}hs.addView(colors);root.addView(hs);

        CheckBox cb=new CheckBox(this);cb.setText("24-часовой формат");cb.setTextColor(0xffe7e9f2);cb.setChecked(true);cb.setOnCheckedChangeListener((v,c)->{use24=c;updatePreview();});
        LinearLayout.LayoutParams cbp=new LinearLayout.LayoutParams(-1,-2);cbp.topMargin=dp(14);root.addView(cb,cbp);

        if("Weather".equals(type)||"WeatherClock".equals(type)){
            label(root,"ГОРОД");
            city=new EditText(this);city.setText("Almaty");city.setHint("City");city.setTextColor(Color.WHITE);city.setHintTextColor(0xff73798a);city.setSingleLine();city.setPadding(dp(14),0,dp(14),0);city.setBackground(bg(0xff1a1d26,14));
            root.addView(city,new LinearLayout.LayoutParams(-1,dp(52)));
        }

        if("Photo".equals(type)){
            label(root,"ФОТО");
            Button photo=new Button(this);photo.setAllCaps(false);photo.setText("Выбрать фото из галереи");photo.setTextColor(Color.WHITE);photo.setBackground(bg(0xff262b39,14));
            photo.setOnClickListener(v->pickPhoto());root.addView(photo,new LinearLayout.LayoutParams(-1,dp(52)));
        }

        if("ScreenTime".equals(type)){
            label(root,"SCREEN TIME");
            Button usage=new Button(this);usage.setAllCaps(false);usage.setText("Разрешить доступ к статистике использования");usage.setTextColor(Color.WHITE);usage.setBackground(bg(0xff262b39,14));
            usage.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));root.addView(usage,new LinearLayout.LayoutParams(-1,dp(52)));
        }

        TextView note=t("Все функции открыты. Никаких Premium, подписок и внутренних покупок.",13,0xff9ca3b5);
        LinearLayout.LayoutParams np=new LinearLayout.LayoutParams(-1,-2);np.topMargin=dp(18);root.addView(note,np);

        Button add=new Button(this);add.setAllCaps(false);add.setText("Добавить на главный экран");add.setTextColor(Color.WHITE);add.setTextSize(16);add.setTypeface(null,1);add.setBackground(bg(0xff6b52ff,18));add.setOnClickListener(v->pin());
        LinearLayout.LayoutParams ap=new LinearLayout.LayoutParams(-1,dp(58));ap.topMargin=dp(18);root.addView(add,ap);

        setContentView(sc);updatePreview();
    }

    private void label(LinearLayout root,String s){TextView l=t(s,12,0xff858c9f);l.setTypeface(null,1);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(20);p.bottomMargin=dp(7);root.addView(l,p);}

    private void updatePreview(){
        preview.setBackground(bg(BG[palette],24));p1.setTextColor(FG[palette]);p2.setTextColor(FG[palette]);p3.setTextColor(FG[palette]);
        String time=new SimpleDateFormat(use24?"HH:mm":"h:mm a",Locale.getDefault()).format(new Date());
        String date=new SimpleDateFormat("EEEE • d MMMM",Locale.getDefault()).format(new Date()).toUpperCase();
        int bat=new BatteryManagerProxy(this).level();
        if("Battery".equals(type)){p1.setText(bat+"%");p2.setText("BATTERY");p3.setText(StyleCatalog.title(new StyleCatalog.Style(type,style)));}
        else if("Calendar".equals(type)||"Date".equals(type)){p1.setText(new SimpleDateFormat("dd",Locale.getDefault()).format(new Date()));p2.setText(new SimpleDateFormat("MMMM yyyy",Locale.getDefault()).format(new Date()).toUpperCase());p3.setText(new SimpleDateFormat("EEEE",Locale.getDefault()).format(new Date()).toUpperCase());}
        else if("XPanel".equals(type)){p1.setText(time);p2.setText("BATTERY "+bat+"%");p3.setText(date);}
        else if("Weather".equals(type)){p1.setText("23°");p2.setText("WEATHER");p3.setText("ALMATY");}
        else if("WeatherClock".equals(type)){p1.setText(time);p2.setText("23° • ALMATY");p3.setText(date);}
        else if("Photo".equals(type)){p1.setText("PHOTO");p2.setText(photoUri.length()>0?"PHOTO SELECTED":"CHOOSE YOUR PHOTO");p3.setText(StyleCatalog.title(new StyleCatalog.Style(type,style)));}
        else if("Gif".equals(type)){p1.setText("GIF");p2.setText("MOTION WIDGET");p3.setText(StyleCatalog.title(new StyleCatalog.Style(type,style)));}
        else if("ScreenTime".equals(type)){p1.setText("2h 18m");p2.setText("SCREEN TIME");p3.setText("TODAY");}
        else if("Music".equals(type)){p1.setText("♫");p2.setText("NOW PLAYING");p3.setText("MUSIC");}
        else if("Distance".equals(type)){p1.setText("0.0 KM");p2.setText("DISTANCE");p3.setText("TODAY");}
        else {p1.setText(time);p2.setText(date);p3.setText(StyleCatalog.title(new StyleCatalog.Style(type,style)));}
        p1.setTextSize(sizeIndex==0?38:(sizeIndex==1?48:56));
    }

    private void pickPhoto(){
        Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("image/*");i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i,PICK_PHOTO);
    }

    @Override protected void onActivityResult(int req,int res,Intent data){
        super.onActivityResult(req,res,data);
        if(req==PICK_PHOTO&&res==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri u=data.getData();photoUri=u.toString();
            try{getContentResolver().takePersistableUriPermission(u,Intent.FLAG_GRANT_READ_URI_PERMISSION);}catch(Exception ignored){}
            updatePreview();
        }
    }

    private void pin(){
        SharedPreferences p=getSharedPreferences("widgets",MODE_PRIVATE);
        SharedPreferences.Editor e=p.edit()
                .putString("pending_type",type).putString("pending_style",style)
                .putInt("pending_palette",palette).putInt("pending_size",sizeIndex)
                .putBoolean("pending_24h",use24).putString("pending_photo",photoUri);
        if(city!=null)e.putString("pending_city",city.getText().toString().trim());
        else e.putString("pending_city","Almaty");
        e.apply();

        AppWidgetManager m=AppWidgetManager.getInstance(this);
        ComponentName provider=new ComponentName(this,GenericWidgetProvider.class);
        if(m.isRequestPinAppWidgetSupported()){
            Intent success=new Intent(this,PinSuccessReceiver.class);
            PendingIntent pi=PendingIntent.getBroadcast(this,7001,success,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
            if(m.requestPinAppWidget(provider,null,pi)){
                Toast.makeText(this,"Подтверди добавление виджета",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this,"Не удалось открыть добавление",Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this,"Зажми рабочий стол → Виджеты → BigBoss Widgets",Toast.LENGTH_LONG).show();
    }

    static final class BatteryManagerProxy{
        private final Context c;BatteryManagerProxy(Context c){this.c=c;}
        int level(){android.os.BatteryManager b=(android.os.BatteryManager)c.getSystemService(Context.BATTERY_SERVICE);int v=b==null?-1:b.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY);return v<0?0:v;}
    }
}