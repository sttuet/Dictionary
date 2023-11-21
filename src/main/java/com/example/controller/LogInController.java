package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.DictionaryDao;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class LogInController extends Controller {
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
    private DictionaryDao dictionaryDao = null;

    public void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {
        try{
            dictionaryDao=new DictionaryDao();
        }catch (Exception e){
            System.out.println("no internet");
            return;
        }
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
    }

    public void onSignUp1ButtonClick(ActionEvent event) throws SQLException {
        signUpPane.setVisible(true);
        subPane.setVisible(false);

    }

    public void asGuestButtonClick(ActionEvent event) throws IOException {
        Main.isGuest = true;
        try {
            super.changeScreenFromLogin("main-view.fxml", "MainView.css");
        }catch (Exception e){
            System.out.println("error on guest ");
        }
    }

    public void onSignUp2ButtonClick(ActionEvent event) throws SQLException {
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
            dictionaryDao.insertAccount(user,pass1);
            signUpSucceeded.setVisible(true);
        }
    }

    public void onSignInButtonClick(ActionEvent event) {
        signUpPane.setVisible(false);
        subPane.setVisible(true);
    }
}
