package parkingsimulator.models;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Random;

public class ReservationCar extends Car {
    private static final Color COLOR = Color.getHSBColor(120, 100, 50);

    private int stayMinutes;

    public ReservationCar() {
        Random random = new Random();
        stayMinutes = (int) (15 * random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public BigDecimal getPrice() {
        BigDecimal extraFee = new BigDecimal(5);
        double hours = Math.ceil(stayMinutes / 60.0f);

        return getFeePerHour().multiply(new BigDecimal(hours)).add(extraFee);
    }
}
