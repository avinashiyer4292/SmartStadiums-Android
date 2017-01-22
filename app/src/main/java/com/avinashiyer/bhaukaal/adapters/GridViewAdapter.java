package com.avinashiyer.bhaukaal.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.avinashiyer.bhaukaal.R;
import com.avinashiyer.bhaukaal.utils.Seat;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * Created by avinashiyer on 1/21/17.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> seatNumbers;
    int width, height;
    private LayoutInflater inflater;
    private Seat seat;
    private boolean isStand;
    public GridViewAdapter(Context mContext,ArrayList<Integer> seatNumbers, int width, int height,Seat seat,boolean isStand){
        this.mContext = mContext;
        this.seatNumbers = seatNumbers;
        this.height = height;
        this.width = width;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.seat = seat;
        this.isStand = isStand;

    }
    @Override
    public int getCount() {
        return seatNumbers.size();
    }

    @Override
    public Object getItem(int i) {
        return seatNumbers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View grid, ViewGroup viewGroup) {
       IconTextView v;

        if(grid == null) {
            grid = inflater.inflate(R.layout.single_seat_layout, null);
            v =  (IconTextView)grid.findViewById(R.id.seatIconTextView);
            grid.setTag(v);
            v.setTag("allotted");

        }else{
            v= (IconTextView) grid.getTag();
        }
        v.setTag("allotted");
        v.setBackgroundResource(R.drawable.layout_border);
        //v.setText(String.valueOf(seatNumbers.get(i)));
        if(i==seat.getSeatNo()-1  && isStand){
            v.setTextColor(Color.parseColor("#12AD12"));
            v.setId(seat.getSeatNo());
        }
        return grid;
    }


}

interface ColorChangeListener{
    void onColorChange(View view);
}
