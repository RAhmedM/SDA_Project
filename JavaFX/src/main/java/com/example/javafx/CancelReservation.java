package com.example.javafx;


import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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


public class CancelReservation {
    private int smpID;
    private int stdID;
    private int routeBusID;
    private String getData(String cs)
    {
        String query = "SELECT * FROM reservations where rollNumber = ?";

        List<String> data = new ArrayList<>();
        String reason = new String();

        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
            preparedStatement.setString(1, cs);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {


                    reason = resultSet.getString("reason");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reason;
    }
}
