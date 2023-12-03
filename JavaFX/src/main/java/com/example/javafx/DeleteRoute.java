package com.example.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteRoute implements Initializable {

    @FXML
    private Label message;

    @FXML
    private Button deletBut;
    @FXML
    private ListView<String> routeBusList;
    public void setListView()
    {
        routeBusList.getItems().addAll(getRequestsFromDB());
    }

    private String currentSelection;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListView();

        routeBusList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String o, String t1) {
                Object obj = routeBusList.getSelectionModel().getSelectedItem();
                if (obj != null)
                {
                    currentSelection = obj.toString();
                }

            }
        });
    }

    private String[] getRequestsFromDB()
    {
        String query = "SELECT * FROM routebus";

        List<String> data = new ArrayList<>();


        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer routeID = resultSet.getInt("routeID");
                    String str[] = getRouteInfo(routeID);
                    String routeBusID = resultSet.getString("routeBusID");
                    String time = resultSet.getString("departureTime");
                    Integer busID = resultSet.getInt("busID");
                    routeBusID = routeBusID + " Start: " + str[0] + " Destination: " + str[1] + " Bus: " +  busID.toString();
                    data.add(routeBusID);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data.toArray(new String[0]);
    }
    private String[] getRouteInfo(int routeID) {
        String[] result = new String[2];

        try (Connection connectionDB = new mySQLConnection().getConnection();) {
            String query = "SELECT departure, destination FROM route WHERE routeID = ?";
            try ( PreparedStatement preparedStatement = connectionDB.prepareStatement(query))  {
                preparedStatement.setInt(1, routeID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result[0] = resultSet.getString("departure");
                        result[1] = resultSet.getString("destination");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }

        return result;
    }

    private void deleteRecord(int routeID) {
        try (Connection connectionDB = new mySQLConnection().getConnection()) {

            String query = "DELETE FROM routeBus WHERE routeBusID = ?";

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


    public static Integer parseRouteBusID(String inputString) {
        // Define the pattern for extracting routeBusID
        Pattern pattern = Pattern.compile("(\\d+)\\s+Start:");

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(inputString);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract the matched routeBusID group
            String routeBusIDStr = matcher.group(1);

            // Parse the routeBusID string to Integer
            try {
                return Integer.parseInt(routeBusIDStr);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing routeBusID: " + e.getMessage());
            }
        }

        // Return null if no match is found
        return null;
    }
    @FXML
    public void setDeleteButton(ActionEvent event)
    {
        deleteRecord(parseRouteBusID(currentSelection));
    }
}
