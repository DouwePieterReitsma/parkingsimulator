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
    private JButton slowerSimulation;
    private JButton fasterSimulation;
    private JLabel tickPause;
    private SimulatorController controller;

    public ButtonsView(SimulatorController controller) {
        this.controller = controller;

        oneMinute = new JButton("1 minute");
        oneMinute.addActionListener(this);
        hundredMinutes = new JButton("100 minutes");
        hundredMinutes.addActionListener(this);
        toggleSimulation = new JButton("Start/ stop");
        toggleSimulation.addActionListener(this);
        slowerSimulation = new JButton("Speed /2");
        slowerSimulation.addActionListener(this);
        fasterSimulation = new JButton("Speed x2");
        fasterSimulation.addActionListener(this);
        tickPause = new JLabel("Tick pause " + controller.getTickPause());

        this.setLayout(new FlowLayout());
        this.add(oneMinute);
        this.add(hundredMinutes);
        this.add(toggleSimulation);
        this.add(slowerSimulation);
        this.add(fasterSimulation);
        this.add(tickPause);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thread = new Thread(() -> {
            if (e.getSource() == oneMinute) controller.run(1);
            if (e.getSource() == hundredMinutes) controller.run(100);
            if (e.getSource() == toggleSimulation) controller.toggle();
            if (e.getSource() == slowerSimulation) {
                controller.setTickPause(1);
                tickPause.setText("Tick pause " + controller.getTickPause());
            }
            if (e.getSource() == fasterSimulation) {
                controller.setTickPause(2);
                tickPause.setText("Tick pause " + controller.getTickPause());
            }
        });

        thread.start();
    }
}
