package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeatConfirmation2 {
    @FXML
    public TextArea displaytext;
    String inputData2global="0098866";
    // ... existing code ...

    public void seat(String inputData) {
        String url = "jdbc:mysql://127.0.0.1:3306/sda_project";
        String username = "root";
        String password = "8686";

        String inputData2 = "0098866"; // Or get this dynamically from your app
        displaytext.setText("Thank You "+inputData2+" For Using Our App your seat in busid "+inputData+" has been confirmed");

        String sqlQuery1 = "UPDATE bus SET total_Seats = total_Seats - 1 WHERE BusID = ?";
        String sqlQuery2 = "UPDATE reservations " +
                "INNER JOIN wallet ON reservations.stdID = wallet.std_ID " +
                "SET reservations.routeBusID = ?, reservations.price = 10, reservations.paymentcleared = 1, " +
                "wallet.balance = wallet.balance - 200 " +
                "WHERE reservations.stdID = ? AND wallet.std_ID = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement1 = connection.prepareStatement(sqlQuery1);
             PreparedStatement statement2 = connection.prepareStatement(sqlQuery2)) {

            connection.setAutoCommit(false); // Start a transaction

            // Set parameters for the first query
            statement1.setString(1, inputData);
            statement1.executeUpdate(); // Use executeUpdate() for updates

            // Set parameters for the second query
            statement2.setString(1, inputData);
            statement2.setString(2, inputData2global);
            statement2.setString(3, inputData2global);
            statement2.executeUpdate(); // Use executeUpdate() for updates

            connection.commit(); // Commit the transaction

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ... other methods ...
}
