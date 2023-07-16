package com.donik.pickaday;

public class Barber {
    private long barberID;
    private String barberName;
    private double barberRate;
    private double barberRating;
    private String barberPhoto;

    public Barber() {
        // Empty constructor required for Firebase Firestore
    }

    public Barber(long barberID, String barberName, double barberRate, double barberRating, String barberPhoto) {
        this.barberID = barberID;
        this.barberName = barberName;
        this.barberRate = barberRate;
        this.barberRating = barberRating;
        this.barberPhoto = barberPhoto;
    }

    public long getBarberID() {
        return barberID;
    }

    public void setBarberID(long barberID) {
        this.barberID = barberID;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public double getBarberRate() {
        return barberRate;
    }

    public void setBarberRate(double barberRate) {
        this.barberRate = barberRate;
    }

    public double getBarberRating() {
        return barberRating;
    }

    public void setBarberRating(double barberRating) {
        this.barberRating = barberRating;
    }

    public String getBarberPhoto() {
        return barberPhoto;
    }

    public void setBarberPhoto(String barberPhoto) {
        this.barberPhoto = barberPhoto;
    }
}
