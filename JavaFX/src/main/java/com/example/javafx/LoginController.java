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
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Scanner;

public class LoginController implements Initializable {
    @FXML
    private Hyperlink registrationHyperLink;
    @FXML
    private Button buttonLoginTop;
    @FXML
    public Button rfidLogin;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessage;
    @FXML
    private ImageView leftpanelimage;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("D:\\Semester 5\\SDA\\Project\\Images\\login_Side_pannel.png");
        Image leftPannel = new Image(brandingFile.toURI().toString());
        leftpanelimage.setImage(leftPannel);
    }

    @FXML
    private void rfidLoginButtonPressed(ActionEvent event) throws SQLException, IOException {
        String cardNumber;
        SerialPort serialPort = SerialPort.getCommPort("COM3");

        if (serialPort.openPort()) {
            System.out.println("Serial port opened successfully.");

            Scanner scanner = new Scanner(System.in);

            while (true) {
               if (serialPort.bytesAvailable() > 0) {
                    byte[] readBuffer = new byte[serialPort.bytesAvailable()];
                    int numRead = serialPort.readBytes(readBuffer, readBuffer.length);

                    if (numRead > 0) {
                        cardNumber = new String(readBuffer).trim();
                        System.out.println(cardNumber);
                        if(rfidValidation(cardNumber))
                        {
                            break;
                        }
                    }
               }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String rfid = cardNumber;
            String rollNumber = getUserNamewithRFID(rfid);
            String firstName = getFirstName(rollNumber);
            String lastName = getLastName(rollNumber);

            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

            Stage primaryStage = new Stage();
            StudentHomePage studentHomePage = new StudentHomePage(rollNumber, firstName, lastName);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
            loader.setController(studentHomePage);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } else {
            System.out.println("Error opening serial port.");
        }
    }

    @FXML
    private void loginButtonPressed(ActionEvent event) throws Exception {

        if (!usernameText.getText().isBlank() && !passwordText.getText().isBlank())
        {
            if (loginValidation())
            {
                String rollNumber = usernameText.getText();
                String firstName = getFirstName(rollNumber);
                String lastName = getLastName(rollNumber);

                Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginStage.close();

                Stage primaryStage = new Stage();
                StudentHomePage studentHomePage = new StudentHomePage(rollNumber, firstName, lastName);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentHomePage.fxml"));
                loader.setController(studentHomePage);
                Parent root = loader.load();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }
        }
        else {
            loginMessage.setText("Please fill the required fields");
        }
    }

    private boolean loginValidation() {
        try (Connection connectionDB = new mySQLConnection().getConnection()) {
            String query = "SELECT count(1) FROM student WHERE UserName = ? AND password = ?";

            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {
                preparedStatement.setString(1, usernameText.getText());
                preparedStatement.setString(2, passwordText.getText());

                try (ResultSet queryResult = preparedStatement.executeQuery()) {
                    while (queryResult.next()) {
                        if (queryResult.getInt(1) == 1) {
                            loginMessage.setText("Login successful");
                            return true;
                        } else {
                            loginMessage.setText("Invalid login");
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            loginMessage.setText("Error during login");
        }
        return false;
    }

    public boolean rfidValidation(String rfid) throws SQLException {
        mySQLConnection connection = new mySQLConnection();

        try (Connection connectionDB = connection.getConnection()) {
            String sql = "SELECT RFID FROM student WHERE RFID = ?  and rfidAvailible = 1";

            try (PreparedStatement preparedStatement = connectionDB.prepareStatement(sql)) {
                preparedStatement.setString(1, rfid);
                ResultSet queryResult = preparedStatement.executeQuery();

                return queryResult.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void hyperlinkClicked(ActionEvent event) throws Exception {
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        Main.showRegistrationForm(primaryStage);
    }


    private String getFirstName(String rollNumber) throws SQLException {
        String query = "SELECT FirstName FROM student WHERE UserName = ? ";

        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            preparedStatement.setString(1, rollNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("FirstName");
                }
            }
        }

        return null;
    }

    private String getLastName(String rollNumber) throws SQLException {
        String query = "SELECT LastName FROM student WHERE UserName = ?";

        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            preparedStatement.setString(1, rollNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("LastName");
                }
            }
        }

        return null;
    }

    private String getUserNamewithRFID(String rfid) throws SQLException {
        String query = "SELECT UserName FROM student WHERE RFID = ?";

        try (Connection connectionDB = new mySQLConnection().getConnection();
             PreparedStatement preparedStatement = connectionDB.prepareStatement(query)) {

            preparedStatement.setString(1, rfid);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("UserName");
                }
            }
        }

        return null;
    }
}
