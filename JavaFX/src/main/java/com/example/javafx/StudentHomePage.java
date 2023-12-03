package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentHomePage implements Initializable {

    @FXML
    private Label rollNumberLabel;
    @FXML
    private Button rfid_Validation_Request_button;
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;


    private String rollNumber;
    private String firstName;
    private String lastName;

    public StudentHomePage(String rollNumber, String firstName, String lastName) {
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        studentInformation.rollNumber = rollNumber;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rollNumberLabel.setText(this.rollNumber);
        firstNameLabel.setText(this.firstName);
        lastNameLabel.setText(this.lastName);
    }

    @FXML
    public void rfidValidationRequestButtonPressed(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rfidValidation_Student.fxml"));
        RfidValidationStudent rfidValidationController = new RfidValidationStudent(rollNumber, firstName, lastName);
        loader.setController(rfidValidationController);
        Parent root = loader.load();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void SeatReservationButton(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seatResrvation.fxml"));
        Parent root = loader.load();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void DisplayRoutesButton(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DrouteMainpage.fxml"));
        Parent root = loader.load();
        newStage.setScene(new Scene(root));
        newStage.show();
    }


}
