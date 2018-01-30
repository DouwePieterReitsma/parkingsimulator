package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;
import parkingsimulator.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorView extends AbstractView
{
    private CarParkView carParkView;
    private SimulatorController controller;
    private SimulatorViewModel model;
    private JButton oneStep;
    private JButton hundredSteps;
    private Container contentPane;
    private JPanel buttons;
    private JFrame screen;


    public SimulatorView(SimulatorController controller, SimulatorViewModel model) {
        this.controller = controller;

        this.model = model;

        screen=new JFrame("Parking Simulator");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        carParkView = new CarParkView(model);
        oneStep = new JButton("1 step");
        hundredSteps = new JButton("100 steps");

        buttons = new JPanel(new BorderLayout());
        buttons.add(oneStep, BorderLayout.CENTER);
        buttons.add(hundredSteps, BorderLayout.EAST);

        oneStep.addActionListener(e -> controller.run(1));
        hundredSteps.addActionListener(e -> controller.run(100));

        contentPane = screen.getContentPane();
        contentPane.add(carParkView, BorderLayout.NORTH);
        contentPane.add(buttons, BorderLayout.WEST);

        screen.pack();
        screen.setVisible(true);

        updateView();
    }

    public void updateView() {
        carParkView.updateView();
    }
}
