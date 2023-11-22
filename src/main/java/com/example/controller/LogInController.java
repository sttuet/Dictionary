package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.DictionaryDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInController extends Controller implements Initializable {
    public TextField username;
    public Button login;
    public Button signUp;
    public Button asGuest;
    public PasswordField password;
    public AnchorPane signUpPane;
    public TextField username1;
    public PasswordField password1;
    public PasswordField password11;
    public Button signUp1;

    public AnchorPane rootPane;
    public AnchorPane subPane;
    public Label wrongPass;
    public Label inappropriatePassword;
    public Label existedUsername;
    public Label nameAndPassFailed;
    public Label signUpSucceeded;
    public Button login1;
    public Label noInternet;
    private DictionaryDao dictionaryDao = null;

    /**
     * xử lý sự kiện bấm chuột vào nút login.
     *
     * @param event sự kiện chuột
     * @throws SQLException ngoại lệ SQL kết nối với database
     * @throws IOException  ngoại lệ input output
     */
    public void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {
        if (dictionaryDao != null) {
            String user = username.getText();
            String pass = password.getText();
            if (dictionaryDao.checkAccount(user, pass)) {
                Main.isGuest = false;
                Main.USERNAME = user;
                Main.PASSWORD = pass;
                super.changeScreenFromLogin("main-view.fxml", "MainView.css");
            } else {
                wrongPass.setVisible(true);
            }
        } else {
            noInternet.setVisible(true);
        }
    }

    /**
     * xử lý sự kiến bấm vào nút Sign up để đăng ký tài khoản.
     *
     * @param event sự kiện bấm chuôt
     * @throws SQLException ngoại lệ SQL kết nối với database
     */
    public void onSignUp1ButtonClick(ActionEvent event) throws SQLException {
        if (Main.checkInternetConnection()) {
            signUpPane.setVisible(true);
            subPane.setVisible(false);
        } else {
            noInternet.setVisible(true);
        }
    }

    /**
     * đăng nhập với tư cách khach.
     *
     * @param event sự kiện chuột
     * @throws IOException ngoại lệ input output
     */
    public void asGuestButtonClick(ActionEvent event) throws IOException {
        Main.isGuest = true;
        try {
            super.changeScreenFromLogin("main-view.fxml", "MainView.css");
        } catch (Exception e) {
            System.out.println("error on guest ");
        }
    }

    /**
     * bấm vào đăng ký để đăng ký tài khoản.
     *
     * @param event sự kiện bấm chuột
     * @throws SQLException ngoại lệ kết nối với database
     */
    public void onSignUp2ButtonClick(ActionEvent event) throws SQLException {
        if (dictionaryDao != null) {
            String user = username1.getText();
            String pass1 = password1.getText();
            String pass2 = password11.getText();
            inappropriatePassword.setVisible(false);
            existedUsername.setVisible(false);
            nameAndPassFailed.setVisible(false);
            if (!pass1.equals(pass2) && !dictionaryDao.checkAccount(user)) {
                inappropriatePassword.setVisible(true);
            }
            if (dictionaryDao.checkAccount(user) && pass1.equals(pass2)) {
                existedUsername.setVisible(true);
            }
            if (dictionaryDao.checkAccount(user) && !pass1.equals(pass2)) {
                nameAndPassFailed.setVisible(true);
            }
            if (!dictionaryDao.checkAccount(user) && pass1.equals(pass2)) {
                dictionaryDao.insertAccount(user, pass1);
                signUpSucceeded.setVisible(true);
            }
        }
    }

    /**
     * bấm vào sign in để quay trở lại đăng nhập.
     *
     * @param event chuột.
     */
    public void onSignInButtonClick(ActionEvent event) {
        signUpPane.setVisible(false);
        subPane.setVisible(true);
    }

    /**
     * khởi tạo các giá trị khi chạy.
     *
     * @param url            url
     * @param resourceBundle nguồn
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dictionaryDao = new DictionaryDao();
        } catch (Exception e) {
            System.out.println("No internet connection!");
        }
        Platform.runLater(()->{
            rootPane.requestFocus();
                }
        );
    }
}
