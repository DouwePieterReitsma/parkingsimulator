package parkingsimulator.views;

import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import parkingsimulator.models.SimulatorViewModel;

public class PieChartView extends AbstractView {
    private SimulatorViewModel model;
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    int a = 1;
    int b = 5;
    int c = 8;

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
                "Car type {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        add(panel);
    }

    public DefaultPieDataset createDataset() {

        dataset.setValue("AdHoc", a);
        dataset.setValue("ParkingPass", b);
        dataset.setValue("Reservation", c);
        return dataset;
    }

    public void updateView() {
        a++;
        b++;
        c++;
        dataset.setValue("AdHoc", a);
        dataset.setValue("ParkingPass", b);
        dataset.setValue("Reservation", c);
        // chart.getPlot().

    }


}
