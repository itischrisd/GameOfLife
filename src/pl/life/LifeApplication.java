package pl.life;

import pl.life.controller.LifeController;
import pl.life.model.LifeModel;
import pl.life.model.RuleSet;
import pl.life.view.LifeView;

public final class LifeApplication {
    public static void main(String[] args) {
        var model = new LifeModel(100, 100, RuleSet.conway());
        var view = new LifeView(800, 800, model.rows(), model.cols());
        new LifeController(model, view).start();
    }
}
