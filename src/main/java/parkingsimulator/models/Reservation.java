package parkingsimulator.models;

import java.util.Calendar;

public class Reservation extends AbstractModel {
    private Calendar begin;
    private Calendar end;
    private Location location;

    /**
     * This class represents a reservation in the parking garage
     *
     * @param location
     * @param begin
     * @param end
     */
    public Reservation(Location location, Calendar begin, Calendar end) {
        this.begin = begin;
        this.end = end;
        this.location = location;
    }

    public Calendar getBegin() {
        return begin;
    }

    public Calendar getEnd() {
        return end;
    }

    public Location getLocation() {
        return location;
    }

    public boolean overlapsWith(Reservation reservation) {
        if (!location.equals(reservation.location))
            return false;

        return end.before(reservation.getBegin()) || begin.after(reservation.getEnd());
    }
}
