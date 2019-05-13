package com.yashodainfotech.queuingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MemberImageDownloader extends AsyncTask<String,Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url= new URL(urls[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream= connection.getInputStream();
            Bitmap memberImage= BitmapFactory.decodeStream(inputStream);
            return memberImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
