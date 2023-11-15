package com.example.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DictionaryDao {

    Connection con;
    Statement statement;

    public DictionaryDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12661845", "sql12661845", "b91yWry7Zx");
            statement = con.createStatement();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> getAllWord(String user) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select FavouriteWord from UserInformation where username = "
                    + "'" + user + "'");
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (String s : list) {
            System.out.println(s);
        }
        return list;
    }

    public boolean checkAcount(String name, String password) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from UserInformation where username ='" + name
                + "'and password ='" + password + "'");
        return resultSet.next();
    }

//    public static void main(String[] args) throws SQLException {
//        DictionaryDao dd = new DictionaryDao();
//        System.out.println(dd.checkAcount("thinh2004", "thinh@2004"));
//    }
}