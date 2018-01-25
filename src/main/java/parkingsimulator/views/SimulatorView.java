package parkingsimulator.views;

import parkingsimulator.controllers.SimulatorController;
import parkingsimulator.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorView extends View
{
    private CarParkView carParkView;
    private SimulatorController controller;
    private SimulatorViewModel model;
    private JButton oneStep;
    private JButton hundredSteps;
    private Container contentPane;
    private JPanel buttons;


    public SimulatorView(SimulatorController controller, SimulatorViewModel model) {
        this.controller = controller;

        this.model = model;

        this.setTitle("Parking Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        carParkView = new CarParkView();
        oneStep = new JButton("1 step");
        hundredSteps = new JButton("100 steps");

        buttons = new JPanel(new BorderLayout());
        buttons.add(oneStep, BorderLayout.CENTER);
        buttons.add(hundredSteps, BorderLayout.EAST);

        oneStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.run(1);
            }
        });

        hundredSteps.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.run(100);
            }
        });

        contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.NORTH);
        contentPane.add(buttons, BorderLayout.WEST);

        pack();
        setVisible(true);

        updateView();
    }

    public void updateView() {
        carParkView.updateView();
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        //return model.getCars()[location.getFloor()][location.getRow()][location.getPlace()];

        return model.getLocations()[location.getFloor()][location.getRow()][location.getPlace()].getCar();
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            this.model.getLocations()[location.getFloor()][location.getRow()][location.getPlace()].setCar(car);
            car.setLocation(location);
            model.decrementNumberOfOpenSpots();
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }

        Car car = getCarAt(location);

        if (car == null) {
            return null;
        }

        this.model.getLocations()[location.getFloor()][location.getRow()][location.getPlace()].setCar(null);
        car.setLocation(null);
        model.incrementNumberOfOpenSpots();

        return car;
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for (int row = 0; row < model.getNumberOfRows(); row++) {
                for (int place = 0; place < model.getNumberOfPlaces(); place++) {

                    Location location = this.model.getLocations()[floor][row][place];

                    if (getCarAt(location) == null)
                        return location;
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for (int row = 0; row < model.getNumberOfRows(); row++) {
                for (int place = 0; place < model.getNumberOfPlaces(); place++) {
                    Location location = model.getLocations()[floor][row][place];

                    if (location.getCar() != null && location.getCar().getMinutesLeft() <= 0 && !location.getCar().getIsPaying())
                        return location.getCar();
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for (int row = 0; row < model.getNumberOfRows(); row++) {
                for (int place = 0; place < model.getNumberOfPlaces(); place++) {
                    Location location = model.getLocations()[floor][row][place];

                    if (location.getCar() != null)
                        location.getCar().tick();
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();

        if (floor < 0 || floor >= model.getNumberOfFloors() || row < 0 || row > model.getNumberOfRows() || place < 0 || place > model.getNumberOfPlaces()) {
            return false;
        }

        return true;
    }

    private class CarParkView extends JPanel
    {

        private Dimension size;
        private Image carParkImage;

        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }

        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }

        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }

            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        /**
         * Update the view
         */
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for (int floor = 0; floor < model.getNumberOfFloors(); floor++) {
                for (int row = 0; row < model.getNumberOfRows(); row++) {
                    for (int place = 0; place < model.getNumberOfPlaces(); place++) {
                        Location location = model.getLocations()[floor][row][place];

                        Color color = location.getCar() == null ? Color.white : location.getCar().getColor();
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }

        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);

            int width = 20 - 1;
            int height = 10 - 1;

            int x = location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20;
//            int x = location.getFloor() * 260 + (int)(location.getRow() * 0.5) * width;
            int y = 60 + location.getPlace() * 10;

            graphics.fillRect(
                    x,
                    y,
                    width,
                    height);
        }
    }

}
