package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RfidValidationStudent implements Initializable {
    @FXML
    private Label rollNumberLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;
    private String rollNumber;
    private String firstName;
    private String lastName;

    public RfidValidationStudent(String rollNumber, String firstName, String lastName) {
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RfidValidationStudent() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rollNumberLabel.setText(this.rollNumber);
        firstNameLabel.setText(this.firstName);
        lastNameLabel.setText(this.lastName);
    }
}
