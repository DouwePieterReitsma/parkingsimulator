package parkingsimulator.views;

import javax.swing.*;
import java.awt.*;

public class SimulatorView extends AbstractView {
    private Container contentPane;
    private JFrame screen;

    CarParkView carParkView;
    ButtonsView buttonsView;
    StatsView statsView;

    public SimulatorView(CarParkView carParkView, ButtonsView buttonsView, StatsView statsView) {
        this.carParkView = carParkView;
        this.buttonsView = buttonsView;
        this.statsView = statsView;

        screen=new JFrame("Parking Simulator");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = screen.getContentPane();
        contentPane.setLayout(new GridLayout(2,2));
        contentPane.add(carParkView);
        contentPane.add(buttonsView);
        contentPane.add(statsView);

        screen.pack();
        screen.setVisible(true);
    }

    public CarParkView getCarParkView(){
        return carParkView;
    }

    public ButtonsView getButtonsView(){
        return buttonsView;
    }

    public StatsView getStatsView(){
        return statsView;
    }
}