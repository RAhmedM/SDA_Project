//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.javafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class displayroutescontroller {
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

    public displayroutescontroller() {
    }

    public void initialize(String inputData1, String inputData2) {
        this.busIdColumn.setCellValueFactory((cellData) -> {
            return ((BusData)cellData.getValue()).busIDProperty();
        });
        this.Dtime.setCellValueFactory((cellData) -> {
            return ((BusData)cellData.getValue()).departureTimeProperty();
        });
        this.Dep.setCellValueFactory((cellData) -> {
            return ((BusData)cellData.getValue()).departureProperty();
        });
        this.Dest.setCellValueFactory((cellData) -> {
            return ((BusData)cellData.getValue()).destinationProperty();
        });
        this.totalSeatsColumn.setCellValueFactory((cellData) -> {
            return ((BusData)cellData.getValue()).totalSeatsProperty();
        });
        this.populateTable(inputData1, inputData2);
    }

    public void populateTable(String inputData1, String inputData2) {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/sda_project";
        String username = "root";
        String password = "8686";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            try {
                String query = "SELECT rb.BusID, rb.departureTime, r.Departure, r.Destination, b.total_Seats FROM route r INNER JOIN routebus rb ON r.routeID = rb.routeID INNER JOIN bus b ON rb.BusID = b.BusID WHERE r.Departure = ? AND r.Destination = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, inputData1);
                statement.setString(2, inputData2);
                ResultSet resultSet = statement.executeQuery();
                ObservableList<BusData> busDataList = FXCollections.observableArrayList();

                while(true) {
                    if (!resultSet.next()) {
                        this.table1122.setItems(busDataList);
                        break;
                    }

                    int busID = resultSet.getInt("BusID");
                    String busIDString = String.valueOf(busID);
                    String departureTime = resultSet.getString("departureTime");
                    String departure = resultSet.getString("Departure");
                    String destination = resultSet.getString("Destination");
                    int totalSeats = resultSet.getInt("total_Seats");
                    String busSeats = String.valueOf(totalSeats);
                    BusData busData = new BusData(busIDString, departureTime, departure, destination, busSeats);
                    busDataList.add(busData);
                }
            } catch (Throwable var20) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var19) {
                        var20.addSuppressed(var19);
                    }
                }

                throw var20;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var21) {
            var21.printStackTrace();
        }

    }

    public void ctnbtmAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("SeatConfirmation.fxml"));
        Parent root = (Parent)loader.load();
        SeatConfirmation nextController = (SeatConfirmation)loader.getController();
        String inputData = this.inputbusid.getText();
        nextController.seat(inputData);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage)this.contbtn.getScene().getWindow();
        currentStage.close();
    }
}
