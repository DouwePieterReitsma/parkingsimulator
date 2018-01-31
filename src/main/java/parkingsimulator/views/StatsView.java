package parkingsimulator.views;

import parkingsimulator.models.*;
import java.math.BigDecimal;
import javax.swing.*;
import java.awt.*;

public class StatsView extends AbstractView {
    private JLabel entranceQueue;
    private int entranceQueueSize;
    private JLabel totalMonday;
    private JLabel totalTuesday;
    private JLabel totalWednesday;
    private JLabel totalThursday;
    private JLabel totalFriday;
    private JLabel totalSaturday;
    private JLabel totalSunday;
    private JLabel totalRevenue;
    private double mondayRevenue;
    private double tuesdayRevenue;
    private double wednesdayRevenue;
    private double thursdayRevenue;
    private double fridayRevenue;
    private double saturdayRevenue;
    private double sundayRevenue;
    private double revenueTotal;
    private SimulatorViewModel model;

    public StatsView(SimulatorViewModel model) {
        this.model = model;
        entranceQueueSize = model.getEntranceCarQueueSize();
        entranceQueue = new JLabel("Entrance queue: ");
        totalMonday = new JLabel("Monday: €0.00");
        totalTuesday = new JLabel("Tuesday: €0.00");
        totalWednesday = new JLabel("Wednesday: €0.00");
        totalThursday = new JLabel("Thursday: €0.00");
        totalFriday = new JLabel("Friday: €0.00");
        totalSaturday = new JLabel("Saturday: €0.00");
        totalSunday = new JLabel("Sunday: €0.00");
        totalRevenue = new JLabel("Total revenue: €0.00");

        this.setLayout(new FlowLayout());
        add(entranceQueue);
        add(totalMonday);
        add(totalTuesday);
        add(totalWednesday);
        add(totalThursday);
        add(totalFriday);
        add(totalSaturday);
        add(totalSunday);
        add(totalRevenue);
    }

    public void updateView() {
        entranceQueue.setText("Entrance queue: " + String.valueOf(entranceQueueSize = model.getEntranceCarQueueSize()));
        totalMonday.setText("Monday: €" + String.valueOf(mondayRevenue = model.getRevenueMonday().doubleValue()));
        totalTuesday.setText("Tuesday: €" + String.valueOf(tuesdayRevenue = model.getRevenueTuesday().doubleValue()));
        totalWednesday.setText("Wednesday: €" + String.valueOf(wednesdayRevenue = model.getRevenueWednesday().doubleValue()));
        totalThursday.setText("Thursday: €" + String.valueOf(thursdayRevenue = model.getRevenueThursday().doubleValue()));
        totalFriday.setText("Friday: €" + String.valueOf(fridayRevenue = model.getRevenueFriday().doubleValue()));
        totalSaturday.setText("Saturday: €" + String.valueOf(saturdayRevenue = model.getRevenueSaturday().doubleValue()));
        totalSunday.setText("Sunday: €" + String.valueOf(sundayRevenue = model.getRevenueSunday().doubleValue()));
        totalRevenue.setText("Total revenue: €" + String.valueOf(revenueTotal = model.getRevenue().doubleValue()));
    }
}
