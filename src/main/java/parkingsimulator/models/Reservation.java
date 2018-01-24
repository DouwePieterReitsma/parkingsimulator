package parkingsimulator.models;

public class Reservation extends Model
{
    private int beginTime;
    private int endTime;
    private Location location;

    /**
     * This class represents a reservation in the parking garage
     * @param location
     * @param beginTime
     * @param endTime
     */
    public Reservation(Location location, int beginTime, int endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }
}
