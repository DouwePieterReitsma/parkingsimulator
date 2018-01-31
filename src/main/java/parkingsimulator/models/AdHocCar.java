package parkingsimulator.models;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Random;

public class AdHocCar extends Car {
    private static final Color COLOR = Color.red;
    private int stayMinutes;

    public AdHocCar() {
        Random random = new Random();
        stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public BigDecimal getPrice() {
        double hours = Math.ceil((float) stayMinutes / 60.0f);

        return getFeePerHour().multiply(new BigDecimal(hours));
    }

    public Color getColor() {
        return COLOR;
    }
}