package com.example.service;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.service.ParseJSON.fromJson;

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

    /**
     * download mp3 file of a word if possible
     * @param word word need to download
     * @throws IOException
     */
    public static void downloadAudio(String word) throws IOException {
        Word my_word= fromJson(sendRequest(word), Main.objectMapper);
        String url=my_word.getAudio();
        if(url==null)return;
        else if(url.equals(""))return;
        else {
            System.out.println(url);
             String path= "src\\main\\resources\\audio\\"+word+".mp3";
             URL audio_url=new URL(url);
            FileOutputStream fout=new FileOutputStream(path);
            InputStream in=new BufferedInputStream(audio_url.openStream());
            fout.write(in.readAllBytes());
            fout.flush();
            fout.close();
        }
    }
}
