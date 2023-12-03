package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.*;
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
    @FXML
    private Label validationRequestResponse;
    @FXML
    private Label cardFoundLabel;
    @FXML
    private Label rfid_tag;
    private String cardNumber = null;
    @FXML
    public void scanButtonPressed(ActionEvent event)
    {

        cardNumber = rfidCardReader.getRfid();
        cardFoundLabel.setVisible(true);
        rfid_tag.setText(cardNumber);
    }
    @FXML
    public void sendValidationRequestButtonPressed(ActionEvent event)
    {
        String rollNumber = checkUNI_RFID(cardNumber);

        if (rollNumber != null) {
            validationRequestResponse.setText("Card Verified");
            updateRFIDInfo(rollNumber,cardNumber);
        } else {
            sendRequest();
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rollNumberLabel.setText(this.rollNumber);
        firstNameLabel.setText(this.firstName);
        lastNameLabel.setText(this.lastName);
        cardFoundLabel.setVisible(false);
    }
    @FXML
    private TextArea requestReason;
    private void sendRequest()
    {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = connection.getConnection()) {
            String query = "INSERT INTO rfid_validation_request (rollNumber, rfid, reason) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, rollNumber);
                preparedStatement.setString(2, cardNumber);
                preparedStatement.setString(3, requestReason.getText());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        validationRequestResponse.setText("Request sent to admin");
    }

    private String checkUNI_RFID(String cardNumber){
        mySQLConnection connection = new mySQLConnection();


        try (Connection connectionDB = connection.getConnection()) {
            String query = "SELECT roll_number FROM uni_rfid WHERE rfid = ?";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, this.cardNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("roll_number");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateRFIDInfo(String rollNumber, String rfid) {
        mySQLConnection connection = new mySQLConnection();


        try (Connection connectionDB = connection.getConnection()) {String query = "UPDATE student SET RFID = ?, rfidAvailible = ? WHERE UserName = ?";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, rfid);
                preparedStatement.setBoolean(2, true);
                preparedStatement.setString(3, rollNumber);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("RFID information updated successfully.");
                } else {
                    System.out.println("Roll number not found. No update performed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
