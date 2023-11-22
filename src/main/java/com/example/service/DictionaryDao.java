package com.example.service;

import com.example.ourdictionary.Main;

import java.sql.*;
import java.util.HashSet;


public class DictionaryDao {

    Connection con;
    Statement statement;

    /**
     * kết nối với database.
     */
    public DictionaryDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12663911"
                    , "sql12663911", "Pf1M4D3gAy");
            statement = con.createStatement();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * lấy tất cả các từ của user.
     *
     * @param user user cần lấy từ
     * @return set các từ
     */
    public HashSet<String> getAllWord(String user) {
        HashSet<String> list = new HashSet<>();
        try {
            ResultSet resultSet = statement.executeQuery("select FavouriteWord from UserInformation "
                    + "where (username ='" + user + "' and FavouriteWord is not null)");
            if (!resultSet.next()) {
                return list;
            }
            String allWords = resultSet.getString(1);
            String[] tmp = allWords.split(",");
            for (String s : tmp) {
                list.add(s);
            }
            list.remove("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * thêm tài khoản vào database.
     *
     * @param user tài khoản cần thêm
     * @param pass mật khẩu của tài khoản
     */
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

    /**
     * kiểm tra tài khoản.
     *
     * @param name     tên tài khoản
     * @param password mật khẩu
     * @return true hoặc false
     * @throws SQLException ngoại lệ sql
     */
    public boolean checkAccount(String name, String password) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from UserInformation where username ='" + name
                + "'and password ='" + password + "'");
        return resultSet.next();
    }

    public boolean checkAccount(String name) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from UserInformation where username ='"
                + name + "'");
        return resultSet.next();
    }

    /**
     * cập nhật từ.
     */
    public void updateListWord() {
        if (!Main.isGuest) {
            StringBuilder builder = new StringBuilder();
            for (String s : Main.favouriteList) {
                builder.append(s);
                builder.append(',');
            }
            String temp = builder.toString();
            String query = "update UserInformation set FavouriteWord ='" + temp
                    + "' where username ='" + Main.USERNAME + "'";
            try {
                PreparedStatement statement1 = con.prepareStatement(query);
                statement1.executeUpdate();
            } catch (Exception e) {
                System.out.println("can not update list ");
            }

        }

    }
}