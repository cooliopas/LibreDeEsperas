package com.uxor.turnos.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TitleBarTextView extends TextView {


    public TitleBarTextView(Context context) {
      super(context);
      Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf"); 
      this.setTypeface(face); 
    }

    public TitleBarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf"); 
  this.setTypeface(face); 
    }

    public TitleBarTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
     Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf"); 
  this.setTypeface(face); 
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}
