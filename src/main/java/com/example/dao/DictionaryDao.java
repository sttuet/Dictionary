package com.example.dao;

import com.example.ourdictionary.Word;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//DAO: Data Access Object, cap nhat du lieu tren database
//commet....
//wefwefa
public class DictionaryDao {
    Connection con;
    Statement statement;
    public DictionaryDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS","root","thinh2004");
            statement= con.createStatement();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String getDefinitionOf(String word){
        String ans = null;
        try {
            ResultSet resultSet=statement.executeQuery("select meaning from dictionary where word='"+word+"'");


            if(resultSet.next()){
                ans=resultSet.getString(1);
                return ans;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<String> getAllWord(){
        List<String> list=new ArrayList<>();
        try {
            ResultSet resultSet=statement.executeQuery("select word from dictionary ");
            while(resultSet.next()){
                list.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public void addWord(Word word)  {
        if(word.getWord().equals(""))return;
        if(getDefinitionOf(word.getWord())!=null){
            try {
                int i = statement.executeUpdate("update dictionary set meaning='"+word.getMeaning()+"' where word='"+word.getWord()+"'");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
        try {
            PreparedStatement preparedStatement= con.prepareStatement("insert into dictionary values(?,?)");
            preparedStatement.setString(1,word.getWord());
            preparedStatement.setString(2,word.getMeaning());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
