package parkingsimulator.views;

import parkingsimulator.models.*;
import java.math.BigDecimal;
import javax.swing.*;
import java.awt.*;

public class StatsView extends AbstractView {
    private JLabel entranceQueue;
    private int entranceQueueSize;
    private JLabel totalRevenue;
    private double revenueTotal;
    private SimulatorViewModel model;

    public StatsView(SimulatorViewModel model) {
        this.model = model;
        entranceQueueSize = model.getEntranceCarQueueSize();
        entranceQueue = new JLabel("Entrance queue: ");
        totalRevenue = new JLabel("Total revenue: €0.00");

        this.setLayout(new FlowLayout());
        add(entranceQueue);
        add(totalRevenue);
    }

    public void updateView() {
        entranceQueue.setText("Entrance queue: " + String.valueOf(entranceQueueSize = model.getEntranceCarQueueSize()));
        totalRevenue.setText("Total revenue: €" + String.valueOf(revenueTotal = model.getRevenue().doubleValue()));
    }
}
