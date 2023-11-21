package com.example.service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class SendRequest {
    public static final String NO_INTERNET = "No internet connection !";
    public static final String URL_PATH = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private static final String URL_TRANSLATE_TEXT = "https://translate.google.com/m";
    private static final String GOOGLE_SYNTHESISER_URL="http://translate.google.com/translate_tts";

    /**
     * gửi 1 http get request để lấy về thông tin từ cần tìm,( từ đơn thôi)
     *
     * @param targetUrl url
     * @return JSON
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static String sendRequest(String targetUrl) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status;
        try {
            status = con.getResponseCode();
        } catch (Exception e) {
            return NO_INTERNET;
        }
        if (status > 299) {
            return null;
        } else {
            Reader reader = new InputStreamReader(con.getInputStream());
            StringBuilder jsonString = new StringBuilder();
            while (true) {
                int i = reader.read();
                if (i == -1) {
                    break;
                } else {
                    jsonString.append((char) i);
                }
            }
            return jsonString.toString();
        }
    }

    /**
     * download mp3 file of a word if possible.
     * source code of api : <a href="https://github.com/goxr3plus/java-google-speech-api/blob/master/src/main/java/com/goxr3plus/speech/synthesiser/Synthesiser.java">...</a>
     *
     * @param word word need to download
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static void downloadAudio(String word) throws IOException {
        long start =System.currentTimeMillis();
        String languageCode="en-us";
        String encoded = URLEncoder.encode(word, StandardCharsets.UTF_8); //Encode
        StringBuilder sb = new StringBuilder();
        sb.append(GOOGLE_SYNTHESISER_URL); //The base URL prefixed by the query parameter.
        sb.append("?tl=");
        sb.append(languageCode); //The query parameter to specify the language code.
        sb.append("&q=");
        sb.append(encoded); //We encode the String using URL Encoder
        sb.append("&ie=UTF-8&total=1&idx=0"); //Some unknown parameters needed to make the URL work
        sb.append("&textlen=");
        sb.append(word.length()); //We need some String length now.
        sb.append("&client=tw-ob"); //Once again, a weird parameter.
        //Client=t no longer works as it requires a token, but client=tw-ob seems to work just fine.

        URL url = new URL(sb.toString());
        // Open New URL connection channel.
        URLConnection urlConn = null;
        InputStream in=null;
        try{
            urlConn = url.openConnection(); //Open connection
            urlConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) "
                    + "Gecko/20100101 Firefox/4.0");
            in= urlConn.getInputStream();
        }catch (Exception e){
            System.out.println("no internet");
            return;
        }
        //Adding header for user agent is required

        File audio=new File("src\\main\\resources\\audio\\" + word + ".mp3");
        if(!audio.exists()) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(audio));
            byte[] b = in.readAllBytes();
            bufferedOutputStream.write(b);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
    }

    private static String buildUrl(String from, String to, String q) {
        return URL_TRANSLATE_TEXT + "?sl=" + from + "&tl=" + to + "&q=" + URLEncoder.encode(q, StandardCharsets.UTF_8);
    }

    public static String getJsonTranslate(String from, String to, String text) throws IOException {
        String s = buildUrl(from, to, text);
        return sendRequest(s);
    }
}
