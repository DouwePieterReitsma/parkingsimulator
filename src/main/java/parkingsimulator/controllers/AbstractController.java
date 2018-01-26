package parkingsimulator.controllers;

import parkingsimulator.views.AbstractView;
import parkingsimulator.models.AbstractModel;


public abstract class AbstractController<TView extends AbstractView, TModel extends AbstractModel>
{
    private TView view;
    private TModel model;

    public AbstractController(){

    }

    public TView getView() {
        return view;
    }

    public TModel getModel(){
        return model;
    }

    public void setView(TView view) {
        this.view = view;
    }

    public void setModel(TModel model) {
        this.model = model;
    }
}