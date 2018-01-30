package parkingsimulator.models;

import java.awt.*;
import java.math.BigDecimal;
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
    public BigDecimal getPrice() {
        return new BigDecimal(0);
    }

    public Color getColor(){
    	return COLOR;
    }
}