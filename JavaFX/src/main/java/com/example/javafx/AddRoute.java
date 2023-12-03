package com.example.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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


    private void insertData() {
        String url = "jdbc:mysql://localhost:3306/sda_project";
        String username = "root";
        String password = "8686";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Disable auto-commit to treat both inserts as part of the same transaction
            connection.setAutoCommit(false);

            String insertRouteQuery = "INSERT INTO route (Departure, Destination) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertRouteQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, startText.getText());
                preparedStatement.setString(2, destinationText.getText());
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int routeID = generatedKeys.getInt(1);

                        String insertRouteBusQuery = "INSERT INTO routebus (routeID, busID, departureTime) VALUES (?, ?, ?)";
                        try (PreparedStatement routeBusStatement = connection.prepareStatement(insertRouteBusQuery)) {
                            routeBusStatement.setInt(1, routeID);
                            routeBusStatement.setInt(2, busSelected); // Replace with your actual busID
                            routeBusStatement.setString(3, timeText.getText());
                            routeBusStatement.executeUpdate();
                        }
                    } else {
                        System.out.println("Failed to retrieve routeID.");
                    }
                }
            }

            // Commit the transaction
            connection.commit();
            connection.setAutoCommit(true);

            System.out.println("Data inserted successfully.");

        } catch (Exception e) {
            // Rollback the transaction in case of an exception
            e.printStackTrace();

        }
    }

    public void insertRoute()
    {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = connection.getConnection()) {
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



    @FXML

    public void addRouteButtonPressed(javafx.event.ActionEvent event) {
        insertData();
    }
}
