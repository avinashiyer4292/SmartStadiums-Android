package com.avinashiyer.bhaukaal.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avinashiyer.bhaukaal.R;
import com.avinashiyer.bhaukaal.adapters.GridViewAdapter;
import com.avinashiyer.bhaukaal.receivers.AlarmReceiver;
import com.avinashiyer.bhaukaal.utils.Seat;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import tourguide.tourguide.ChainTourGuide;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Sequence;
import tourguide.tourguide.ToolTip;


public class HomeActivity extends AppCompatActivity {
GridView gridView1, gridView2, gridView3, gridView4;
ImageView exit,exit2;
    static View allottedSeat,beaconSeat;
    private Animation mEnterAnimation, mExitAnimation;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView1 = (GridView)findViewById(R.id.gridviewTop);
        gridView2 = (GridView)findViewById(R.id.gridviewLeft);
        gridView3 = (GridView)findViewById(R.id.gridviewRight);
        gridView4 = (GridView)findViewById(R.id.gridviewBottom);
        exit = (ImageView)findViewById(R.id.exit1);
        exit2 = (ImageView)findViewById(R.id.exit2);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        for(int i=1;i<37;i++){
            list1.add(i);
        }

        ArrayList<Integer> list2 = new ArrayList<Integer>();
        for(int i=1;i<31;i++){
            list2.add(i);
        }

        ArrayList<Integer> list3 = new ArrayList<Integer>();
        for(int i=1;i<31;i++){
            list3.add(i);
        }

        ArrayList<Integer> list4 = new ArrayList<Integer>();
        for(int i=1;i<37;i++){
            list4.add(i);
        }
        final Seat seat = new Seat("B",21);
        gridView1.setAdapter(new GridViewAdapter(this,list1,width-13,70,seat,false));
        gridView2.setAdapter(new GridViewAdapter(this,list2,70,250,seat,true));
        gridView3.setAdapter(new GridViewAdapter(this,list3,70,250,seat,false));
        gridView4.setAdapter(new GridViewAdapter(this,list4,width-13,70,seat,false));


        ViewTreeObserver vto = gridView1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                View v = gridView1.getChildAt(6);
                IconTextView itv = (IconTextView)v.findViewById(R.id.seatIconTextView);
                itv.setTextColor(Color.parseColor("#08B2B6"));
                v = (View)gridView1.getChildAt(25);
                itv = (IconTextView)v.findViewById(R.id.seatIconTextView);
                itv.setTextColor(Color.parseColor("#08B2B6"));
                beaconSeat = itv;


            }
        });

        ViewTreeObserver vto2 = gridView2.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                View v = gridView2.getChildAt(8);
                IconTextView itv = (IconTextView)v.findViewById(R.id.seatIconTextView);
                //flashUserSeat(itv,R.color.beaconColor);
                itv.setTextColor(Color.parseColor("#08B2B6"));
                v = gridView2.getChildAt(19);
                itv = (IconTextView)v.findViewById(R.id.seatIconTextView);
                allottedSeat = itv;
                showTour(exit,allottedSeat,beaconSeat);

            }
        });

        displaySeatAlertDialog(seat);

        //View v = (View)gridView2.findViewById(seat.getSeatNo());



        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(HomeActivity.this,"Starting notifica services...",Toast.LENGTH_SHORT).show();
                Intent alarmIntent = new Intent(HomeActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, alarmIntent, 0);
                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                int interval = 10000;

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+interval, interval, pendingIntent);

            }
        });

        Intent alarmIntent = new Intent(HomeActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, alarmIntent, 0);
    }

    private void changeBeaconseats(View view){

    }
    private void blinkBeaconSeats(View view){
        ObjectAnimator colorFade = ObjectAnimator.ofObject(view, "textColor", new ArgbEvaluator(), Color.WHITE,R.color.beaconColor);
        colorFade.setDuration(5000);
        colorFade.start();
    }

    private void flashUserSeat(View view,int color){
        final AnimationDrawable drawable = new AnimationDrawable();
        final Handler handler = new Handler();
        final View v=view;

        view.setBackgroundDrawable(drawable);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //drawable.start();
                blinkBeaconSeats(v);
            }
        }, 100);
    }
    private void displaySeatAlertDialog(Seat seat){
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Seat No.")
                .setMessage("Your seat no. is: Stand-"+seat.getStand()+", Seat-"+seat.getSeatNo() +"(marked GREEN)")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })

                .show();


    }



    private void showTour(View v1, View v2, View v3){
        mEnterAnimation = new AlphaAnimation(0f, 1f);
        mEnterAnimation.setDuration(1000);
        mEnterAnimation.setFillAfter(true);

        mExitAnimation = new AlphaAnimation(1f, 0f);
        mExitAnimation.setDuration(1000);
        mExitAnimation.setFillAfter(true);

        runOverlay_ContinueMethod(v1,v2,v3,mEnterAnimation,mExitAnimation);
    }
    private void runOverlay_ContinueMethod(View v1, View v2, View v3,Animation mEnterAnimation,Animation mExitAnimation){
        // the return handler is used to manipulate the cleanup of all the tutorial elements
        ChainTourGuide tourGuide1 = ChainTourGuide.init(this)
                .setToolTip(new ToolTip()
                        .setTitle("")
                        .setDescription("You are currently here")
                        .setGravity(Gravity.BOTTOM)
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(v1);

        ChainTourGuide tourGuide2 = ChainTourGuide.init(this)
                .setToolTip(new ToolTip()
                        .setTitle("")
                        .setDescription("Your seat is here")
                        .setGravity(Gravity.RIGHT)

                )
                .playLater(v2);

        ChainTourGuide tourGuide3 = ChainTourGuide.init(this)
                .setToolTip(new ToolTip()
                        .setTitle("")
                        .setDescription("Follow the seats highlighted in BLUE to reach your seat")
                        .setGravity(Gravity.BOTTOM)
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(v3);

        Sequence sequence = new Sequence.SequenceBuilder()
                .add(tourGuide1, tourGuide2, tourGuide3)
                .setDefaultOverlay(new Overlay()
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                )
                .setDefaultPointer(null)
                .setContinueMethod(Sequence.ContinueMethod.Overlay)
                .build();


        ChainTourGuide.init(this).playInSequence(sequence);
    }
}
