package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;
import parkingsimulator.models.SimulatorViewModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StatsView extends AbstractView {
    private JPanel panelOne;
    private JPanel panelTwo;
    private JLabel entranceCarQueue;
    private JLabel entrancePassQueue;
    private JLabel entranceReservationQueue;
    private JLabel paymentCarQueue;
    private JLabel exitCarQueue;
    private JLabel carsNotEntering;
    private int entranceCarQueueSize;
    private int entrancePassQueueSize;
    private int entranceReservationQueueSize;
    private int paymentCarQueueSize;
    private int exitCarQueueSize;
    private int carsNotEnteringSize;
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
    private SimulatorController controller;

    public StatsView(SimulatorViewModel model, SimulatorController controller) {
        this.model = model;
        this.controller = controller;
        entranceCarQueueSize = model.getEntranceCarQueueSize();
        entrancePassQueueSize = model.getEntrancePassQueueSize();
        entranceReservationQueueSize = model.getEntranceReservationQueueSize();
        paymentCarQueueSize = model.getPaymentCarQueueSize();
        exitCarQueueSize = model.getExitCarQueueSize();

        entranceCarQueue = new JLabel("Entrance Car queue: ");
        entrancePassQueue = new JLabel("Entrance Pass queue: ");
        entranceReservationQueue = new JLabel("Entrance Reservation queue: ");
        paymentCarQueue = new JLabel("Payment Car queue: ");
        exitCarQueue = new JLabel("Exit Car queue: ");
        carsNotEntering = new JLabel("Cars not entering (Long queue): ");
        totalMonday = new JLabel("Monday: €0.00");
        totalTuesday = new JLabel("Tuesday: €0.00");
        totalWednesday = new JLabel("Wednesday: €0.00");
        totalThursday = new JLabel("Thursday: €0.00");
        totalFriday = new JLabel("Friday: €0.00");
        totalSaturday = new JLabel("Saturday: €0.00");
        totalSunday = new JLabel("Sunday: €0.00");
        totalRevenue = new JLabel("Total revenue: €0.00");

        panelOne = new JPanel(new GridLayout(6,1));
        panelOne.add(entranceCarQueue);
        panelOne.add(entrancePassQueue);
        panelOne.add(entranceReservationQueue);
        panelOne.add(paymentCarQueue);
        panelOne.add(exitCarQueue);
        panelOne.add(carsNotEntering);

        panelTwo = new JPanel(new GridLayout(8,1));
        panelTwo.add(totalMonday);
        panelTwo.add(totalTuesday);
        panelTwo.add(totalWednesday);
        panelTwo.add(totalThursday);
        panelTwo.add(totalFriday);
        panelTwo.add(totalSaturday);
        panelTwo.add(totalSunday);
        panelTwo.add(totalRevenue);

        this.setLayout(new GridLayout(1,2));
        add(panelOne);
        add(panelTwo);
    }

    public void updateView() {
        entranceCarQueue.setText("Entrance Car queue: " + String.valueOf(entranceCarQueueSize = model.getEntranceCarQueueSize()));
        entrancePassQueue.setText("Entrance Pass queue: " + String.valueOf(entrancePassQueueSize = model.getEntrancePassQueueSize()));
        entranceReservationQueue.setText("Entrance Reservation queue: " + String.valueOf(entranceReservationQueueSize = model.getEntranceReservationQueueSize()));
        paymentCarQueue.setText("Payment Car queue: " + String.valueOf(paymentCarQueueSize = model.getPaymentCarQueueSize()));
        exitCarQueue.setText("Exit Car queue: " + String.valueOf(exitCarQueueSize = model.getExitCarQueueSize()));
        carsNotEntering.setText("Cars not entering (Long queue): " + String.valueOf(carsNotEnteringSize = controller.getCarsNotEntering()));
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
