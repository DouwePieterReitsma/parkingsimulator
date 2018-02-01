package parkingsimulator.controllers;

import parkingsimulator.models.AbstractModel;
import parkingsimulator.views.AbstractView;

/**
 * @param <TView> view type
 * @param <TModel> model type
 */
public abstract class AbstractController<TView extends AbstractView, TModel extends AbstractModel> {
    private TView carParkView;
    private TModel model;

    public AbstractController() {

    }

    public TView getView() {
        return carParkView;
    }

    public void setView(TView carParkView) {
        this.carParkView = carParkView;
    }

    public TModel getModel() {
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }
}