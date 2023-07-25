package com.example.townbus;

import java.util.Date;

public class Transport {
    private String transportOperator;
    private String transportFrom;
    private String transportTo;
    private String transportRouteNumber;
    private String transportDepartureDate;
    private Date transportArrivalDate;

    public Transport() {
    }

    public Transport(String transportOperator, String transportRouteNumber) {
        this.transportOperator = transportOperator;
        this.transportRouteNumber = transportRouteNumber;
    }

    public Transport(String transportOperator, String transportFrom, String transportTo, String transportRouteNumber) {
        this.transportOperator = transportOperator;
        this.transportFrom = transportFrom;
        this.transportTo = transportTo;
        this.transportRouteNumber = transportRouteNumber;
    }

    public Transport(String transportOperator, String transportFrom, String transportTo, String transportRouteNumber, String transportDepartureDate, Date transportArrivalDate) {
        this.transportOperator = transportOperator;
        this.transportFrom = transportFrom;
        this.transportTo = transportTo;
        this.transportRouteNumber = transportRouteNumber;
        this.transportDepartureDate = transportDepartureDate;
        this.transportArrivalDate = transportArrivalDate;
    }

    public String getTransportOperator() {
        return transportOperator;
    }

    public void setTransportOperator(String transportOperator) {
        this.transportOperator = transportOperator;
    }

    public String getTransportFrom() {
        return transportFrom;
    }

    public void setTransportFrom(String transportFrom) {
        this.transportFrom = transportFrom;
    }

    public String getTransportTo() {
        return transportTo;
    }

    public void setTransportTo(String transportTo) {
        this.transportTo = transportTo;
    }

    public String getTransportRouteNumber() {
        return transportRouteNumber;
    }

    public void setTransportRouteNumber(String transportRouteNumber) {
        this.transportRouteNumber = transportRouteNumber;
    }

    public String getTransportDepartureDate() {
        return transportDepartureDate;
    }

    public void setTransportDepartureDate(String transportDepartureDate) {
        this.transportDepartureDate = transportDepartureDate;
    }

    public Date getTransportArrivalDate() {
        return transportArrivalDate;
    }

    public void setTransportArrivalDate(Date transportArrivalDate) {
        this.transportArrivalDate = transportArrivalDate;
    }
}
