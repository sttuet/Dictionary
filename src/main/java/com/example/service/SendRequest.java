package com.example.service;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.example.service.ParseJSON.fromJson;

public class SendRequest {
    public static final String URL_PATH="https://api.dictionaryapi.dev/api/v2/entries/en/";
    private static final String URL_TRANSLATE_TEXT="https://translate.google.com/m";

    /**
     * gửi 1 http get request để lấy về thông tin từ cần tìm,( từ đơn thôi)
     * @param targetUrl url
     * @return JSON
     * @throws IOException
     */
    public static String sendRequest(String targetUrl) throws IOException {
        URL url=new URL(targetUrl);
        HttpURLConnection con=(HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent","Mozilla/5.0");
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
        Word my_word= fromJson(sendRequest(URL_PATH+word), Main.objectMapper);
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
    private static String format(String q){
        String[] list=q.split(" ");
        StringBuilder res=new StringBuilder();
        for(String s:list){
            res.append(s);
            res.append("+");
        }
        return res.toString();
    }
    private static String buildUrl(String from,String to,String q){
        return URL_TRANSLATE_TEXT+"?sl="+from+"&tl="+to+"&q="+format(q);
    }
    public static String getJsonTranslate(String from,String to,String text) throws IOException {
        String s=buildUrl(from,to,text);
        return sendRequest(s);
    }
}
