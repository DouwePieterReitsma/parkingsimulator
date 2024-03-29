package parkingsimulator.views;

import parkingsimulator.models.Location;
import parkingsimulator.models.SimulatorViewModel;

import java.awt.*;

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;
    private SimulatorViewModel model;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorViewModel model) {
        this.model = model;
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
        } else {
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
