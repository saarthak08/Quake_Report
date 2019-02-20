/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {
    public static List<String>[] earthquake;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        RecyclerView recyclerView;
        recyclerView=findViewById(R.id.list);
        Intent intent=getIntent();
        String Json=intent.getStringExtra("abc");
        try {
            JSONData(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        final Adapter adapter=new Adapter(EarthquakeActivity.this,earthquake);
        recyclerView.setAdapter(adapter);
    }

   

    public static void JSONData(String json) throws JSONException {
        int i;
        Date date;
        SimpleDateFormat dateFormat;
        earthquake=new List[4];
        earthquake[0]=new ArrayList<>();
        earthquake[1]=new ArrayList<>();
        earthquake[2]=new ArrayList<>();
        earthquake[3]=new ArrayList<>();
        String SAMPLE_JSON_RESPONSE=json;
        JSONObject root=new JSONObject(SAMPLE_JSON_RESPONSE);
        JSONArray features=root.getJSONArray("features");
        for(i=0;i<=features.length();i++)
        {
            JSONObject index=features.getJSONObject(i);
            JSONObject properties=index.getJSONObject("properties");
            String m=(properties.getString("place"));
            earthquake[1].add(properties.getString("mag"));
            earthquake[3].add(properties.getString("url"));
            int k=m.lastIndexOf("of");
            if(k>0) {
                String u = m.substring(0, k + 2);
                String z = m.substring(k + 3, m.length());
                String l = u + "\n" + z;
                earthquake[0].add(l);
            }
            else
                earthquake[0].add(m);
            String x=properties.getString("time");
            long a=Long.parseLong(x);
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            date=new Date(a);
            String Time=timeFormat.format(date);
            String Timek=Time.toUpperCase();
            dateFormat=new SimpleDateFormat("MMM dd, yyyy");
            String dateToDisplay = dateFormat.format(date);
            String dates=dateToDisplay+ "\n" +Timek;
            earthquake[2].add(dates);
        }
    }
}
