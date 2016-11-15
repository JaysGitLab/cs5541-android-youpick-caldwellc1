package com.bignerdranch.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caldwellc1 on 11/8/2016.
 */
public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";
    private static final String PARENT_STATE_KEY = "ParentKey?";
    private Box mCurrentBox;
    public static List<Box> mBoxen = new ArrayList<>();
    public static Paint mBoxPaint;
    public static Paint mBackgroundPaint;

    public BoxDrawingView(Context context){
        this(context, null);
    }

    public BoxDrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPaint(mBackgroundPaint);
        for(Box box : mBoxen){
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if(mCurrentBox != null){
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
        }
        Log.i(TAG, action + " at x=" + current.x + ", y=" + current.y);
        return true;
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        Parcelable parentParcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARENT_STATE_KEY, parentParcelable);

        int boxNumber =1;
        for(Box box : mBoxen){
            float[] pointArray= {box.getOrigin().x, box.getOrigin().y, box.getCurrent().x, box.getCurrent().y};
            bundle.putFloatArray("box" + boxNumber, pointArray);
            boxNumber++;
        }
        if(mBoxPaint.getColor()==0x2200ffff) {
            bundle.putBoolean("color", true);
        }
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state){
        BoxDrawingView.mBoxen.clear();
        if(state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(PARENT_STATE_KEY));
            String prefixName = "box";
            int boxCount = 1;
            while (bundle.containsKey(prefixName + boxCount)) {
                float[] pointArray = bundle.getFloatArray(prefixName + boxCount);
                PointF origin = new PointF(pointArray[0], pointArray[1]);
                PointF current = new PointF(pointArray[2], pointArray[3]);
                Box box = new Box(origin);
                box.setCurrent(current);

                mBoxen.add(box);
                boxCount++;
            }
            super.onRestoreInstanceState(bundle.getParcelable("color"));
            if(bundle.containsKey("color")==true){
                BoxDrawingView.mBoxPaint.setColor(0x2200ffff);
                BoxDrawingView.mBackgroundPaint.setColor(0xff000000);
            }
            else{
                BoxDrawingView.mBoxPaint.setColor(0x22ff0000);
                BoxDrawingView.mBackgroundPaint.setColor(0xfff8efe0);
            }
        }
        else{
            super.onRestoreInstanceState(state);
        }
    }

}
