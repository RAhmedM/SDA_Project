package com.example.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageRoutes {
    private int routeID;
    private String departure;
    private String destinaiton;


    @FXML
    public void addRoutesButtonPressed(ActionEvent event) throws Exception {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        main.show_AddRoutes(primaryStage);
    }
    @FXML
    public void deleteRoutesButtonPressed(ActionEvent event) throws Exception {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        main.show_ManageRoutes(primaryStage);
    }

    private String[] getRouteData()
    {
        String query = "SELECT * FROM route";

        List<String> data = new ArrayList<>();


        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int routeID = resultSet.getInt("routeID");
                    String dep = resultSet.getString("Departure");
                    String dest = resultSet.getString("Destinaiton");

                    String str = dep + " to " + dest;
                    data.add(str);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data.toArray(new String[0]);
    }



}
