package parkingsimulator.models;

import java.awt.*;
import java.util.Random;

public class ReservationCar extends Car
{
    private static final Color COLOR = Color.green;

    private int stayMinutes;

    public ReservationCar()
    {
        Random random = new Random();
        stayMinutes = (int)(15 * random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public double getPrice()
    {
        int extraFee = 5;
        return stayMinutes * (this.getFeePerHour() / 60) + extraFee;
    }
}
