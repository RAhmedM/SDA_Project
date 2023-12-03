package com.example.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddRoute implements Initializable {
    @FXML
    private TextField startText;
    @FXML
    private TextField destinationText;
    @FXML
    private ListView<Integer> busList;
    @FXML
    private TextField timeText;

    private int busSelected;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBusData(busList, getBusData());

        busList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> arg0, Integer o, Integer t1) {
                Object obj = busList.getSelectionModel().getSelectedItem();
                if (obj != null)
                {
                    busSelected =  Integer.parseInt(obj.toString());
                    System.out.println(busSelected);

                }

            }
        });
    }

    private static void displayBusData(ListView<Integer> busList, int[] busData) {
        List<Integer> dataList = Arrays.stream(busData).boxed().collect(Collectors.toList());

        ObservableList<Integer> items = FXCollections.observableArrayList();
        items.addAll(dataList);

        busList.setItems(items);
    }
    private int[] getBusData()
    {
        String query = "SELECT * FROM bus";

        List<Integer> data = new ArrayList<>();


        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer bus = resultSet.getInt("BusID");
                    data.add(bus);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data.stream().mapToInt(Integer::intValue).toArray();
    }

    public void insertRoute()
    {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = mySQLConnection.getConnection()) {
            String query = "INSERT INTO route (Departure, Destination) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, startText.getText());
                preparedStatement.setString(2, destinationText.getText());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = connection.getConnection()) {
            String query = "SELECT routeID FROM route WHERE Departure = ? AND Destination = ?";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, startText.getText());
                preparedStatement.setString(2, destinationText.getText());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println(resultSet.getInt("routeID"));
                        return resultSet.getInt("routeID");
                    } else {
                        // Handle the case when no routeID is found
                        return 0; // or throw an exception, or return a special value
                    }
                }
            }
        } catch (SQLException e) {
            // Handle the exception more appropriately, e.g., log it
            e.printStackTrace();
            return 0; // or throw an exception, or return a special value
        }
    }


    public void setRouteBuS(Integer route_id) {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = mySQLConnection.getConnection()) {
            String query = "INSERT INTO routebus (routeID, busID, departureTime) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setInt(1, route_id);
                preparedStatement.setInt(2, busSelected);
                preparedStatement.setString(3, timeText.getText());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void addRouteButtonPressed(javafx.event.ActionEvent event) {
        insertRoute();
        setRouteBuS(getID());
    }
}
