package com.example.navigationapplication;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Constantes {
    public static final String BASE_URL = "http://172.15.1.255:8080";

    public String makeGetRequest(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        try{
            Response response = client.newCall(request).execute();
            return response.body().string();

        }catch (Exception e){
            return e.toString();
        }
    }
}
