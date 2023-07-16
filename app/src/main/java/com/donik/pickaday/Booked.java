package com.donik.pickaday;

public class Booked {

    public String appointmentDateTime,appointmentMessage ,barberName, serviceName, docID, bookedByID;
    public long barberID, serviceID,eventID;

    public Booked(String appointmentDateTime, String appointmentMessage, String barberName, String serviceName, long barberID, String bookedByID, long serviceID, long eventID) {
        this.appointmentDateTime = appointmentDateTime;
        this.appointmentMessage = appointmentMessage;
        this.barberName = barberName;
        this.serviceName = serviceName;
        this.barberID = barberID;
        this.bookedByID = bookedByID;
        this.serviceID = serviceID;
        this.eventID =eventID;
    }

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public Booked() {
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getAppointmentMessage() {
        return appointmentMessage;
    }

    public void setAppointmentMessage(String appointmentMessage) {
        this.appointmentMessage = appointmentMessage;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getBarberID() {
        return barberID;
    }

    public void setBarberID(long barberID) {
        this.barberID = barberID;
    }

    public String getBookedByID() {
        return bookedByID;
    }

    public void setBookedByID(String bookedByID) {
        this.bookedByID = bookedByID;
    }

    public long getServiceID() {
        return serviceID;
    }

    public void setServiceID(long serviceID) {
        this.serviceID = serviceID;
    }
}
