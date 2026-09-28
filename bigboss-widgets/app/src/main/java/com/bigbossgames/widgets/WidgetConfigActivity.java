package com.bigbossgames.widgets;
import android.app.*;import android.appwidget.*;import android.content.*;import android.os.*;
public class WidgetConfigActivity extends Activity{
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        int id=getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        Intent result=new Intent();result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,id);
        if(id==AppWidgetManager.INVALID_APPWIDGET_ID){setResult(RESULT_CANCELED,result);finish();return;}
        WidgetUpdater.copyPending(this,id);
        WidgetUpdater.update(this,AppWidgetManager.getInstance(this),id);
        setResult(RESULT_OK,result);finish();
    }
}