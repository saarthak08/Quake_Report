package com.example.android.quakereport;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LauncherActivity extends AppCompatActivity {
        public String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        URL=setURL();
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        Toast.makeText(LauncherActivity.this,"Created By: Saarthak Gupta",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.animate().alpha(1).setDuration(500);
                progressBar.setIndeterminate(true);
                Internet_data task=new Internet_data(URL,LauncherActivity.this);
                task.execute();
            }
        },2000);
    }
    public String setURL()
    {
        String a=
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-01-01&endtime=2019-02-10&limit=50&minmagnitude=5&orderby=time";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String result= "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-01-01&endtime="+date+"&limit=50&minmagnitude=5&orderby=time";
        return result;
    }
}
