package com.example.ourdictionary;

import java.util.ArrayList;
import java.util.List;

// dùng array thì tìm từ t nghĩ sẽ lâu hơn nên dùng cây trie
public class Dictionary {
    private Node root;
    Dictionary() {
        root=new Node();
        List<String> list=Main.dictionaryDao.getAllWord();
        for(String s:list){
            root.add(s);
        }
    }
    public List<String> allWordsHas(String prefix){
        List<String> list=new ArrayList<>();
        //code here
        return root.find(prefix);
    }
    public boolean has(String word){
        return root.check(word);
    }

    public void addWord(String word){
        root.add(word);
    }
}
class Node {
    Node[] next;
    char character;
    int pre;
    boolean end;

    Node() {
        end = false;
        pre = 0;
        next = new Node[26];
    }
    public boolean check(String s){
        Node root=this;
        for(int i=0;i<s.length();i++){
            if(root.next[s.charAt(i)-'a']==null)return false;
        }return true;
    }
    public void add(String s) {
        Node root = this;
        for (int i = 0; i < s.length(); i++) {
            if (root.next[s.charAt(i) - 'a'] != null) {
                root = root.next[s.charAt(i) - 'a'];
                root.pre++;
            } else {
                root.next[s.charAt(i) - 'a'] = new Node();
                root = root.next[s.charAt(i) - 'a'];
                root.pre++;
            }
            if (i == s.length() - 1) {
                root.end = true;
            }
        }
    }

    public void Try(List<String> list, Node root, String cur, int need) {
        if (list.size() > need) return;
        if (root.end) {
            list.add(cur);
        }

        for (int i = 0; i < 26; i++) {
            if (root.next[i] == null) continue;
            String newString = cur + (char) ('a' + i);
            Try(list, root.next[i], newString, need);
        }

    }

    public List<String> find(String s) {

        Node root = this;
        List<String> list = new ArrayList<>();
        if (s.equals("")) return list;
        for (int i = 0; i < s.length(); i++) {
            if (root.next[s.charAt(i) - 'a'] == null) return list;
            root = root.next[s.charAt(i) - 'a'];
        }
        int numWord = Math.min(root.pre, 20);
        Try(list, root, s, numWord);
        return list;
    }
}