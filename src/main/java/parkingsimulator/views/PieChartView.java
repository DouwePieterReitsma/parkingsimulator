package parkingsimulator.views;

import java.awt.*;
import java.text.DecimalFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import parkingsimulator.models.*;

public class PieChartView extends AbstractView {
    private SimulatorViewModel model;
    private DefaultPieDataset dataset;
    private JFreeChart chart;

    public PieChartView(SimulatorViewModel model) {
        this.model = model;
        this.setSize(800,400);
        this.setVisible(true);

        // Create dataset
        dataset = new DefaultPieDataset();
        dataset = createDataset();

        // Create chart
        chart = ChartFactory.createPieChart(
                "Cars parked in garage",
                dataset,
                true,
                true,
                false);
        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "Car type {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0.00%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        add(panel);
    }

    public DefaultPieDataset createDataset() {

        dataset.setValue("AdHoc", 0);
        dataset.setValue("ParkingPass", 0);
        dataset.setValue("Reservation", 0);
        return dataset;
    }

    public void updateView() {
        Location[][][] locations = model.getLocations();

        int adHocCar = 0;
        int parkingPassCar = 0;
        int reservationCar = 0;

        for (int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for (int row = 0; row < model.getNumberOfRows(); row++) {
                for (int place = 0; place < model.getNumberOfPlaces(); place++) {
                    Location location = locations[floor][row][place];
                    Car car = location.getCar();
                    if (location.getCar() != null) {
                        if(car instanceof AdHocCar) {
                            adHocCar++;
                        }
                        if(car instanceof ParkingPassCar) {
                            parkingPassCar++;
                        }
                        if(car instanceof ReservationCar) {
                            reservationCar++;
                        }
                    }

                }
            }
        }
        dataset.setValue("AdHoc", adHocCar);
        dataset.setValue("ParkingPass", parkingPassCar);
        dataset.setValue("Reservation", reservationCar);
    }
}