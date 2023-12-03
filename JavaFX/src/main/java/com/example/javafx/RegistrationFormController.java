package com.example.javafx;


import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.event.Event;
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

public class RegistrationFormController {

    @FXML
    private TextField rollNumberInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField retypePasswordInput;
    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Label registrationMessageLabel;

    @FXML
    public void registerButtonPressed(ActionEvent event) throws IOException {
        if (validateFields()) {
            if (Objects.equals(passwordInput.getText(), retypePasswordInput.getText())) {
                mySQLConnection connection = new mySQLConnection();

                try (Connection connectionDB = connection.getConnection()) {
                    String query = "INSERT INTO student (UserName, Password, FirstName, Lastname, RFID, rfidAvailible) VALUES (?, ?, ?, ?, 0, 0)";

                    try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                        preparedStatement.setString(1, rollNumberInput.getText());
                        preparedStatement.setString(2, passwordInput.getText());
                        preparedStatement.setString(3, firstNameInput.getText());
                        preparedStatement.setString(4, lastNameInput.getText());

                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    registrationMessageLabel.setText("Error during registration");
                    return;
                }

                registrationMessageLabel.setText("Registration Successful.");


                String rollNumber = rollNumberInput.getText();
                String firstName = firstNameInput.getText();
                String lastName = lastNameInput.getText();

                go_to_home_page(event, rollNumber,firstName, lastName);

                clearFields();
            } else {
                registrationMessageLabel.setText("Passwords do not match.");
            }
        } else {
            registrationMessageLabel.setText("Please fill all the required fields.");
        }
    }

    private boolean validateFields() {
        return !rollNumberInput.getText().isBlank() && !passwordInput.getText().isBlank()
                && !retypePasswordInput.getText().isBlank() && !firstNameInput.getText().isBlank()
                && !lastNameInput.getText().isBlank();
    }

    private void clearFields() {
        rollNumberInput.clear();
        passwordInput.clear();
        retypePasswordInput.clear();
        firstNameInput.clear();
        lastNameInput.clear();
    }

    @FXML
    public void backTOLogin(ActionEvent event) throws Exception {
        Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        Main.showLoginForm(primaryStage);
    }

    private void go_to_home_page(Event event, String rollNumber, String firstName, String lastName) throws IOException {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

        Stage primaryStage = new Stage();
        StudentHomePage studentHomePage = new StudentHomePage(rollNumber, firstName, lastName);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
        loader.setController(studentHomePage);
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}