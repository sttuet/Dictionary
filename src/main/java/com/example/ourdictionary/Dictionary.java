package com.example.ourdictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Use: khi nhập từ vào ô tìm kiếm sã liệt kê ra các từ có phần tiền tố giống nhau ra ListView.
 * lưu toàn bộ các từ dưới 1 cái cây Trie
 *
 * @see Dictionary#Dictionary()
 * @see Dictionary#addWord(String)
 * @see Dictionary#has(String)
 * @see Dictionary#allWordsHas(String)
 */
public class Dictionary {
    private Node root;

    /**
     * khởi tạo 1 cái cây rỗng với root.
     */
    Dictionary() {
        root = new Node();
    }

    /**
     * Tìm các từ có tiền tố giống nhau
     *
     * @param prefix tiền tố cần tìm, là phần nhập trong ô tìm kiếm.
     * @return 1 List<String> các từ với tiền tố giống nhau
     */
    public List<String> allWordsHas(String prefix) {
        List<String> list = new ArrayList<>();
        return root.find(prefix);
    }

    /**
     * kiểm tra xem từ này có trong Dictionary hay k
     *
     * @param word từ cần kiểm tra
     * @return boolean
     */
    public boolean has(String word) {
        return root.check(word);
    }

    /**
     * thêm 1 từ mới vào từ điển.
     *
     * @param word từ cần thêm
     */
    public void addWord(String word) {
        root.add(word);
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

        public boolean check(String s) {
            Node root = this;
            for (int i = 0; i < s.length(); i++) {
                if (root.next[s.charAt(i) - 'a'] == null) return false;
            }
            return true;
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
}
