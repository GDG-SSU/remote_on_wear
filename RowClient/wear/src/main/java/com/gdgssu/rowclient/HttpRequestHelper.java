package com.gdgssu.rowclient;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HttpRequestHelper {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String BASE_URL = "http://172.30.0.113:8080";

    public static void get(String url){

        client.get(getAbsoluteUrl(url), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("network is good", "network is good");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.d("url", BASE_URL+relativeUrl);
        return BASE_URL + relativeUrl;
    }
}
