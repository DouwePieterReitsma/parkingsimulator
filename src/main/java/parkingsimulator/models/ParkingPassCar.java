package parkingsimulator.models;

import java.awt.*;
import java.util.Random;

public class ParkingPassCar extends Car
{
	private static final Color COLOR = Color.blue;
	
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    @Override
    public double getPrice() {
        return 0;
    }

    public Color getColor(){
    	return COLOR;
    }
}