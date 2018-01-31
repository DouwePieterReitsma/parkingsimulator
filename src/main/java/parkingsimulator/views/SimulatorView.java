package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;
import parkingsimulator.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorView extends AbstractView implements ActionListener {
    private CarParkView carParkView;
    private SimulatorController controller;
    private SimulatorViewModel model;
    private QueueView queueView;
    private JButton oneMinute;
    private JButton hundredMinutes;
    private JButton stopSimulating;
    private Container contentPane;
    private JPanel buttons;
    private JFrame screen;

    public SimulatorView(CarParkView carParkView, SimulatorController controller, SimulatorViewModel model, QueueView queueView) {
        this.carParkView = carParkView;
        this.controller = controller;
        this.model = model;
        this.queueView = queueView;

        screen=new JFrame("Parking Simulator");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        oneMinute = new JButton("1 minute");
        oneMinute.addActionListener(this);
        hundredMinutes = new JButton("100 minutes");
        hundredMinutes.addActionListener(this);
        stopSimulating = new JButton("Start/ stop");
        stopSimulating.addActionListener(this);

        buttons = new JPanel(new GridLayout(1,3));
        buttons.add(oneMinute);
        buttons.add(hundredMinutes);
        buttons.add(stopSimulating);

        contentPane = screen.getContentPane();
        contentPane.setLayout(new GridLayout(2,2));
        contentPane.add(carParkView);
        contentPane.add(buttons);
        contentPane.add(queueView);

        screen.pack();
        screen.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        Thread thread = new Thread(() -> {
            if (e.getSource()==oneMinute) controller.run(1);
            if (e.getSource()==hundredMinutes) controller.run(100);
            if (e.getSource()==stopSimulating) controller.toggle();
        });
        thread.start();
    }
}