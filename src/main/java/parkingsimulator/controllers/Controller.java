package parkingsimulator.controllers;

import parkingsimulator.models.Model;
import parkingsimulator.views.AbstractView;

public class Controller<TView extends AbstractView, TModel extends Model>
{
    private TView view;
    private TModel model;

    public Controller(){

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
