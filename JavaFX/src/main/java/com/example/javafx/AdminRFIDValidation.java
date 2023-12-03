package com.example.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminRFIDValidation implements Initializable {
    @FXML
    private ListView lc;
    @FXML
    private Label reasons;
    @FXML
    private Label verificationResponse;
    private String rn;
    private String rf;
    private Button verifyButton;
    String currentStudent;
    public void setListView()
    {
        lc.getItems().addAll(getRequestsFromDB());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListView();

        lc.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String o, String t1) {
                Object obj = lc.getSelectionModel().getSelectedItem();
                if (obj != null)
                {
                    currentStudent = obj.toString();
                    //System.out.println(currentStudent);

                    reasons.setText(getReason(currentStudent));
                }

            }
        });
    }

    private String[] getRequestsFromDB()
    {
        String query = "SELECT * FROM rfid_validation_request";

        List<String> data = new ArrayList<>();


        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String rollNumber = resultSet.getString("rollNumber");
                    data.add(rollNumber);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data.toArray(new String[0]);
    }
    private String getReason(String cs)
    {
        String query = "SELECT * FROM rfid_validation_request where rollNumber = ?";

        List<String> data = new ArrayList<>();
        String reason = new String();

        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
            preparedStatement.setString(1, cs);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {


                    reason = resultSet.getString("reason");
                    rn = resultSet.getString("rollNumber");
                    rf = resultSet.getString("rfid");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reason;
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
                    verificationResponse.setText("Card verified");
                } else {
                    System.out.println("Roll number not found. No update performed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void deleteRequest(String rollNumber) {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = connection.getConnection()) {
            String query = "DELETE FROM rfid_validation_request WHERE rollNumber = ?";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, rollNumber);

                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @FXML
    public void verifyButtonPressed(ActionEvent event)
    {
        updateRFIDInfo(rn, rf);
        deleteRequest(rn);

        setListView();

        lc.getSelectionModel().clearSelection();
        reasons.setText("");
    }

}
