package parkingsimulator.models;

import java.awt.*;
import java.util.ArrayList;

public abstract class Car {

    private ArrayList<Location> locations;
    private Location location;
    private int minutesLeft;
    private double priceToPay;

    private boolean isDoubleParked;
    private boolean isPaying;
    private boolean hasToPay;
    private double feePerHour;


    /**
     * Constructor for objects of class Car
     */

    public Car() {
        this.feePerHour = 10;
        locations = new ArrayList<>();
    }



    public void setIsDoubleParked (boolean b, double fine) {
        if (b && !isDoubleParked){
            isDoubleParked = true;
            priceToPay += fine;
        }else {
            isDoubleParked = false;
        }
    }


    public boolean isDoubleParked() {
        return isDoubleParked;
    }

    /**
     * Gives the primary location of the car.
     * @return The location.
     */
    public Location getPrimaryLocation() {
        return locations.get(0);
    }

    /**
     * Gives the secondary location of the car.
     * @return The location.
     */
    public Location getSecondaryLocation() {
        return locations.get(1);
    }

    public abstract double getPrice();

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void setPriceToPay(double priceToPay) {
        this.priceToPay = priceToPay;
    }

    public double getPriceToPay(){
        return priceToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();

    public double getFeePerHour() {
        return feePerHour;
    }
}