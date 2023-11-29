package com.example.javafx;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Scanner;
public class AdminLogin {

    @FXML
    private TextField adminUserName;
    @FXML
    private TextField adminPassword;
    @FXML
    private Label adminWarningLabel;

    @FXML
    private void adminBackButtonClicked(ActionEvent event) throws Exception {
        Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        main.showHomePage(primaryStage);
    }

    @FXML
    private void adminLoginButtonClicked(ActionEvent event) throws Exception {
        String adminUsername = adminUserName.getText();
        String adminPass = adminPassword.getText();

        if (Objects.equals(adminUsername, "root") && Objects.equals(adminPass, "8686"))
        {
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.close();

            Stage primaryStage = new Stage();
            main Main = new main();
            main.showAdminHomePage(primaryStage);
        }
        else
        {
            adminWarningLabel.setText("Wrong credentials. Please try again.");
        }

    }
}
