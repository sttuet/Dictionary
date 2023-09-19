package com.example.dao;

import com.example.ourdictionary.Word;
import java.sql.*;
//DAO: Data Access Object, cap nhat du lieu tren database

public class DictionaryDao {
    Connection con;
    Statement statement;
    DictionaryDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Dictionary","root","a4k23cvp");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getDefinitionOf(String word){
        return "";
    }
    public static void addWord(Word word){
        //add comment
    }
}
