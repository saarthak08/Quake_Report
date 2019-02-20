package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public class Internet_data extends AsyncTask<URL,Void,Object> {
    String mURL;
    Context mContext;
    public String JSONResponse;
    public Internet_data(String url, Context context)
    {
        mURL=url;
        mContext=context;
    }
    @Override
    protected Object doInBackground(URL... urls) {
        URL url= createURL(mURL);
        JSONResponse="";
        try {
            JSONResponse=makeHTTPRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String makeHTTPRequest(URL url) throws IOException{
        String JSONResponse="";
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try{
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            JSONResponse=readFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JSONResponse;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (JSONResponse != null) {
            Intent intent = new Intent(mContext, EarthquakeActivity.class);
            intent.putExtra("abc",JSONResponse);
            mContext.startActivity(intent);
        }
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private URL createURL(String urls)
    {
        URL url=null;
        try
        {
            url=new URL(urls);
        }
        catch(MalformedURLException exception)
        {
            Log.e(LOG_TAG,"Error with creating URL",exception);
            return null;
        }
        return url;
    }
}
