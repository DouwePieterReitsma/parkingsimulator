package parkingsimulator.models;

import java.util.Calendar;

public class Reservation extends Model
{
    private Calendar beginTime;
    private Calendar endTime;
    private Location location;

    /**
     * This class represents a reservation in the parking garage
     * @param location
     * @param beginTime
     * @param endTime
     */
    public Reservation(Location location, Calendar beginTime, Calendar endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }
}
