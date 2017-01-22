package com.avinashiyer.bhaukaal.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.avinashiyer.bhaukaal.R;

/**
 * Created by avinashiyer on 1/22/17.
 */

public class NotificationViewActivity extends AppCompatActivity {
    TextView tv1,tv2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");
        tv1 = (TextView)findViewById(R.id.textViewNotifTitle);
        tv2 = (TextView)findViewById(R.id.textViewNotifText);
        tv1.setText(title);
        tv2.setText(text);


    }
}
