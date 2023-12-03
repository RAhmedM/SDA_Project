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
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Scanner;


public class AdminHomePage {

    @FXML
    public void verifyRequestButtonPressed(ActionEvent event) throws Exception {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        main.show_rfidValidationAdmin(primaryStage);
    }

    @FXML
    public void manageRoutesButtonPressed(ActionEvent event) throws Exception {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        main.show_ManageRoutes(primaryStage);
    }


}
