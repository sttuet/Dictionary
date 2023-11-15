package com.example.controller;

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

    public void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {
        DictionaryDao DD = new DictionaryDao();
        String user = username.getText();
        String pass = password.getText();
        if (DD.checkAcount(user, pass)) {
            changeScreenFromLogin("main-view.fxml", "MainView.css");
        } else {
            wrongPass.setVisible(true);
        }
    }

    public void onSignUp1ButtonClick(ActionEvent event) {
        signUpPane.setVisible(true);
        subPane.setVisible(false);
    }

    public void asGuestButtonClick(ActionEvent event) {
    }

    public void onSignUp2ButtonClick(ActionEvent event) {
    }
}
