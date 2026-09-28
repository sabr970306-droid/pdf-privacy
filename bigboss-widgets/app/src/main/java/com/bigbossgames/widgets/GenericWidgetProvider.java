package com.bigbossgames.widgets;
import android.appwidget.*;import android.content.*;
public class GenericWidgetProvider extends AppWidgetProvider{
    @Override public void onUpdate(Context c,AppWidgetManager m,int[] ids){for(int id:ids)WidgetUpdater.update(c,m,id);}
    @Override public void onAppWidgetOptionsChanged(Context c,AppWidgetManager m,int id,android.os.Bundle opts){WidgetUpdater.update(c,m,id);}
}