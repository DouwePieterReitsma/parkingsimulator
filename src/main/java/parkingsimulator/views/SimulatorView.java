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
    private JButton oneStep;
    private JButton hundredSteps;
    private JButton stopSimulating;
    private Container contentPane;
    private JPanel buttons;
    private JFrame screen;

    public SimulatorView(SimulatorController controller, SimulatorViewModel model) {
        this.controller = controller;
        this.model = model;

        screen=new JFrame("Parking Simulator");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        carParkView = new CarParkView(model);
        queueView = new QueueView(model);

        oneStep = new JButton("1 step");
        oneStep.addActionListener(this);
        hundredSteps = new JButton("100 steps");
        hundredSteps.addActionListener(this);
        stopSimulating = new JButton("Start/ stop");
        stopSimulating.addActionListener(this);

        buttons = new JPanel(new GridLayout(1,3));
        buttons.add(oneStep);
        buttons.add(hundredSteps);
        buttons.add(stopSimulating);

        contentPane = screen.getContentPane();
        contentPane.setLayout(new GridLayout(2,2));
        contentPane.add(carParkView);
        contentPane.add(buttons);
        contentPane.add(queueView);

        screen.pack();
        screen.setVisible(true);

        updateView();
    }

    public void actionPerformed(ActionEvent e) {

        Thread thread = new Thread(() -> {
            if (e.getSource()==oneStep) controller.run(1);
            if (e.getSource()==hundredSteps) controller.run(100);
            if (e.getSource()==stopSimulating) controller.toggle();
        });
        thread.start();
    }

    public void updateView() {
        carParkView.updateView();
    }

    public QueueView getQueueView() {
        return queueView;
    }
}
