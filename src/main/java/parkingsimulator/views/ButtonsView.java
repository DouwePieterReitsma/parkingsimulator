package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsView extends AbstractView implements ActionListener {
    private JButton oneMinute;
    private JButton hundredMinutes;
    private JButton toggleSimulation;

    private SimulatorController controller;

    public ButtonsView(SimulatorController controller) {
        this.controller = controller;

        oneMinute = new JButton("1 minute");
        oneMinute.addActionListener(this);
        hundredMinutes = new JButton("100 minutes");
        hundredMinutes.addActionListener(this);
        toggleSimulation = new JButton("Start/ stop");
        toggleSimulation.addActionListener(this);

        this.setLayout(new FlowLayout());
        this.add(oneMinute);
        this.add(hundredMinutes);
        this.add(toggleSimulation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thread = new Thread(() -> {
            if (e.getSource() == oneMinute) controller.run(1);
            if (e.getSource() == hundredMinutes) controller.run(100);
            if (e.getSource() == toggleSimulation) controller.toggle();
        });

        thread.start();
    }
}
