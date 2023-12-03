package com.example.javafx;

import com.example.javafx.BusData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Droute2C {
    @FXML
    public Button contbtn;
    @FXML
    private TextField inputbusid;

    @FXML
    private TableView<BusData> table1122;

    @FXML
    private TableColumn<BusData, String> busIdColumn;

    @FXML
    public TableColumn<BusData, String> Dtime;
    @FXML
    public TableColumn<BusData, String> Dep;
    @FXML
    public TableColumn<BusData, String> Dest;



    @FXML
    private TableColumn<BusData, String> totalSeatsColumn;

    public void initialize(String inputData1, String inputData2) {
        // Initialize TableView columns with data properties
        busIdColumn.setCellValueFactory(cellData -> cellData.getValue().busIDProperty());
        Dtime.setCellValueFactory(cellData -> cellData.getValue().departureTimeProperty());
        Dep.setCellValueFactory(cellData -> cellData.getValue().departureProperty());
        Dest.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        totalSeatsColumn.setCellValueFactory(cellData -> cellData.getValue().totalSeatsProperty());

        // Fetch data and populate TableView
        populateTable(inputData1, inputData2);
    }

    public void populateTable(String inputData1, String inputData2) {
        // MySQL database credentials and connection URLs
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/sda_project";
        String username = "root";
        String password = "8686";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query =  "SELECT rb.BusID, rb.departureTime, r.Departure, r.Destination, b.total_Seats " +
                    "FROM route r " +
                    "INNER JOIN routebus rb ON r.routeID = rb.routeID " +
                    "INNER JOIN bus b ON rb.BusID = b.BusID";


            PreparedStatement statement = connection.prepareStatement(query);
    //        statement.setString(1, inputData1);
    //        statement.setString(2, inputData2);

            ResultSet resultSet = statement.executeQuery();
            ObservableList<BusData> busDataList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int busID = resultSet.getInt("BusID");
                String busIDString = String.valueOf(busID);

                String departureTime = resultSet.getString("departureTime");
                String departure = resultSet.getString("Departure");
                String destination = resultSet.getString("Destination");
                int totalSeats = resultSet.getInt("total_Seats");
                String busSeats = String.valueOf(totalSeats);

                // Do something with retrieved data, like printing
                BusData busData = new BusData(busIDString,departureTime,departure,destination,busSeats);
                busDataList.add(busData);

            }
            table1122.setItems(busDataList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void  ctnbtmAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatConfirmation.fxml"));
        Parent root = loader.load();

        // Access the controller associated with the next FXML
        SeatConfirmation nextController = loader.getController();

        // Retrieve input data from inputbusid (assuming it's a TextField or some input field)
        String inputData = inputbusid.getText(); // Replace inputbusid with your actual FXID

        // Pass the input data to the seat method in the next controller
        nextController.seat(inputData);


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Optionally, close the current window
        Stage currentStage = (Stage) contbtn.getScene().getWindow();
        currentStage.close();
    }

}
