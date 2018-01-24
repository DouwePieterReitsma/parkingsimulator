package parkingsimulator.models;

public class Location extends Model {

    private int floor;
    private int row;
    private int place;
    private boolean forPass;
    private boolean forSubscriber;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.forPass = false;
        this.forSubscriber = false;
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
     * Get whether or not a location is reserved for pass
     * @return Whether or not a location is reserved
     */

    public boolean isForPass() {
        return forPass;
    }

    /**
    * Set location for pass
    */

    public void setForPass(){
        forPass = true;
    }

    /**
     * Get whether or not a location is reserved for subscriber
     * @return Whether or not a location is reserved
     */
    public boolean isForSubscriber() {
        return forSubscriber;
    }
    /**
     * Set for subscriber
     */
    public void setForSubscriber(){
        forSubscriber = true;
    }

    /**
     * Unmark the location as reserved
     */
    public void unReserve(){
        forPass = false;
        forSubscriber = false;
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

}