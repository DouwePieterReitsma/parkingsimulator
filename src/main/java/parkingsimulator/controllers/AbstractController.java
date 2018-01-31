package parkingsimulator.controllers;

import parkingsimulator.views.AbstractView;
import parkingsimulator.models.AbstractModel;


public abstract class AbstractController<TCarParkView extends AbstractView, TModel extends AbstractModel, TQueueView extends AbstractView>
{
    private TCarParkView carParkView;
    private TModel model;
    private TQueueView queueView;

    public AbstractController(){

    }

    public TCarParkView getCarParkView() {
        return carParkView;
    }

    public TModel getModel(){
        return model;
    }

    public TQueueView getQueueView() {
        return queueView;
    }

    public void setCarParkView(TCarParkView carParkView) {
        this.carParkView = carParkView;
    }

    public void setModel(TModel model) {
        this.model = model;
    }

    public void setQueueView(TQueueView queueView) {
        this.queueView = queueView;
    }

    public abstract void run(int steps);
}