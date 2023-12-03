//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class SeatConfirmation {
    @FXML
    public TextArea displaytext;
    String rollNumber = studentInformation.rollNumber;

    public SeatConfirmation() {
    }

    public void seat(String routeBusID_PPas) {
        String url = "jdbc:mysql://127.0.0.1:3306/sda_project";
        String username = "root";
        String password = "8686";
        this.displaytext.setText("Thank You " + rollNumber + " For Using Our App your seat in busid " + routeBusID_PPas + " has been confirmed");
        String sqlQuery1 = "UPDATE bus SET total_Seats = total_Seats - 1 WHERE BusID = ?";
        String sqlQuery2 = "INSERT INTO reservations (stdID, routeBusID, price, paymentCleared)\n" +
                "VALUES (?, ?, ?, ?);\n";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                PreparedStatement statement1 = connection.prepareStatement(sqlQuery1);

                try {
                    PreparedStatement statement2 = connection.prepareStatement(sqlQuery2);

                    try {
                        connection.setAutoCommit(false);
                        statement1.setString(1, routeBusID_PPas);
                        statement1.executeUpdate();
                        statement2.setString(1, rollNumber);
                        statement2.setString(2, routeBusID_PPas);
                        statement2.setInt(3, 10);
                        statement2.setInt(4, 1);
                        statement2.executeUpdate();
                        connection.commit();
                    } catch (Throwable var16) {
                        if (statement2 != null) {
                            try {
                                statement2.close();
                            } catch (Throwable var15) {
                                var16.addSuppressed(var15);
                            }
                        }

                        throw var16;
                    }

                    if (statement2 != null) {
                        statement2.close();
                    }
                } catch (Throwable var17) {
                    if (statement1 != null) {
                        try {
                            statement1.close();
                        } catch (Throwable var14) {
                            var17.addSuppressed(var14);
                        }
                    }

                    throw var17;
                }

                if (statement1 != null) {
                    statement1.close();
                }
            } catch (Throwable var18) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var13) {
                        var18.addSuppressed(var13);
                    }
                }

                throw var18;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var19) {
            var19.printStackTrace();
        }

    }
}
