package com.example.service;

import com.example.ourdictionary.Main;

public class GetContentOfHTML {
    /**
     * lấy nghĩa của từ trong file E_V dưới dạng html
     * sau đó phân  tích để lấy ra các thông tin bên trong tag
     * VD: <p>dcm</p> thì return về dcm để hiển thị trong textArea lúc edit word.
     * @param word
     * @return
     */
    public static String  parse(String word){
        StringBuilder ans=new StringBuilder();
        String s= Main.meanings.get(word);
        if(s==null){
            return "";
        }
        int currentIndex=0;
        String tag=null;
        String stringInBound;
        int ulTags=0;
        while(currentIndex<s.length()){
            if (s.charAt(currentIndex)=='<'){
                tag=getNextTag(s,currentIndex);
                currentIndex+=tag.length();
                switch (tag) {
                    case "<ul>" -> {
                        ulTags++;
                        if (ans.charAt(ans.length() - 1) != '\n') {
                            ans.append('\n');
                        }
                    }
                    case "<li>" -> {
                        if (ans.charAt(ans.length() - 1) != '\n') {
                            ans.append('\n');
                        }
                    }
                    case "</ul>" -> ulTags--;
                    default -> {
                    }
                }
            }else {
                stringInBound=getStringInBound(s,currentIndex);
                currentIndex+=stringInBound.length();
                assert tag != null;
                if(!tag.equals("</b>")){
                    for(int i=0;i<ulTags;i++){
                        ans.append('\t');
                    }
                    ans.append('-');
                }
                ans.append(stringInBound);
            }
        }
        return ans.toString();
    }
    public static String getNextTag(String s,int currentIndex){
        StringBuilder ans= new StringBuilder();
        if(s.charAt(currentIndex)=='<'){
            for(int i=currentIndex;;i++){
                ans.append(s.charAt(i));
                if(s.charAt(i)=='>'){
                    return ans.toString();
                }
            }
        }
        return "";
    }
    public static String getStringInBound(String s,int currentIndex){
        StringBuilder ans= new StringBuilder();
        for(int i=currentIndex;i<s.length();i++){
            if(s.charAt(i)=='<'){
                return ans.toString();
            }else {
                ans.append(s.charAt(i));
            }
        }
        return "";
    }

}
