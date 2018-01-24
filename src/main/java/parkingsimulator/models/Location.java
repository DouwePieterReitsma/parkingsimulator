package parkingsimulator.models;

public class Location extends Model {

    private int floor;
    private int row;
    private int place;
    private boolean forSubscriber;
    private int reservationBeginTimeInMinutes;
    private int reservationEndTimeInMinutes;
    private boolean isReserved;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.forSubscriber = false;
        this.isReserved = false;
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
     * Reserve this location
     * @param beginTimeInMinutes
     * @param endTimeInMinutes
     */
    public void reserve(int beginTimeInMinutes, int endTimeInMinutes)
    {
        this.reservationBeginTimeInMinutes = beginTimeInMinutes;
        this.reservationEndTimeInMinutes = endTimeInMinutes;
        this.isReserved = true;
    }

    /**
     * Unmark the location as reserved
     */
    public void unReserve(){
        isReserved = false;
        reservationBeginTimeInMinutes = 0;
        reservationEndTimeInMinutes = 0;
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}