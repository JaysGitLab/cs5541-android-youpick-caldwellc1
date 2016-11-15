package com.bignerdranch.android.draganddraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by caldwellc1 on 11/8/2016.
 */
public class DragAndDrawFragment extends Fragment {
    private BoxDrawingView mBoxDrawingView;

    public static DragAndDrawFragment newInstance(){
        return new DragAndDrawFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_drag_and_draw, container, false);
        mBoxDrawingView = (BoxDrawingView) v.findViewById(R.id.View);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_options, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_color:
                if(BoxDrawingView.mBoxPaint.getColor()==0x22ff0000){
                    BoxDrawingView.mBoxPaint.setColor(0x2200ffff);
                    BoxDrawingView.mBackgroundPaint.setColor(0xff000000);
                }
                else {
                    BoxDrawingView.mBoxPaint.setColor(0x22ff0000);
                    BoxDrawingView.mBackgroundPaint.setColor(0xfff8efe0);
                }
                mBoxDrawingView.setVisibility(View.GONE);
                mBoxDrawingView.setVisibility(View.VISIBLE);
                return true;
            case R.id.menu_item_clear:
                BoxDrawingView.mBoxen.clear();
                mBoxDrawingView.setVisibility(View.GONE);
                mBoxDrawingView.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
