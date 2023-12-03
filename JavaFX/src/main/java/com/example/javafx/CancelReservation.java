package com.example.javafx;


import com.fazecast.jSerialComm.SerialPort;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CancelReservation implements Initializable{
    private int smpID;
    private int stdID;
    private int routeBusID;

    @FXML
    private ListView reservationList;
    public void setListView()
    {
        reservationList.getItems().addAll(getRequestsFromDB());
    }

    private String currentSelection;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListView();

        reservationList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String o, String t1) {
                Object obj = reservationList.getSelectionModel().getSelectedItem();
                if (obj != null)
                {
                    currentSelection = obj.toString();
                    System.out.println(currentSelection);
                }

            }
        });
    }

    private String[] getRequestsFromDB()
    {
        String query = "SELECT\n" +
                    "    reservations.smpID,\n" +
                "    reservations.stdID,\n" +
                "    reservations.price,\n" +
                "    reservations.paymentCleared,\n" +
                "    route.Departure,\n" +
                "    route.Destination,\n" +
                "    routebus.departureTime\n" +
                "FROM\n" +
                "    reservations\n" +
                "JOIN\n" +
                "    routebus ON reservations.routeBusID = routebus.routeBusID\n" +
                "JOIN\n" +
                "    route ON routebus.routeID = route.routeID\n" +
                "WHERE\n" +
                "    reservations.stdID = ?;";

        List<String> data = new ArrayList<>();


        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
            preparedStatement.setString(1,studentInformation.rollNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String str = "";
                    Integer smpID = resultSet.getInt("smpID");
                    Float price = resultSet.getFloat("price");
                    String start = resultSet.getString("Departure");
                    String destination = resultSet.getString("Destination");
                    String time = resultSet.getString("departureTime");

                    str += smpID.toString() + "Start: " + start + " Destination: "
                            + destination + "Time: " + time ;
                    data.add(str);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data.toArray(new String[0]);
    }




    private void deleteRecord(int routeID) {
        try (Connection connectionDB = new mySQLConnection().getConnection()) {

            String query = "DELETE FROM reservations WHERE smpID = ?";

            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setInt(1, routeID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Record deleted successfully.");
                } else {
                    System.out.println("No matching record found for routeID: " + routeID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int extractSmpID(String input) {
        // Split the input string by non-digit characters
        String[] parts = input.split("\\D+");

        // Find the first non-empty part, which should be the smpID
        for (String part : parts) {
            if (!part.isEmpty()) {
                // Convert the found part to an integer and return it
                return Integer.parseInt(part);
            }
        }

        // Return a default value (you may choose to throw an exception or handle it differently)
        return -1;
    }
    @FXML
    public void setDeleteButton(ActionEvent event)
    {
        deleteRecord(extractSmpID(currentSelection));
    }
}
