package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsView extends AbstractView implements ActionListener {
    private JButton oneMinute;
    private JButton hundredMinutes;
    private JButton stopSimulating;

    private SimulatorController controller;

    public ButtonsView(SimulatorController controller){
        this.controller = controller;

        oneMinute = new JButton("1 minute");
        oneMinute.addActionListener(this);
        hundredMinutes = new JButton("100 minutes");
        hundredMinutes.addActionListener(this);
        stopSimulating = new JButton("Start/ stop");
        stopSimulating.addActionListener(this);

        this.setLayout(new FlowLayout());
        this.add(oneMinute);
        this.add(hundredMinutes);
        this.add(stopSimulating);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thread = new Thread(() -> {
            if (e.getSource() == oneMinute) controller.run(1);
            if (e.getSource() == hundredMinutes) controller.run(100);
            if (e.getSource() == stopSimulating) controller.toggle();
        });

        thread.start();
    }
}
