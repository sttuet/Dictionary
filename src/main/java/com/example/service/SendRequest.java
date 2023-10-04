package com.example.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
public class SendRequest {
    private static final String URL_PATH="https://api.dictionaryapi.dev/api/v2/entries/en/";

    /**
     * gửi 1 http get request để lấy về thông tin từ cần tìm,( từ đơn thôi)
     * @param word từ cần tìm
     * @return JSON
     * @throws IOException
     */
    public static String sendRequest(String word) throws IOException {
        URL url=new URL(URL_PATH+word);
        HttpURLConnection con=(HttpURLConnection) url.openConnection();
        con.setRequestProperty("content-type","application/json");
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status=con.getResponseCode();

        if(status>299){
            return null;
        }else{
            Reader reader=new InputStreamReader(con.getInputStream());
            StringBuilder jsonString=new StringBuilder();
            while(true){
                int i=reader.read();
                if(i==-1){
                    break;
                }else{
                    jsonString.append((char)i);
                }
            }
            return jsonString.toString();
        }
    }
}
