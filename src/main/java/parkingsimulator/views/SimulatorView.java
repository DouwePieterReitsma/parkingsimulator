package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;
import javax.swing.*;
import java.awt.*;

public class SimulatorView extends AbstractView {
    private Container contentPane;
    private JFrame screen;

    public SimulatorView(CarParkView carParkView, SimulatorController controller, StatsView statsView) {
        screen=new JFrame("Parking Simulator");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = screen.getContentPane();
        contentPane.setLayout(new GridLayout(2,2));
        contentPane.add(carParkView);
        contentPane.add(controller);
        contentPane.add(statsView);

        screen.pack();
        screen.setVisible(true);
    }
}