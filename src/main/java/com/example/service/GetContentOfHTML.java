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
        String stringInBound=null;
        int ulTags=0;
        int liTags=0;
        while(currentIndex<s.length()){
            if (s.charAt(currentIndex)=='<'){
                tag=getNextTag(s,currentIndex);
                currentIndex+=tag.length();
                switch (tag){
                    case "<ul>":
                        ulTags++;
                        if(ans.charAt(ans.length()-1)!='\n'){
                            ans.append('\n');
                        }
                        break;
                    case "<li>":
                        liTags++;
                        if(ans.charAt(ans.length()-1)!='\n'){
                            ans.append('\n');
                        }
                        break;
                    case "</ul>":
                        ulTags--;

                        break;
                    case "</li>":
                        liTags--;

                        break;
                    default:
                        break;
                }
            }else {
                stringInBound=getStringInBound(s,currentIndex);
                System.out.println(stringInBound);
                currentIndex+=stringInBound.length();
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
        String ans="";
        if(s.charAt(currentIndex)=='<'){
            for(int i=currentIndex;;i++){
                ans+=s.charAt(i);
                if(s.charAt(i)=='>'){
                    return ans;
                }
            }
        }
        return "";
    }
    public static String getStringInBound(String s,int currentIndex){
        String ans="";
        for(int i=currentIndex;i<s.length();i++){
            if(s.charAt(i)=='<'){
                return ans;
            }else {
                ans+=s.charAt(i);
            }
        }
        return "";
    }

}
