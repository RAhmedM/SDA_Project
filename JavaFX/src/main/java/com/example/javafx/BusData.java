//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BusData {
    private final StringProperty busID;
    private final StringProperty departureTime;
    private final StringProperty departure;
    private final StringProperty destination;
    private final StringProperty totalSeats;

    public BusData(String busID, String departureTime, String departure, String destination, String totalSeats) {
        this.busID = new SimpleStringProperty(busID);
        this.departureTime = new SimpleStringProperty(departureTime);
        this.departure = new SimpleStringProperty(departure);
        this.destination = new SimpleStringProperty(destination);
        this.totalSeats = new SimpleStringProperty(totalSeats);
    }

    public StringProperty busIDProperty() {
        return this.busID;
    }

    public StringProperty departureTimeProperty() {
        return this.departureTime;
    }

    public StringProperty departureProperty() {
        return this.departure;
    }

    public StringProperty destinationProperty() {
        return this.destination;
    }

    public StringProperty totalSeatsProperty() {
        return this.totalSeats;
    }
}
