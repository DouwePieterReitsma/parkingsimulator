package parkingsimulator.controllers;

import parkingsimulator.views.AbstractView;
import parkingsimulator.models.AbstractModel;

import javax.swing.*;

public abstract class AbstractController<TView extends AbstractView, TModel extends AbstractModel>
{
    private TView carParkView;
    private TModel model;

    public AbstractController(){

    }

    public TView getView() {
        return carParkView;
    }

    public void setView(TView carParkView) {
        this.carParkView = carParkView;
    }

    public TModel getModel(){
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }

    public abstract void run(int steps);
}