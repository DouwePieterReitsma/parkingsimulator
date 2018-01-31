package parkingsimulator.controllers;

import parkingsimulator.views.AbstractView;
import parkingsimulator.models.AbstractModel;

import javax.swing.*;

public abstract class AbstractController<TCarParkView extends AbstractView, TModel extends AbstractModel, TStatsView extends AbstractView> extends JPanel
{
    private TCarParkView carParkView;
    private TModel model;
    private TStatsView statsView;

    public AbstractController(){

    }

    public TCarParkView getCarParkView() {
        return carParkView;
    }

    public TModel getModel(){
        return model;
    }

    public TStatsView getStatsView() {
        return statsView;
    }

    public void setCarParkView(TCarParkView carParkView) {
        this.carParkView = carParkView;
    }

    public void setModel(TModel model) {
        this.model = model;
    }

    public void setStatsView(TStatsView statsView) {
        this.statsView = statsView;
    }

    public abstract void run(int steps);
}