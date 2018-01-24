package parkingsimulator.models;

import java.awt.*;
import java.util.Random;

public class AdHocCar extends Car
{
	private static final Color COLOR = Color.red;
    private int stayMinutes;

    public AdHocCar() {
        Random random = new Random();
        stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public double getPrice() {
        return getFeePerHour() / 60 * stayMinutes;
    }

    public Color getColor(){
    	return COLOR;
    }
}