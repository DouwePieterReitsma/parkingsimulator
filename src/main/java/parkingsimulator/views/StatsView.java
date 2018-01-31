package parkingsimulator.views;

import parkingsimulator.models.*;

import javax.swing.*;
import java.awt.*;

public class StatsView extends AbstractView {
    private JLabel entranceQueue;
    private int entranceQueueSize;

    public StatsView(SimulatorViewModel model) {
        entranceQueueSize = model.getEntranceCarQueueSize();

        entranceQueue = new JLabel("Entrance queue: ");

        this.setLayout(new GridLayout(1,1));
        add(entranceQueue);
    }

    public void updateView() {
        entranceQueue.setText("Entrance queue: " + String.valueOf(entranceQueueSize));
    }
}
