package com.example.service;

import com.example.ourdictionary.Dictionary;
import com.example.ourdictionary.Main;

import java.io.*;
import java.util.*;


public class IOFile {
    private static final String RECENT_PATH = "src\\main\\resources\\data\\Recent.txt";
    private static final String FAVOURITE_PATH = "src\\main\\resources\\data\\Favourite.txt";
    private static final String E_V_PATH = "src\\main\\resources\\data\\E_V.txt";
    private static final String COMMON_WORD_PATH = "src\\main\\resources\\data\\common_word.txt";
    private static final String MODIFIED_PATH = "src\\main\\resources\\data\\modified_word.txt";


    public static BufferedReader bufferedReader;
    public static BufferedWriter bufferedWriter;

    IOFile() {
    }

    public static void writeToFavouriteFile(Set<String> list) throws IOException {
        File file = new File(FAVOURITE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        bufferedWriter = new BufferedWriter(new FileWriter(FAVOURITE_PATH));
        for (String s : list) {
            bufferedWriter.write(s + '\n');
        }
        bufferedWriter.close();
    }

    public static Set<String> readFromFavouriteFile() throws IOException {
        Set<String> list = new HashSet<>();
        File file = new File(FAVOURITE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        bufferedReader = new BufferedReader(new FileReader(FAVOURITE_PATH));
        String word;
        while ((word = bufferedReader.readLine()) != null) {
            list.add(word);
        }
        return list;
    }

    public static void writeToRecentFile(List<String> list) throws IOException {
        File file = new File(RECENT_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        bufferedWriter = new BufferedWriter(new FileWriter(RECENT_PATH));
        for (String s : list) {
            bufferedWriter.write(s + '\n');
        }
        bufferedWriter.close();
    }

    public static LinkedList<String> readFromRecentFile() throws IOException {
        File file = new File(RECENT_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        bufferedReader = new BufferedReader(new FileReader(RECENT_PATH));
        LinkedList<String> list = new LinkedList<>();
        String word;
        while ((word = bufferedReader.readLine()) != null) {
            list.addLast(word);
        }
        return list;
    }

    public static Map<String, String> readFromE_VFile(Dictionary dictionary) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(E_V_PATH));
        String line;
        Map<String, String> means = new HashMap<>();
        while ((line = bufferedReader.readLine()) != null) {
            String[] part = line.split("<html>");
            means.put(part[0], "<html>" + part[1]);
            dictionary.addWord(part[0]);
        }
        return means;
    }

    public static List<String> readFromCommonWord() throws IOException {
        List<String> ans = new ArrayList<>();
        bufferedReader = new BufferedReader(new FileReader(COMMON_WORD_PATH));
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            ans.add(tmp);
        }
        return ans;
    }

    public static void writeToModifiedFile() throws IOException {
        File file = new File(MODIFIED_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        bufferedWriter = new BufferedWriter(new FileWriter(MODIFIED_PATH));
        for (String s : Main.modifiedWord.keySet()) {
            bufferedWriter.write(s + DELIMITER);
            bufferedWriter.write(Main.modifiedWord.get(s) + DELIMITER);
        }
        bufferedWriter.close();
    }

    public static final String DELIMITER = "@#!@!";

    public static Map<String, String> readFromModifiedFile(Dictionary dictionary) throws IOException {
        Map<String, String> meanings = new HashMap<>();
        File file = new File(MODIFIED_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(DELIMITER);
        while (scanner.hasNext()) {
            String word = scanner.next();
            String mean = "";
            if (!scanner.hasNext()) {
                break;
            }
            mean = scanner.next();
            meanings.put(word, mean);
            if (!dictionary.has(word)) {
                dictionary.addWord(word);
            }
        }
        return meanings;
    }


    /**
     * kiểm tra xem từ này có phải từ đơn hay k( chỉ chứa kí tự từ a-z) để add vào dictionary.
     *
     * @param s từ cần kiểm tra (chỉ được chứa a-z và ' ' và '-')
     * @return true false;
     */
    public static boolean isValidWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c < 'a' || c > 'z') && c != ' ' && c != '-') {
                return false;
            }
        }
        return true;
    }
}
