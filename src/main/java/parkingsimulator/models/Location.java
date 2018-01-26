package parkingsimulator.models;

import java.util.ArrayList;
import java.util.List;

public class Location extends AbstractModel {

    private int floor;
    private int row;
    private int place;
    private boolean forSubscriber;
    private Car car;
    private List<Reservation> reservations;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.forSubscriber = false;
        this.reservations = new ArrayList<>();
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        }
        else {
            return false;
        }
    }

    /**
     * @param car
     */
    public void setCar(Car car){
        this.car = car;
    }

    /**
     * @return The car that is parked at this location
     */
    public Car getCar()
    {
        return this.car;
    }


    /**
     * Get whether or not a location is reserved for subscribers
     * @return Whether or not a location is reserved for subscribers
     */
    public boolean isForSubscriber() {
        return forSubscriber;
    }
    /**
     * Set location for subscriber
     */
    public void setForSubscriber(boolean forSubscriber){
        this.forSubscriber = forSubscriber;
    }

    /**
     * Return a string of the form floor,row,place.
     * @return A string representation of the location.
     */
    public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The place.
     */
    public int getPlace() {
        return place;
    }

    /**
     * @return List of reservations for this location
     */
    public List<Reservation> getReservations() {
        return reservations;
    }
}