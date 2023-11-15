package com.example.service;

import com.example.ourdictionary.Main;

import java.sql.*;
import java.util.HashSet;


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

    public HashSet<String> getAllWord(String user) {
        HashSet<String> list = new HashSet<>();
        try {
            ResultSet resultSet = statement.executeQuery("select FavouriteWord from UserInformation where (username = "
                    + "'" + user + "' and FavouriteWord is not null)");
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

    public void insertAccount(String user, String pass) {
        try {
            String query = "INSERT INTO UserInformation (username,password, FavouriteWord) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, pass);
                preparedStatement.setString(3, null);
                preparedStatement.executeUpdate();
                System.out.println("account inserted successfully!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertWord(String word) throws SQLException {
        try {
            String query = "INSERT INTO UserInformation (username,password, FavouriteWord) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, Main.USERNAME);
                preparedStatement.setString(2, Main.PASSWORD);
                preparedStatement.setString(3, word);
                preparedStatement.executeUpdate();
                System.out.println("Data inserted successfully!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean checkAccount(String name, String password) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from UserInformation where username ='" + name
                + "'and password ='" + password + "'");
        return resultSet.next();
    }

    public boolean checkAccount(String name) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from UserInformation where username ='" + name + "'");
        return resultSet.next();
    }

    public void removeWord(String s) throws SQLException {
        String query = "DELETE FROM UserInformation WHERE (username ='" + Main.USERNAME + "' and FavouriteWord ='" + s + "');";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.executeUpdate();
        System.out.println("delete successfully");
    }

//    public static void main(String[] args) throws SQLException {
//        DictionaryDao dd = new DictionaryDao();
//        System.out.println(dd.checkAcount("thinh2004", "thinh@2004"));
//    }
}